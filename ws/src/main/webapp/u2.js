function $id(id) {
	return typeof (id) == "string" ? document.getElementById(id) : id;
}
function $attr(attr,val,node){
	var results=[],
		node=node||document.body;
	walk(node,function(n){
		var actual=n.nodeType===1&&(attr==="class"?n.className:n.getAttribute(attr));
		if(typeof actual === 'string' && (actual === val || typeof val !== 'string')){
			results.push(n);
		}
	});
	return results;
	function walk(n,func){
		func(n);
		n=n.firstChild;
		while(n){
			walk(n,func);
			n=n.nextSibling;
		}
	}
}
function $bindEvent(dom, handle, type) {
	if (!dom || !handle) {
		return;
	}
	type = type || 'click';
	if ( dom instanceof Array) {
		for (var i = 0, l = dom.length; i < l; i++) {
			$bindEvent(dom[i], handle, type);
		}
		return;
	}
	if ( type instanceof Array) {
		for (var i = 0, l = type.length; i < l; i++) {
			$bindEvent(dom, handle, type[i]);
		}
		return;
	}
	function setHandler(dom, type, handler,wrapper) {
		var eid=dom.__eventId = dom.__eventId || $incNum();
		$bindEvent.__allHandlers = $bindEvent.__allHandlers || {};
		$bindEvent.__allHandlers[eid]=$bindEvent.__allHandlers[eid]||{};
		$bindEvent.__allHandlers[eid][type]=$bindEvent.__allHandlers[eid][type]||[];
		$bindEvent.__allHandlers[eid][type].push({handler : handler,wrapper: wrapper});
	}
	function createDelegate(handle, context) {
		return function(e) {
			return handle.call(context,$eventNormalize(e || window.event));
		};
	}
	if(type=='wheel' || type=='mousewheel'|| type=='DOMMouseScroll'){
		//对wheel,mousewheel,DOMMouseScroll做一致性兼容
		type=( 'onwheel' in document || document.documentMode >= 9 )?'wheel':(/Firefox/i.test(navigator.userAgent))?"DOMMouseScroll": "mousewheel";
	}
	if (window.addEventListener) {
		var wrapper = createDelegate(handle, dom);
		setHandler(dom, type, handle, wrapper)
		dom.addEventListener(type, wrapper, false);
	} else if (window.attachEvent) {
		var wrapper = createDelegate(handle, dom);
		setHandler(dom, type, handle, wrapper)
		dom.attachEvent("on" + type, wrapper);
	} else {
		dom["on" + type] = handle;
	}
}
var $eventNormalize = (function() {
	function returnFalse() {
		return false;
	}

	function returnTrue() {
		return true;
	}

	var EventWrap = function(src, props) {
		// Allow instantiation without the 'new' keyword
		if (!(this instanceof EventWrap)) {
			return new EventWrap(src, props);
		}
		// Event object
		if (src && src.type) {
			this.originalEvent = src;
			this.type = src.type;
			// Events bubbling up the document may have been marked as prevented
			// by a handler lower down the tree; reflect the correct value.
			this.isDefaultPrevented = (src.defaultPrevented || src.returnValue === false || src.getPreventDefault && src.getPreventDefault() ) ? returnTrue : returnFalse;
			// Event type
		} else {
			this.type = src;
		}
		// Put explicitly provided properties onto the event object
		if (props) {
			$extend(this, props);
		}
		// Create a timestamp if incoming event doesn't have one
		this.timeStamp = src && src.timeStamp || new Date().getTime();
	};
	EventWrap.prototype = {
		isDefaultPrevented : returnFalse,
		isPropagationStopped : returnFalse,
		isImmediatePropagationStopped : returnFalse,
		preventDefault : function() {
			var e = this.originalEvent;
			this.isDefaultPrevented = returnFalse;
			if (!e) {
				return;
			}
			// If preventDefault exists, run it on the original event
			if (e.preventDefault) {
				e.preventDefault();
				// Support: IE
				// Otherwise set the returnValue property of the original event to false
			} else {
				e.returnValue = false;
			}
		},
		stopPropagation : function() {
			var e = this.originalEvent;
			this.isPropagationStopped = returnFalse;
			if (!e) {
				return;
			}
			// If stopPropagation exists, run it on the original event
			if (e.stopPropagation) {
				e.stopPropagation();
			}
			// Support: IE
			// Set the cancelBubble property of the original event to true
			e.cancelBubble = true;
		},
		stopImmediatePropagation : function() {
			this.isImmediatePropagationStopped = returnFalse;
			this.stopPropagation();
		}
	};
	var rkeyEvent = /^key/, rmouseEvent = /^(?:mouse|contextmenu)|click/;
	var props = "altKey bubbles cancelable ctrlKey currentTarget eventPhase metaKey relatedTarget shiftKey target timeStamp view which".split(" ");
	var fixHooks = {};
	var keyHooks = {
		props : "char charCode key keyCode".split(" "),
		filter : function(event, original) {
			// Add which for key events
			if (event.which == null) {
				event.which = original.charCode != null ? original.charCode : original.keyCode;
			}
			return event;
		}
	}, mouseHooks = {
		props : "button buttons clientX clientY fromElement offsetX offsetY pageX pageY screenX screenY toElement".split(" "),
		filter : function(event, original) {
			var body, eventDoc, doc, button = original.button, fromElement = original.fromElement;
			// Calculate pageX/Y if missing and clientX/Y available
			if (event.pageX == null && original.clientX != null) {
				eventDoc = event.target.ownerDocument || document;
				doc = eventDoc.documentElement;
				body = eventDoc.body;
				event.pageX = original.clientX + (doc && doc.scrollLeft || body && body.scrollLeft || 0 ) - (doc && doc.clientLeft || body && body.clientLeft || 0 );
				event.pageY = original.clientY + (doc && doc.scrollTop || body && body.scrollTop || 0 ) - (doc && doc.clientTop || body && body.clientTop || 0 );
			}
			// Add relatedTarget, if necessary
			if (!event.relatedTarget && fromElement) {
				event.relatedTarget = fromElement === event.target ? original.toElement : fromElement;
			}
			// Add which for click: 1 === left; 2 === middle; 3 === right
			// Note: button is not normalized, so don't use it
			if (!event.which && button !== undefined) {
				event.which = (button & 1 ? 1 : (button & 2 ? 3 : (button & 4 ? 2 : 0 ) ) );
			}
			return event;
		}
	};
	var special = {};
	//fix mousewheel
	fixHooks.wheel = fixHooks.mousewheel = fixHooks.DOMMouseScroll = mouseHooks;
	//hook
	var lowestDelta;
	function shouldAdjustOldDeltas(original, absDelta) {
		return original.type === 'mousewheel' && absDelta % 120 === 0;
	}
	special.wheel = special.mousewheel = special.DOMMouseScroll = function(event, original) {
		var delta = 0, deltaX = 0, deltaY = 0, absDelta = 0;
		//fixed type
		event.type = 'mousewheel';
		//fixed delta
		if ('detail' in original) {
			deltaY = original.detail * -1;
		}
		if ('wheelDelta' in original) {
			deltaY = original.wheelDelta;
		}
		if ('wheelDeltaY' in original) {
			deltaY = original.wheelDeltaY;
		}
		if ('wheelDeltaX' in original) {
			deltaX = original.wheelDeltaX * -1;
		}
		// Firefox < 17 horizontal scrolling related to DOMMouseScroll event
		if ('axis' in original && original.axis === original.HORIZONTAL_AXIS) {
			deltaX = deltaY * -1;
			deltaY = 0;
		}
		// Set delta to be deltaY or deltaX if deltaY is 0 for backwards compatabilitiy
		delta = deltaY === 0 ? deltaX : deltaY;
		// New school wheel delta (wheel event)
		if ('deltaY' in original) {
			deltaY = original.deltaY * -1;
			delta = deltaY;
		}
		if ('deltaX' in original) {
			deltaX = original.deltaX;
			if (deltaY === 0) {
				delta = deltaX * -1;
			}
		}
		// Store lowest absolute delta to normalize the delta values
		absDelta = Math.max(Math.abs(deltaY), Math.abs(deltaX));
		if (!lowestDelta || absDelta < lowestDelta) {
			lowestDelta = absDelta;
			// Adjust older deltas if necessary
			if (shouldAdjustOldDeltas(original, absDelta)) {
				lowestDelta /= 40;
			}
		}
		// Adjust older deltas if necessary
		if (shouldAdjustOldDeltas(original, absDelta)) {
			// Divide all the things by 40!
			delta /= 40;
			deltaX /= 40;
			deltaY /= 40;
		}
		// Get a whole, normalized value for the deltas
		delta = Math[ delta  >= 1 ? 'floor' : 'ceil' ](delta / lowestDelta);
		deltaX = Math[ deltaX >= 1 ? 'floor' : 'ceil' ](deltaX / lowestDelta);
		deltaY = Math[ deltaY >= 1 ? 'floor' : 'ceil' ](deltaY / lowestDelta);
		// Add information to the event object
		event.deltaX = deltaX;
		event.deltaY = deltaY;
		event.delta = delta;
		return event;
	};

	var eventNormalize = function(event) {
		if ( event instanceof EventWrap) {
			return event;
		}
		// Create a writable copy of the event object and normalize some properties
		var i, prop, copy, type = event.type, originalEvent = event, fixHook = fixHooks[type];
		if (!fixHook) {
			fixHooks[type] = fixHook = rmouseEvent.test(type) ? mouseHooks : rkeyEvent.test(type) ? keyHooks : {};
		}
		copy = fixHook.props ? props.concat(fixHook.props) : props;
		event = new EventWrap(originalEvent);

		i = copy.length;
		while (i--) {
			prop = copy[i];
			event[prop] = originalEvent[prop];
		}
		// Support: IE<9
		// Fix target property (#1925)
		if (!event.target) {
			event.target = originalEvent.srcElement || document;
		}
		// Support: Chrome 23+, Safari?
		// Target should not be a text node (#504, #13143)
		if (event.target.nodeType === 3) {
			event.target = event.target.parentNode;
		}
		// Support: IE<9
		// For mouse/key events, metaKey==false if it's undefined (#3368, #11328)
		event.metaKey = !!event.metaKey;
		event=fixHook.filter ? fixHook.filter(event, originalEvent) : event;
		event=special[type] ? special[type](event, originalEvent) : event;
		return event;
	};
	return eventNormalize;
})();
function $bindEvent(dom, handle, type) {
	if (!dom || !handle) {
		return;
	}
	type = type || 'click';
	if ( dom instanceof Array) {
		for (var i = 0, l = dom.length; i < l; i++) {
			$bindEvent(dom[i], handle, type);
		}
		return;
	}
	if ( type instanceof Array) {
		for (var i = 0, l = type.length; i < l; i++) {
			$bindEvent(dom, handle, type[i]);
		}
		return;
	}
	function setHandler(dom, type, handler,wrapper) {
		var eid=dom.__eventId = dom.__eventId || $incNum();
		$bindEvent.__allHandlers = $bindEvent.__allHandlers || {};
		$bindEvent.__allHandlers[eid]=$bindEvent.__allHandlers[eid]||{};
		$bindEvent.__allHandlers[eid][type]=$bindEvent.__allHandlers[eid][type]||[];
		$bindEvent.__allHandlers[eid][type].push({handler : handler,wrapper: wrapper});
	}
	function createDelegate(handle, context) {
		return function(e) {
			return handle.call(context,$eventNormalize(e || window.event));
		};
	}
	if(type=='wheel' || type=='mousewheel'|| type=='DOMMouseScroll'){
		//对wheel,mousewheel,DOMMouseScroll做一致性兼容
		type=( 'onwheel' in document || document.documentMode >= 9 )?'wheel':(/Firefox/i.test(navigator.userAgent))?"DOMMouseScroll": "mousewheel";
	}
	if (window.addEventListener) {
		var wrapper = createDelegate(handle, dom);
		setHandler(dom, type, handle, wrapper)
		dom.addEventListener(type, wrapper, false);
	} else if (window.attachEvent) {
		var wrapper = createDelegate(handle, dom);
		setHandler(dom, type, handle, wrapper)
		dom.attachEvent("on" + type, wrapper);
	} else {
		dom["on" + type] = handle;
	}
}
function $incNum(acc){
	acc=acc||$incNum;
	acc.num=acc.num||0;
	return acc.num++;
}
/**
 *
 *
 */

var iuni=iuni || {};
iuni.u2={};
iuni.u2.init=function(){
	this.initTab();
	this.slide();
};
iuni.u2.slide=function(){

	/* --------------------------------------------------
	 @ $Slide:滚动切换
	 ----------------------------------------------------- */
	function $Slide(o){
		var othis=this,undefined;
		othis.timeout=null;
		othis._selfID=(++othis.constructor._newNum);
		othis._selfName=".$Slide"+othis._selfID;

		if(!o){return;}
		if(o.config!=undefined){
			othis.config=$.extend({},othis.config,o.config);
		}
		if(o.oslide!=undefined){
			othis.oslide=$(o.oslide);
			othis.oPanel=othis.oslide.find(othis.config.panelSelector);
			othis.oItems=othis.oPanel.find(othis.config.itemsSelector);
			othis.oOptionsCon=othis.oslide.find(othis.config.optionsSelector);
			othis.oOptions=othis.oOptionsCon.find(othis.config.optionSelector);
		}
	}
	$Slide.prototype={
		config:{pos:0,isRandom:false,speed:400,delay:4000,auto:false,moveWay:"moveWidth",itemsSelector:".ui_slide_item",panelSelector:".ui_slide_panel",optionSelector:".ui_slide_option",optionsSelector:".ui_slide_options",optionCSSCurr:"ui_slide_option_curr",itemCSSCurr:"ui_slide_item_curr",itemWidth:0,itemHeight:0,onBeforeMove:null,isDelay:false},
		autoPlay:function(){
			var othis=this;
			clearTimeout(othis.timeout);
			othis.config.pos++;
			othis.move();
			othis.timeout=setTimeout(function(){othis.autoPlay(); return othis.timeout;},othis.config.delay);
		},
		setPos:function(){
			var othis=this;
			othis.config.pos>=othis.sum?othis.config.pos=othis.config.pos-othis.sum:othis.config.pos=othis.config.pos;
		},
		itemsHandler: function(e){
			var othis=e.data;
			clearTimeout(othis.timeout);
		},
		optionsEnterHandler:function(e){
			var othis=e.data,cfg=othis.config,oself=$(this);
			clearTimeout(othis.delayTimeout);
			othis.delayTimeout=setTimeout(function(){
				clearTimeout(othis.timeout);
				var index=othis.oOptions.index(oself);
				cfg.pos=index;
				othis.move();
			},200);
		},
		leaveHandler:function(e){
			var othis=e.data;
			clearTimeout(othis.delayTimeout);
			if(othis.config.auto){
				clearTimeout(othis.timeout);
				othis.timeout=setTimeout(function(){othis.autoPlay(); return othis.timeout;},othis.config.delay);
			}
		},
		init:function(){
			var othis=this,cfg=othis.config;
			var sum=othis.oItems.length;
			othis.sum=sum;

			if(sum==0){othis.oslide.hide();return false;}
			if(sum==1){
				othis.oOptions.hide();
				othis.oItems.addClass(cfg.itemCSSCurr);
				if(othis.config.isDelay){
					cfg.pos=0;
					$(window).bind("resize"+othis._selfName+" scroll"+othis._selfName,othis,othis.fnDelay);
					othis.fnDelay();
				}
				return false;
			}

			if(cfg.isRandom){
				cfg.pos=Math.floor(othis.sum*Math.random());
			}

			othis.setMoveWay();

			if(othis.config.isDelay){
				othis.config.onBeforeMove=othis.fnDelay;
				$(window).bind("resize"+othis._selfName+" scroll"+othis._selfName,othis,othis.fnDelay);
				othis.fnDelay();
			}

			othis.oItems.mouseenter(othis,othis.itemsHandler);
			othis.oOptions.mouseenter(othis,othis.optionsEnterHandler);
			othis.oOptions.mouseleave(othis,othis.leaveHandler);

			if(cfg.auto){
				othis.oItems.mouseleave(othis,othis.leaveHandler);
				othis.timeout=setTimeout(function(){othis.autoPlay(); return othis.timeout;},cfg.delay);
			}
		},
		move:function(){
			if(this.config.onBeforeMove){
				this.onBeforeMove=this.config.onBeforeMove;
				this.onBeforeMove();
			}
			this.movefunc();
		},
		setMoveWay:function(){
			var othis=this,cfg=othis.config;
			othis.oInitMoveWay[cfg.moveWay](othis);
			othis.movefunc=othis.oMoveWays[cfg.moveWay];
		},
		setStatus:function(){
			var othis=this,cfg=othis.config;
			othis.oItems.removeClass(cfg.itemCSSCurr);
			othis.oItems.eq(cfg.pos).addClass(cfg.itemCSSCurr);
			othis.oOptions.removeClass(cfg.optionCSSCurr);
			othis.oOptions.eq(cfg.pos).addClass(cfg.optionCSSCurr);
		},
		oInitMoveWay:{

			// moveOpacity 改变透明度
			moveOpacity:function(e){
				var othis=e,cfg=othis.config;
				othis.setPos();
				othis.oItems.hide();
				othis.oItems.eq(cfg.pos).show();
				othis.setStatus();
			}
		},
		oMoveWays:{

			// moveOpacity 改变透明度
			moveOpacity:function(){
				var othis=this,cfg=othis.config;
				othis.setPos();
				othis.oPanel.children().not(":hidden").stop(false,true).fadeOut(cfg.speed);
				othis.oItems.eq(cfg.pos).fadeIn(cfg.speed);
				othis.setStatus();
			}
		},
		fnDelay:function(e){
			var othis=(e && e.data) || this,oimgs;
			if(!othis.oDelayImgs){
				oimgs=othis.oslide.find("img");
				othis.oDelayImgs=new Array();
				oimgs.each(function(){
					if($(this).attr("orial_src")){othis.oDelayImgs.push(this);}
				});
			};
			var spos=othis.config.pos;
			if(spos>othis.oItems.length-1){spos=spos-othis.oItems.length;}
			oimgs=othis.oItems.eq(spos).find("img");

			var owin=$(window);
			var scrollStart=owin.scrollTop(),winH=owin.height(),end=scrollStart+winH;

			var sum=oimgs.length;
			oimgs.each(function(){
				var oself=$(this);
				var offTop=parseInt(oself.offset().top);
				var offBottom=parseInt(offTop+(oself.outerHeight() || 800));
				if((offTop>=scrollStart && offTop <=end) || (offBottom>=scrollStart && offBottom <= end )){
					oself.attr("src",oself.attr("orial_src"));
					for(var i=0;i<othis.oDelayImgs.length;i++){
						if(othis.oDelayImgs[i]== this){othis.oDelayImgs.splice(i,1);i--;break;}
					}
				}
			});
			if(othis.oDelayImgs.length==0){ othis.config.onBeforeMove=null; $(window).unbind("resize"+othis._selfName+" scroll"+othis._selfName);}
		}
	};
	$Slide.prototype.constructor=$Slide;
	$Slide._newNum=0;

	var banner=new  $Slide({oslide:"#slide",config:{auto:true,moveWay:"moveOpacity",isDelay:true,panelSelector:".slide_panel",itemsSelector:".banner-bg",optionsSelector:".banner-option",optionSelector:"li",optionCSSCurr:"slide-option-onshow",delay:8000}});
	banner.init();

};
iuni.u2.initTab=function(){
	//
	var domS10tab=$id("Js10tab"),
		domS10img=$id("Js10img"),
		tag10Name="tag10_action",
		tabOptionArr=$attr(tag10Name,"option",domS10tab),
		tabItemConAttr=$attr("sel10_action","con",domS10tab);

	$bindEvent(domS10tab,function(e){
		var e=e || window.event;
		var etarget=e.target || e.srcElement,
			elem=etarget,
			tag;

		while(elem!==domS10tab){
			tag=elem.getAttribute(tag10Name);
			if(tag){
				break;
			}else{
				elem=elem.parentNode;
			}
		}

		if(tag){

			switch(tag){

				case 'option':
					changeOption10(elem);
					break;
				case 'item':
					changeItem10(elem);
					break;
			};

		}

	},"click");

	function changeOption10(elem){
		var index=0;
		for(var i=0,len=tabOptionArr.length;i<len;i++){
			if(tabOptionArr[i].className){
				tabOptionArr[i].className="";
			}
			if(elem===tabOptionArr[i]){
				index=i;
				tabOptionArr[i].className="s10_3_tabs_onshow";
			}
		}

		//itemCon
		for(var i=0,len=tabItemConAttr.length;i<len;i++){
			tabItemConAttr[i].style.display="none";
		}

		if(tabItemConAttr[index]){
			var com=tabItemConAttr[index];
			com.style.display="block";

			var itemArr=$attr(tag10Name,"item",com);
			if(itemArr.length>0){
				changeItem10(itemArr[0]);
			}

		}

	}

	function changeItem10(elem){

		var p=elem.parentNode,
			itemAttr=$attr(tag10Name,"item",p);

		if($(item).hasClass("onshow")){
			return;
		}

		for(var i=0,len=itemAttr.length,item;i<len;i++){
			item=itemAttr[i];
			if(elem===item){
				$(item).addClass("onshow");
				domS10img.src=item.getAttribute("bigImg") || "";
			}else{
				$(item).removeClass("onshow");
			}
		}

	}

	changeOption10(tabOptionArr[0]);
}
iuni.u2.init();

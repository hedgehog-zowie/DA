/******************************
 脚部文件

 2013-11-28 10:06:35 wubocao 创建
 *******************************/
$namespace("iuni.common.footer");
/******************************
 　全局参数配置
 *******************************/
iuni.common.footer.data={

};
iuni.common.footer.dom={

};
/******************************
 　初始化配置
 *******************************/
iuni.common.footer.init=function(){

	//记录来源
	this.recoOrign();

	//数据上报
	this.initReport();

	//记录邀请码
	this.initInviteCode();
};
/******************************
 　功能实现区
 *******************************/

/**
 * 数据上报
 */
iuni.common.footer.initReport = function() {

	var hn2 = location.hostname.split('.');
	hn2.reverse();
	hn2.length = 2;
	hn2.push( 'rd2' );
	hn2.reverse();
	hn2 = hn2.join('.');

	//编码

	function encode(str) {
		if (str && str.replace) {
			return encodeURIComponent(str.replace(/\|/g, '_'));
		}
		return '';
	}
	//页面加载上报
	function reportLoad(pageId, referId, referer, fromId) {

		var url = encode(location.href);
		var uid = encode($getCookie('uid'));
		var sid = encode($getCookie('sid'));
		var adId = encode( $getQuery( 'adId' ) );
		var execTime;

		try{
			execTime = ( +new Date ) - pageExecStart;
		}catch(e){

		}

		$report('http://' + hn + '/dp/rptData', {
			id : pageId,
			type : 'pv',
			data : url + '|' + referId + '|' + referer + '|' + fromId,
			uid : uid,
			sid : sid
		});

		var reportObj = {
			type : 'pv',
			s1: pageId,
			s2: adId,
			s3: referer
		}

		if ( !isNaN( execTime ) ){
			reportObj.s4 = execTime;
		}

		$report( 'http://' + hn2 + '/log/report', reportObj );

	}
	//页面监控，标记上报
	function startRtagMonitor(pageId) {
		//点击上报
		$bindEvent(document.body, function(e) {

			var dom = $getTarget(e), t, b = document.body;
			//检测rtag标记
			while (dom && dom != this && !( t = dom.getAttribute('rtag'))) {
				dom = dom.parentNode;
			}

			if (t) {
				//上报
				var uid = encode($getCookie('uid'));
				var sid = encode($getCookie('sid'));
				$report('http://' + hn + '/dp/rptData', {
					id : pageId,
					type : 'rtag',
					data : encode(t),
					uid : uid,
					sid : sid
				});

				$report('http://rd2.iuni.com/log/report', {
					type : 'click',
					s1: t,	//rtag
					s2: pageId,	//url
				});

			}
		});
		//页面初始化判断上报
		if ( t = $getQuery('rtag')) {
			var uid = encode($getCookie('uid'));
			var sid = encode($getCookie('sid'));
			$report('http://' + hn + '/dp/rptData', {
				id : pageId,
				type : 'rtag',
				data : encode(t),
				uid : uid,
				sid : sid
			});
		}
	}
	//window对象缩写，处理上报域名
	var w = window, hn = location.hostname.split('.');
	hn.reverse();
	hn.length = 2;
	hn.push('rd');
	hn.reverse();
	hn = hn.join('.');
	//页面ID，用于标识页面
	var pageId = encode(w['__PAGEID'] || (location.protocol + '//' + location.host + location.pathname));
	//来源ID，自定义标记用
	var referId = encode(w['__REFERID'] || $getQuery('REF'));
	//referer标记，标识来源
	var referer = encode(document.referrer);
	//页面标记，fromID，推广用
	var fromId = encode(w['__FROMID']);
	//启动上报
	reportLoad(pageId, referId, referer, fromId);
	//启动页面监控，标记上报
	startRtagMonitor(pageId);


};

/**
 *  记录邀请码
 */
iuni.common.footer.initInviteCode=function(){
	var url=window.location.search;
	var invite_code=$getQuery("invite_code",url),   //用户id
		zt_remark;    //活动id

	if(invite_code){

		$setCookie("invite_code",invite_code,null,"/","iuni.com");

		zt_remark=$getQuery("zt_remark",url);
		if(zt_remark){
			$setCookie("zt_remark",zt_remark,null,"/","iuni.com");
		}

	}
};
/**
 * 记录来源
 */
iuni.common.footer.recoOrign=function(){
	var url=decodeURIComponent(window.location.search).toLowerCase();
	var ad_id=$getQuery("ad_id",url);   //来源ad_id

	if(ad_id){
		var u = navigator.userAgent;
		var exp=null;
		if(u.indexOf('iPhone') > -1 || (!!/.*Android.*Mobile.*/.test(u) && (u.indexOf('FROYO')=== -1)) || (!!/'.*Touch.*Mobile.*'/.test(u)) || !!/nokia/i.test(u)){
			exp=60*24;
		}
		$setCookie("ad_id",ad_id,exp,"/","iuni.com");

	}
}

iuni.common.footer.init();
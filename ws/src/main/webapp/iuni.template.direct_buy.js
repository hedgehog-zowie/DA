
/******************************
 直购

 2014-01-04 13:21:30 svenzeng 创建
 *******************************/
$namespace("iuni.template.direct_buy");

/******************************
 　全局参数配置
 *******************************/

iuni.template.direct_buy.data = {};

/******************************
 　初始化配置
 *******************************/
iuni.template.direct_buy.set_default_value = function( dom, value ){

	dom[0].onfocus = function(){
		if ( dom.val() === value ){
			dom.val( '' );
			dom.removeClass( 'color_gray1' );
		}
	};


	dom[0].onblur = function(){
		if ( dom.val() === '' ){
			dom.val( value );
			dom.addClass( 'color_gray1' );
		}
	};

	dom.removeAttr( 'placeholder' );

	setTimeout(function(){
		if ( dom.val() === '' ){
			dom.val( value );
			dom.addClass( 'color_gray1' );
		}
	}, 100);

	return {
		get_val: function(){
			var val = dom.val();
			if ( val === value ){
				return '';
			}
			return val;
		}
	}

}

iuni.template.direct_buy.get_checked = function( nodes ){	//取得选中的radio
	var node;
	$.each( nodes, function(){
		if ( $(this).is( ':checked' ) ){
			return node = $( this );
		}
	});
	return node || {val:function(){return ''}};
};


iuni.template.direct_buy.Address = function(){


	var layer = function(){	//增加收货地址浮层


		var bindRegionSelect = function( ary, callback ){

			var targetSelect;

			var event = $Event.create('iuni');

			var _ary = ary.slice( 1 );

			var country = ary[ 0 ];


			while( ary.length >= 2 ){

				var item = ary.shift();

				~function( _target, length ){

					var index = 0;

					item.off().on( 'change', function(ev,param){

						if ( $(this).find('option').eq(0).val() === '0' ){
							$(this).find('option').eq(0).remove();
						}

						var request = $Request.create({
							url: 'http://www.iuni.com/api/user/region_list',
							type: 'jsonp',
							cache: true
						});

						request.done( function( data ){

							var str = '';
							$.each( data.data, function( i, n ){
								str += '<option value='+ n.id +'>'+ n.name +'</option>';
							});

							_target.html( str );

							if ( !callback ){
								_target.trigger( 'change' );
							}

							if ( !callback && index++ === 0 ){
								if ( length === 3 ){
									_target.prepend( '<option value="0">选择省/自治区</option>' );
								}else if ( length === 2 ){
									_target.prepend( '<option value="0">选择城市/地区/自治州</option>' );
								}else if ( length === 1 ){
									_target.prepend( '<option value="0">选择区/县</option>' );
								}
							}

							_target[0].options[0].selected = true;

							if ( callback ){
								callback( _target );
								_target.trigger( 'change' );		//新增
							}

							return false;

						});

						request.setParam({
							parent_id: this.value
						});

						request.start();

						return false;

					});

				}( ary[ 0 ], ary.length );

			}

			country.trigger( 'change' );

		};



		var render = iuni.app.getSingle( function( __parent, address_list, callback ){

			var parent = __parent,
				closeBtn = parent.find( '[data-action="closeBtn"]' ),	//关闭按钮
				consignee = parent.find( '[data-action="consignee"]' ),	//收货人
				mobile = parent.find( '[data-action="mobile"]' ),	//手机
				tel = parent.find( '[data-action="tel"]' ),	//固话
				country = parent.find( '[data-action="country"]' ),	//国家
				province = parent.find( '[data-action="province"]' ),	//省
				city = parent.find( '[data-action="city"]' ),	//市
				district = parent.find( '[data-action="district"]' ),	//地区
				address = parent.find( '[data-action="address"]' ),	//地址
				zipcode = parent.find( '[data-action="zipcode"]' ),	//邮编
				saveBtn = parent.find( '[data-action="saveBtn"]' ),	//保存按钮
				cancel = parent.find( '[data-action="cancel"]' );	//取消按钮


			var consignee_error = parent.find( '[data-action="consignee_error"]' ),
				mobile_error = parent.find( '[data-action="mobile_error"]' ),
				address_error = parent.find( '[data-action="address_error"]' ),
				zipcode_error = parent.find( '[data-action="zipcode_error"]' );


			var getLength = function( str ){	//中文===2个字符
				var a = str.length , b = str.match(/[^\x00-\x80]/ig);
				if( b != null ) a += b.length * 1;
				return a;
			}


			consignee.focus( function(){
				consignee_error.hide();
			});

			mobile.focus( function(){
				mobile_error.hide();
			});

			tel.focus( function(){
				mobile_error.hide();
			});

			address.focus( function(){
				address_error.hide();
			});

			zipcode.focus( function(){
				zipcode_error.hide();
			});


			closeBtn.on( 'click', function(){
				hide();
			});


			var address_value = iuni.template.direct_buy.set_default_value( address, '请填写具体地址，不需要重复填写省/市/区' );

			var tel_value = iuni.template.direct_buy.set_default_value( tel, '区号-电话号码-分机号(可选)' );


			var saveRequest = $Request.create({
				url: 'http://www.iuni.com/api/user/save_address',
				type: 'iframe'
			});


			saveRequest.beforeSend( function(){

				var _consignee = $.trim ( consignee.val() ),
					_mobile = $.trim ( mobile.val() ),
					_tel = $.trim ( tel_value.get_val() ),
					_address = $.trim( address_value.get_val() ),
					_zipcode = $.trim( zipcode.val() ),
					flag = true;

				if ( _consignee === '' ){
					consignee_error.show().html( '收货人不能为空' );
					flag = false;
				}

				if ( getLength( _consignee ) < 4 || getLength( _consignee ) > 15 ){
					consignee_error.show().html( '收货人姓名为4~15个字符之间' );
					flag = false;
				}

				if ( _mobile === '' && _tel === '' ){
					mobile_error.show().html( '手机号或者固话不能为空' );
					flag = false;
				}


				if ( ( _mobile !== '' && !/^1\d{10}$/.test( _mobile ) ) || ( _tel !== '' && !$is_tel( _tel ) ) ){
					mobile_error.show().html( '请填写正确的手机号码或固定电话' );
					flag = false;
				}


				if ( _address === '' ){
					address_error.show().html( '收货地址不能为空' );
					flag = false;
				}

				if ( getLength( _address ) < 5 || getLength( _address ) > 60 ){
					address_error.show().html( '详细地址为5~60个字符之间' );
					flag = false;
				}

				if (  _zipcode.length && !/^[0-9][0-9]{5}$/.test( _zipcode ) ){
					zipcode_error.show().html( '请填写正确的邮政编码' );
					flag = false;
				}


				if ( district.val() === '0' ){
					address_error.show().html( '请选择区/县' );
					flag = false;
				}

				if ( city.val() === '0' ){
					address_error.show().html( '请选择城市/地区/自治州' );
					flag = false;
				}

				if ( province.val() === '0' ){
					address_error.show().html( '请选择省/自治区' );
					flag = false;
				}

				return flag;

			});


			saveRequest.done( function( data ){

				parent.fadeOut( 300 );

				var str = $micro_tmpl( 'tpl_address', { list: [ data.data ] } ); //添加新增的收货地址到列表

				var last = address_list.find( 'dd' ).last();

				if ( last.length ){
					var dom = $( str ).insertAfter( address_list.find( 'dd' ).last() );
				}else{
					var dom = $( str ).appendTo( address_list );
				}


				dom.find( 'input' ).attr( 'checked', 'checked' );

				iuni.common.ui.succ( '保存成功' );

			});


			saveRequest.error( function( data ){
				iuni.common.ui.fail( data.msg || '操作失败' );
			});


			saveBtn.on( 'click', function(){

				var param = {
					consignee: consignee.val(),
					mobile: mobile.val(),
					tel: tel_value.get_val(),
					countryId: country.val(),
					provinceId: province.val(),
					cityId: city.val(),
					districtId: district.val(),
					address: address_value.get_val(),
					zipcode: zipcode.val()
				}

				// var target = saveBtn.data( 'target' );

				// if ( target ){
				// 	param.id = target.attr( 'data-id' );
				// }

				saveRequest.setParam( param );

				saveRequest.start();

				return false;

			});


			cancel.on( 'click', function(){
				hide();
			});


			return {
				parent: parent,
				consignee: consignee,
				mobile: mobile,
				tel: tel,
				country: country,
				province: province,
				city: city,
				district: district,
				address: address,
				zipcode: zipcode,
				saveBtn: saveBtn
			};

		});


		var show = function( __parent, address_list, callback, mode ){

			var doms = render( __parent, address_list, callback, mode );
			doms.parent.fadeIn( 200 );
			doms.consignee.focus();
			iuni.template.direct_buy.set_default_value( doms.address, '请填写具体地址，不需要重复填写省/市/区' );
			iuni.template.direct_buy.set_default_value( doms.tel, '区号-电话号码-分机号(可选)' );
			callback( doms.consignee, doms.mobile, doms.tel, doms.address, doms.zipcode, doms.saveBtn, doms.country, doms.province, doms.city, doms.district );

		};


		var hide = function(){
			var doms = render();
			doms.parent.fadeOut( 300 );
		};


		return{
			show: show,
			hide: hide,
			bindRegionSelect: bindRegionSelect
		}


	}();


	var render_address_list = function( address_list, add_address_btn ){		//渲染收货地址列表


		var getRequest = iuni.app.getSingle( function( address_list ){

			var request = $Request.create({
				url: 'http://www.iuni.com/api/user/address_list',
				type: 'iframe'
			});


			request.done( function( data ){

				var str = $micro_tmpl( 'tpl_address', { list: data.data } );

				address_list.append( str );

				$Event.trigger( 'after_get_address_list' );

				if ( data.data.length === 0 ){
					add_address_btn.trigger( 'click' );
				}

			});

			return request;

		});


		var request = getRequest( address_list );

		request.start();

		return false;

	};


	var add_address = function( add_address_btn, add_address_layer, address_list ){		//增加收货地址

		add_address_btn.on( 'click', function(){

			var address_list_length = address_list.find( 'dd' ).length;

			if ( address_list_length >= 5 ){
				return $iuni_fail( '收货地址不能超过5个' );
			}

			layer.show( add_address_layer, address_list,
				function( consignee, mobile, tel, address, zipcode, saveBtn, country, province, city, district ){
					consignee.val( '' );		//打开浮层之后的回调，清空之前填的数据
					mobile.val( '' );
					tel.val( '' );
					address.val( '' );
					zipcode.val( '' );

					saveBtn.data({
						target: null
					});

					layer.bindRegionSelect( [ country, province, city, district ] );	//绑定省市区下拉框事件

				});
		});

	};






	return {
		render_address_list: render_address_list,
		add_address: add_address
	}


}();




/*********************************************************
 选择支付方式
 **********************************************************/



iuni.template.direct_buy.Payment = function(){

	var curr_tab,
		tab;

	var check_payment_type = function( payment_type, payment_1, shipping_list, order_sn ){

		var change_shipping_list = $Request.create({
			url: 'http://www.iuni.com/order/shipping_list',
			type: 'iframe',
			cache: true
		});


		change_shipping_list.done( function( data ){

			var data = data.data;
			var str = '';
			$.each( data, function( i,n ){
				//console.dir(n);
				str += '<dd><input data-action="shipping_list_radio" name="shipping_list" type="radio" value="'+ n.code +'" class="f_l">' +
					'<div class="express"> <strong>'+ n.name +'</strong><p class="color_gray1">' +n.desc +'</p></div></dd>'
			});

			shipping_list.html( str );

			var deliver = shipping_list.find( 'input[value='+ window.deliverId +']' );

			if ( deliver.length ){
				deliver.trigger( 'click' );
			}else{
				shipping_list.find( 'input' ).eq( 0 ).trigger( 'click' );
			}

			setTimeout(function(){
				iuni.template.direct_buy.compute_price();
			}, 100);

		});


		payment_type.on( 'click', function(){		//选择在线支付或者货到付款

			var param = {
				order_sn: order_sn
			}

			if ( this.value === '1' ){	//在线支付
				param.pay_type = 2;
				iuni.template.direct_buy.Payment.get_tab().show();
			}else{	//货到付款
				param.pay_type = 1;
				iuni.template.direct_buy.Payment.get_tab().hide();
			}

			change_shipping_list.setParam( param );

			change_shipping_list.start();

		});




		setTimeout( function(){

			//if ( window.payType === '1' ){	//在线支付
			payment_type.eq( 0 ).trigger( 'click' );
			//}else{
			//payment_type.eq( 1 ).trigger( 'click' );
			//}

		}, 500 );

		//默认切换支付或者货到付款


	};

	var get_payment_type = function( payment_type ){		//查询已选择的支付方式(在线支付或者货到付款)
		return iuni.template.direct_buy.get_checked( payment_type ).val();
	};


	var payment_mode = function( payment_tab ){


		var request = $Request.create({
			url: 'http://www.iuni.com/api/pay/payment',
			type: 'iframe'
		});


		var class_map = {
			alipay: 'alipay',   //支付宝
			chinapay:'unpay',    //银联在线
			cmb: 'zs',	//招商银行
			icbcb2c: 'icbc', //中国工商银行
			ccb: 'ccb', //中国建设银行
			comm: 'communications',	//交通银行
			abc: 'agricultural', //中国农业银行
			citic: 'citic', //中信银行
			spdb: 'spd', //上海浦发银行
			bocb2c: 'bankofchina', //中国银行
			gdb: 'cgb', //广发银行
			cib: 'cib', //兴业银行
			postgc: 'postal', //中国邮政储蓄银行
			spabank: 'ping', //平安银行
			hzcbb2c: 'hzbank', //杭州银行
			cmbc: 'cmbc', //中国民生银行
			bjrcb: 'brcb', //北京农商银行,
			cebbank:"ceb",  //光大银行,旧值
			'CEB-DEBIT':"ceb",  //光大银行2014-10-23
			nbbank:"nbbank",  //宁波银行
			shbank:"shanghai",  //上海银行
			fdb:"fdbank",  //富滇银行
			'CMB-EXPRESS-CREDIT': 'zs',
			'CCB-MOTO-CREDIT': 'ccb',
			'ABC-EXPRESS-CREDIT': 'agricultural',
			'ICBC-MOTO-CREDIT ': 'icbc',
			'BOC-MOTO-CREDIT': 'bankofchina',
			'SPABANK-MOTO-CREDIT': 'ping',
			'CITIC-EXPRESS-CREDIT': 'citic',
			'CMBC-EXPRESS-CREDIT ': 'cmbc'
		};


		request.done( function( data ){

			var payment_list = data.data[ 1 ].payment_list;

			var platform = payment_list[ 3 ],
				online_back = payment_list[ 1 ],
				credit_card = payment_list[ 2 ];


			tab = iuni.common.ui.Tab.getInstance( payment_tab );

			var tab1 = tab.add( '支付平台', function( ret ){

				var str = '<ul class="bank_box cf">';

				$.each( platform, function( i, n ){
					var checked = this.pay_code === window.bankcode ? 'checked': '';

					str += '<li class="blankborder '+ class_map[ this.pay_code ] +'"><div class="iner"><input '+ checked +' data-pay_name='+ this.pay_name +' data-pay_type='+ this.pay_type +' data-pay_code='+ this.pay_code +' data-catid='+ this.catid +' name="platform" type="radio" value=""></div></li>'
				});

				str += '</ul>'

				var parent = $( str );

				parent.find( 'li' ).on( 'click', function(){
					parent.find( '.greenborder' ).removeClass( 'greenborder' ).addClass( 'blankborder' );
					$( this ).removeClass( 'blankborder' ).addClass( 'greenborder' );
					$( this ).find( 'input' ).attr( 'checked', true );
				})


				var curr_tab_item = parent.find( '[data-pay_code='+ window.bankcode +']' );

				if ( curr_tab_item.length ){
					setTimeout( function(){
						curr_tab_item.trigger( 'click' );
						ret.trigger();
					}, 100 );
				}else{
					parent.find( 'li' ).eq( 0 ).trigger( 'click' );
				}

				return parent;

			});


			var tab2 = tab.add( '网银支付', function( ret ){

				var str = '<ul class="bank_box cf">';

				$.each( online_back, function( i, n ){
					var checked = this.pay_code === window.bankcode ? 'checked': '';

					str += '<li class="blankborder '+ class_map[ this.pay_code ] +' "><div class="iner"><input '+ checked +' data-pay_name='+ this.pay_name +' data-pay_type='+ this.pay_type +' data-pay_code='+ this.pay_code +' data-catid='+ this.catid +' name="online_bank" type="radio" value=""></div></li>'
				});

				str += '</ul>'

				var parent = $( str );

				parent.find( 'li' ).on( 'click', function(){
					parent.find( '.greenborder' ).removeClass( 'greenborder' ).addClass( 'blankborder' );
					$( this ).removeClass( 'blankborder' ).addClass( 'greenborder' );
					$( this ).find( 'input' ).attr( 'checked', true );
				})


				var curr_tab_item = parent.find( '[data-pay_code='+ window.bankcode +']' );

				if ( curr_tab_item.length ){
					setTimeout( function(){
						curr_tab_item.trigger( 'click' );
						ret.trigger();
					}, 100 );
				}else{
					parent.find( 'li' ).eq( 0 ).trigger( 'click' );
				}

				return parent;

			});


			if ( credit_card ){

				var tab3 = tab.add( '信用卡支付', function( ret ){

					var str = '<ul class="bank_box cf mart15">';

					$.each( credit_card, function( i, n ){

						var checked = this.pay_code === window.bankcode ? 'checked': '';


						str += '<li class=" blankborder '+ class_map[ this.pay_code ] +' "><div class="iner"><input '+ checked +' data-pay_name='+ this.pay_name +' data-pay_type='+ this.pay_type +' data-pay_code='+ this.pay_code +' data-catid='+ this.catid +' name="credit_card" type="radio" value=""></div></li>'
					});

					str += '</ul>'

					var parent = $( str );

					parent.find( 'li' ).on( 'click', function(){
						parent.find( '.greenborder' ).removeClass( 'greenborder' ).addClass( 'blankborder' );
						$( this ).removeClass( 'blankborder' ).addClass( 'greenborder' );
						$( this ).find( 'input' ).attr( 'checked', true );
					});

					var curr_tab_item = parent.find( '[data-pay_code='+ window.bankcode +']' );

					if ( curr_tab_item.length ){
						setTimeout( function(){
							curr_tab_item.trigger( 'click' );
							ret.trigger();
						}, 100 );
					}else{
						parent.find( 'li' ).eq( 0 ).trigger( 'click' );
					}

					return parent;

				});

			}

			tab.init();


		});

		request.error( function(){

		});

		request.start();

		return false;

	};


	var get_curr_tab = function(){
		return curr_tab;
	};


	var get_tab = function(){
		return tab;
	};

	var get_checked = function(){
		return iuni.template.direct_buy.get_checked( tab.getCurrTab().target.find( 'input' ) );
	};


	return {
		payment_mode: payment_mode,
		get_curr_tab: get_curr_tab,
		check_payment_type: check_payment_type,
		get_payment_type: get_payment_type,
		get_tab: get_tab,
		get_checked: get_checked
	}


}();



iuni.template.direct_buy.compute_price = function( shipping_list, use_conpounNumber, coupon_list, user_conponNumber_wrap,order_sn ){		//切换配送方式或者现金券之后重新计算商品价格

	var shipping_list_radio = shipping_list.find( 'input' ),	//配送方式
		coupon_list_radio = coupon_list.find( 'input' );	//现金券

	shipping_list_radio.eq( 0 ).attr( 'checked', 'checked' );
	coupon_list_radio.length && coupon_list_radio.eq( 0 ).attr( 'checked', 'checked' );




	var goods_amount = $( '[data-action="goods_amount"]' ),
		coupon_amount = $( '[data-action="coupon_amount"]' ),
		ship_amount1 = $( '[data-action="ship_amount1"]' ),
		ship_amount2 = $( '[data-action="ship_amount2"]' ),
		order_amount = $( '[data-action="order_amount"]' )


	var change_request = $Request.create({
		url: 'http://www.iuni.com/order/change',
		type: 'jsonp',
		timeout: 5000,
		lock:true
		//cache: true
	});


	change_request.done( function( data ){

		var data = data.data;
		goods_amount.html( '?' + data.goods_amount );
		coupon_amount.html( '-?' + data.coupon_amount );
		ship_amount1.html( '?' + data.ship_amount + '（已减?' + data.ship_prom + '）' );
		ship_amount2.html( '?' + data.ship_amount );
		order_amount.html('?' + data.order_amount );
		iuni.template.direct_buy.data.order_amount = data.order_amount;
	});


	change_request.error(function(data){
		$iuni_fail( data && data.msg || "网络延时请稍后再试");
		//to do   错误的时候，要把radio设置为请求之前的选中
	});


	change_request.timeout( function(data){
		$iuni_fail( data && data.msg || "网络延时请稍后再试");
		//to do   超时的时候，要把radio设置为请求之前的选中
	});


	var shipping_list_radio_val = iuni.template.direct_buy.get_checked( shipping_list_radio ).val();

	var coupon_list_radio_val = iuni.template.direct_buy.get_checked( coupon_list_radio ).val()||"";

	var conpousInput = $('[data-action="conpousText"]'),conpousText = conpousInput.val();

	var valida = iuni.template.direct_buy.validate_conpounNumber( conpousInput, conpousText);

	//coupon_list_radio_val，选中激活优惠码  ---- add wangzz
	if(coupon_list_radio_val){
		user_conponNumber_wrap.hide();
		user_conponNumber_wrap.parent().next().hide();
	}else{
		user_conponNumber_wrap.show();
		conpousInput.focus();
	}

	function ordioschange(ev){
		//切换配送方式或者现金券的事件

		if ( ev.target.tagName.toLowerCase() !== 'input' ){
			return false;
		}


		coupon_list_radio_val = iuni.template.direct_buy.get_checked( coupon_list_radio ).val()||"";

		var shipping_list_radio = shipping_list.find( 'input' );

		shipping_list_radio_val = iuni.template.direct_buy.get_checked( shipping_list_radio ).val();

		//coupon_list_radio_val，选中激活优惠码  ---- add wangzz
		if(coupon_list_radio_val){
			user_conponNumber_wrap.hide();
			user_conponNumber_wrap.parent().next().hide();
		}else{
			user_conponNumber_wrap.show();
			conpousInput.focus();
		}

		change_request.setParam({
			order_sn: order_sn || '',
			ship_code: shipping_list_radio_val || '',
			coupon_sn: iuni.template.direct_buy.coupon_type !== 2 ? ( coupon_list_radio_val || '' ) : ''
		});

		change_request.start();
	}

	shipping_list.add( coupon_list_radio ).on( 'change', ordioschange);

	use_conpounNumber.on('click',function(ev){   // 确认使用优惠码事件

		var $this = $(this);

		var conpousText = conpousInput.val().toUpperCase();

		if( valida() ) return;

		shipping_list_radio_val = iuni.template.direct_buy.get_checked( shipping_list_radio ).val();

		var bindCoupous_request = $Request.create({
			url: 'http://www.iuni.com/api/user/add_coupon',
			type: 'jsonp',
			timeout: 5000,
			lock: true
		});

		bindCoupous_request.error(function(data){
			$iuni_fail( data.msg );
		});


		bindCoupous_request.timeout( function(){
			$iuni_fail( '请求超时' );
		});

		bindCoupous_request.setParam({
			coupon_sn: conpousText
		});

		bindCoupous_request.done( function( data ){

			if(data.returnCode == 0 && data.errorCode == 0){
				var bonus = data.data.data.bonus;
				var coupon_radio = $('[data-action="coupon_radio"]');
				coupon_list_radio_val = conpousText;
				conpousInput.val("");
				conpousInput.blur();
				for(var i = 0,len=coupon_radio.length;i<len;i++){
					if(coupon_radio[i].value == bonus.boundSn){
						$iuni_succ( '您已激活成功！' );
						$(coupon_radio[i]).attr('checked','checked').siblings().removeAttr('checked');
						change_request.setParam({
							order_sn: order_sn || '',
							ship_code: shipping_list_radio_val || '',
							coupon_sn: conpousText
						});

						change_request.start();
						return;
					}
				}
				$iuni_succ( '激活成功！' );
				var noneCouponus = $("#noneCouponus");
				if(noneCouponus.length){
					noneCouponus.remove();
				}
				var money = bonus.money != 0 ? '[<b class="color_red">-?'+bonus.money+'</b>]' : '';
				var html = '<dd><input data-action="coupon_radio" name="coupon_radio" type="radio" value="'+bonus.boundSn+'" >'+bonus.boundTypeName + money +' </dd>';
				$(html).insertBefore($("#inputCounpous"));
				coupon_list.find( 'input[value="'+bonus.boundSn+'"]' ).attr('checked','checked').siblings().removeAttr('checked');
				coupon_list_radio=$('[data-action="coupon_radio"]');
				coupon_list_radio.eq(coupon_list_radio.length-2).on( 'change', ordioschange);

				change_request.setParam({
					order_sn: order_sn || '',
					ship_code: shipping_list_radio_val || '',
					coupon_sn: conpousText
				});

				change_request.start();

			}else{
				// $this.parent().next().html('<i class="iunifont i_suc z">&#59396;</i>'+data.msg).show();
				$iuni_fail( data && data.msg || "网络延时请稍后再试");
			}

		});

		bindCoupous_request.start();


	})



	iuni.template.direct_buy.compute_price = function(){		//初始化之后改写自身，共用之前的变量


		return function( _type ){

			iuni.template.direct_buy.coupon_type = _type || iuni.template.direct_buy.coupon_type ;

			var param = {
				order_sn: order_sn,
				ship_code: shipping_list_radio_val
			}

			if ( iuni.template.direct_buy.coupon_type !== 2 ){		//type===1为使用现金券，传coupon_sn到后台
				param.coupon_sn = coupon_list_radio_val
			}else{
				param.coupon_sn = '';
			}

			change_request.setParam( param );

			change_request.start();

		}

	}();

};

iuni.template.direct_buy.coupon_type = null;

iuni.template.direct_buy.user_conpon_list = function( user_conpon_list, coupon_list ){		//使用现金券

	var use_icon = $( '[data-action="use_icon"]' ),
		text = use_icon.next();

	user_conpon_list.on( 'click', function(){

		coupon_list.toggle();

		if ( use_icon.hasClass( 'use_icon' ) ){
			use_icon.removeClass( 'use_icon' ).addClass( 'use_icon2' );
			text.html ( '取消使用' );
			iuni.template.direct_buy.compute_price( 1 );
		}else{
			use_icon.removeClass( 'use_icon2' ).addClass( 'use_icon' );
			text.html ( '使用优惠券' );
			iuni.template.direct_buy.compute_price( 2 );
		}

		return false;

	});

};

iuni.template.direct_buy.validate_conpounNumber = function( dom,val ){		//验证优惠码

	var valida = function(){
		var result = null,
			conpNum_Res = /^[a-zA-Z0-9]{10}$/;
		var val = dom.val();
		if(!val){
			result = "请输入优惠码";
		}else{
			if(!conpNum_Res.test(val)){

				result = "请输入10位数字或字母";
			}
		}

		if(result){
			dom.parent().parent().next().html(' <div class="coupon_code_tips cl ui_clr_red"><i class="iunifont i_error z">&#59398;</i>'+result+'</div>').show();
		}

		return result;
	}

	dom.focus(function() {
		var $this = $(this);
		if($this.val() == '请输入优惠码'){
			$this.val('');
		}
		$this.parent().parent().next().hide();
	});

	dom.blur(function() {
		var $this = $(this);
		if($this.val() == ''){
			$this.val('请输入优惠码');
		}
		$this.parent().parent().next().hide();
	});

	dom.click(function(){
		var coupon_radios = $('[data-action="coupon_radio"]');
		var coupon_radio=coupon_radios.eq(coupon_radios.length-1);
		if(!coupon_radio.is(":checked")){  //
			if($isBrowser("ie8") || $isBrowser("ie7") || $isBrowser("ie6")){
				coupon_radios.removeAttr("checked");
				coupon_radio.attr("checked","checked");
			}
			coupon_radio.click();
			this.focus();
		}
	});

	return valida;

};


//提交

iuni.template.direct_buy.submit = function( submit_btn, payment_type, payment_1, order_sn, add_address_btn, need_inv, inv_value, inv_change,coupon_list_sel ){


	inv_change.on( 'change', function(){

		var val = this.value,
			address_radio = $( '[data-action="address_radio"]' ),
			address_checked = iuni.template.direct_buy.get_checked( address_radio ),
			address_value = address_checked.length ? address_checked.attr( 'data-name' ) : '';

		if ( val === '(个人)' && $.trim( inv_value.val() ) === '' ){
			inv_value.val( address_value );
		}else{
			inv_value.val( '' );
		}

	});


	$Event.listen( 'after_get_address_list', function(){
		inv_change.trigger( 'change' );
	});


	submit_btn.on( 'click', function(){

		var shipping_list_radio = $( '[data-action="shipping_list_radio"]' ),
			use_icon = $( '[data-action="use_icon"]' ),
			coupon_radio = $( '[data-action="coupon_radio"]' );



		var address_radio = $( '[data-action="address_radio"]' ),
			_direct_buy = iuni.template.direct_buy;

		if ( address_radio.length === 0 ){		//提交之前要保证至少有一个收货地址
			return add_address_btn.trigger( 'click' );
		}


		var pay_type = _direct_buy.get_checked( payment_type ).val(),
			pay_code,
			address_id,
			ship_code,
			coupon_sn,
			order_type = 'ys',
			type_name = '',
			inv_title = '',
			inv_content = '',
		//订单金额,需传回后台做校验
			order_amount = _direct_buy.data.order_amount || $("#hdnOrderAmount").val();


		if ( pay_type === '1' ){	//在线支付
			pay_code = _direct_buy.Payment.get_checked().attr( 'data-pay_code' );
		}else{
			pay_code = 'cod';	//货到付款
		}

		address_id = _direct_buy.get_checked( address_radio ).val();

		ship_code = _direct_buy.get_checked( shipping_list_radio ).val();

		//优惠券
		coupon_sn = iuni.template.direct_buy.coupon_type !== 2 ? ( _direct_buy.get_checked( coupon_radio ).val() || '' ) : '';


		if ( need_inv.is( ':checked' ) ){
			type_name = '普通发票';
			inv_title = $.trim( inv_value.val() );
			inv_content = '商品明细';

			if ( inv_title === '' ){
				iuni.common.ui.fail( '请填写发票抬头' );
				return setTimeout(function(){
					inv_value.focus();
				}, 1000 );
			}
		}


		var param = {
			pay_type: pay_type,
			pay_code: pay_code,
			address_id: address_id,
			ship_code: ship_code,
			coupon_sn: coupon_sn,
			order_sn: order_sn,
			//order_type: order_type,
			type_name: type_name,
			inv_title: inv_title,
			inv_content: inv_content,
			order_amount : order_amount
		};


		var submit_request = $Request.create({
			url: 'http://www.iuni.com/order/submit',
			type: 'iframe',
			lock: true,
			target: order_amount - 0 === 0 ? 'self' : 'blank'
		});


		submit_request.setParam( param );

		submit_request.start();

		window.pay_callback = function( order_sn, order_url ){
			iuni.template.direct_buy.show_aftersubmit_layer( order_sn, order_url );
		};

		return false;

	});


};


iuni.template.direct_buy.show_aftersubmit_layer = function( order_sn ){

	$LocalStorage.save( 'from', 'order_submit' );

	return location.href = 'http://www.iuni.com/order/submit/' + order_sn;

};


iuni.template.direct_buy.deposit = function( btnDeposit , conDeposit ){
	btnDeposit.on( "click" , function(){
		var t = $(this).children(".use_icon");
		t[t.hasClass("use_icon2")?"removeClass":"addClass"]("use_icon2");
		conDeposit.toggle();
	})
}


//切换是否需要发票

iuni.template.direct_buy.inv = function( need_inv, inv_info ){

	need_inv.on( 'change', function(){
		if ( $(this).is(':checked') ){
			inv_info.show();
		}else{
			inv_info.hide();
		}
	});

}



iuni.template.direct_buy.init = function(){

	var address_list = $( '[data-action="address_list"]' ),
		add_address_btn = $( '[data-action="add_address_btn"]' ),
		add_address_layer = $( '[data-action="add_address_layer"]' ),
		payment_type = $( '[data-action="payment_type"]' ),
		payment_1 = $( '[data-action="payment_1"]' ),
		shipping_list = $( '[data-action="shipping_list"]' ),
		use_conpounNumber = $("#use_conpounNumber"),
		coupon_list = $( '[data-action="coupon_list"]' ),
		user_conpon_list = $( '[data-action="user_conpon_list"]' ),
		user_conponNumber_wrap=$("#conpous_input_wrap"),
		submit_btn = $( '[data-action="submit_btn"]' ),
		need_inv = $( '[data-action="need_inv"]' ),
		inv_info = $( '[data-action="inv_info"]' ),
		inv_value = $( '[data-action="inv_value"]' ),
		inv_change = $( '[data-action="inv_change"]' ),
		btnDeposit = $("#btnDeposit"),
		conDeposit = $("#conDeposit"),
		order_sn,
		_direct_buy = iuni.template.direct_buy;

	order_sn = location.href.split( 'confirm/' )[1] || '';


	_direct_buy.Address.render_address_list( address_list, add_address_btn );	//收货地址列表

	_direct_buy.Address.add_address( add_address_btn, add_address_layer, address_list );	//增加收货地址

	_direct_buy.Payment.payment_mode( payment_1 );	//支付方式列表

	_direct_buy.Payment.check_payment_type( payment_type, payment_1, shipping_list, order_sn );	//选择在线支付或者货到付款


	_direct_buy.compute_price( shipping_list, use_conpounNumber, coupon_list,user_conponNumber_wrap, order_sn );	//配送方式和优惠券改变后重新计算

	_direct_buy.user_conpon_list( user_conpon_list, coupon_list );	//切换是否使用优惠券

	_direct_buy.inv( need_inv, inv_info );	//切换是否需要发票

	_direct_buy.deposit( btnDeposit , conDeposit );

	inv_info.focus();

	_direct_buy.submit( submit_btn, payment_type, payment_1, order_sn, add_address_btn, need_inv, inv_value, inv_change )	//提交订单



};
/******************************
 　功能实现区
 *******************************/

iuni.template.direct_buy.init();
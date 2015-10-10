/******************************
 app初始化配置

 2013-12-10 11:00:36 svenzeng 创建
 *******************************/
$namespace("iuni.app");



/******************************
 　全局参数配置
 *******************************/

/******************************
 　初始化配置
 *******************************/



iuni.app.getSingle = function ( fn ) {
	var ret;
	return function () {
		return ret || ( ret = fn.apply(this, arguments) );
	};
};



iuni.app.dispatch = function( data ){

	var sso = data.sso || [];

	$.each( sso, function( i, n ){
		var img = new Image();
		img.src = n;
	});

};

/******************************
 　第三方登录
 *******************************/


iuni.app.bind_login_href = function( parent ){

	// var a = parent.find( 'a[href="'+ location.href +'"]' );

	// if ( a.length === 0 ){
	// 	a = parent.find( 'a[href="http://www.iuni.com/index.shtml"]' )
	// }

	// a.css( 'color', '#019c74' );


	var needLogin = parent.find( '[data-needLogin="true"]' );

	$.each( needLogin, function( i, n ){

		var $n = $( n );
		var href = $n.attr( 'href' );

		name = href.split( '/' ).pop().split( '.' )[0],

			$n.attr( 'data-name', name ).attr( 'href', 'javascript:void 0' ).off().on( 'click', function(){

				var isLogin;

				try{
					isLogin = iuni.common.header.userInfo.get().isLogin;
				}catch(e){
					isLogin = false;
				}

				if ( !isLogin ){
					return $iuni_Login_layer.show( href );
				}

				location.href = href;
				return false;

			});

	});

};




iuni.app.init = function(){

	/*降域*/
	document.domain = 'iuni.com';

	/*设置ajax返回的错误触发条件*/
	$Request.setErrorFilter( function( data, url ){

		if ( data.code === 0 || data.returnCode === 0 ){
			return true;
		}

		if ( ( data.returnCode === -1 || data.code === 6000 ) && url.indexOf( '/getinfo' ) < 0 ){
			//失去登陆态的情况不放入error回调， 会自动弹出登陆框
			return true;
		}

		return false;

	});


	/*设置等待重新发送的cgi的触发条件*/

	$Request.setReStartFilter( function( data, url ){
		if ( ( data.returnCode === -1 || data.code === 6000 ) && url.indexOf( '/getinfo' ) < 0 ){
			//$iuni_Login_layer.show( top.location.href );
			location.href = 'http://passport.iuni.com/login.shtml?reurl=' + encodeURIComponent( top.location.href );
			return true;
		}

		return false;

	});



	/*设置ajax根路径*/

	$Request.setUrlRoot( 'http://passport.iuni.com' );


	/*给ajax请求加上token*/

	var token = $getToken();
	var prototype = $Request.getPrototype();

	prototype.start = $before( prototype.start, function(){
		this.param = this.param || {};
		this.param.tk = token;
		this.url = $addToken( this.url );
	});


	var userlogged = (function(){

		var img = new Image;

		return function(){
			img.src = 'http://www.iuni.com/api/cart/userlogged';
		}

	})();


	$Event.listen( 'login_succ', userlogged );

};



$Event.listen( 'requestDone', function( request ){
	var nowDate = +new Date();
	var url = encodeURIComponent( request.url );
	var time = nowDate - request.startTime;
	var img = new Image;
	img.src = 'http://rd.iuni.com/ws/page?type=cgi&state=succ&url='+ url +'&time=' + time;
});


$Event.listen( 'requestTimeout', function( request ){
	var nowDate = +new Date();
	var url = encodeURIComponent( request.url );
	var time = nowDate - request.startTime;
	var img = new Image;
	img.src = 'http://rd.iuni.com/ws/page?type=cgi&state=fail&url='+ url +'&time=' + time;
});


/******************************
 　功能实现区
 *******************************/

iuni.app.init();
$(function(){
	//IP地址信息
//	alert("cip="+returnCitySN.cip+"\r\n"+
//		  "cid="+returnCitySN.cid+"\r\n"+
//		  "cname="+returnCitySN.cname+"\r\n"+
//		  "IP归属: "+(isNaN(returnCitySN.cid)?"海外IP":"大陆IP"));
	console.log("cip="+returnCitySN.cip+"\r\n"+
			  "cid="+returnCitySN.cid+"\r\n"+
			  "cname="+returnCitySN.cname+"\r\n"+
			  "IP归属: "+(isNaN(returnCitySN.cid)?"海外IP":"大陆IP"));

	
	//判断访问终端
	var browser = {
		versions : function() {
			var u = navigator.userAgent, app = navigator.appVersion;
			return {
				trident : u.indexOf('Trident') > -1, //IE内核
				presto : u.indexOf('Presto') > -1, //opera内核
				webKit : u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
				gecko : u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1,//火狐内核
				mobile : !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端
				ios : !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
				android : u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
				iPhone : u.indexOf('iPhone') > -1, //是否为iPhone或者QQHD浏览器
				iPad : u.indexOf('iPad') > -1, //是否iPad
				webApp : u.indexOf('Safari') == -1, //是否web应该程序，没有头部与底部
				weixin : u.indexOf('MicroMessenger') > -1, //是否微信 （2015-01-22新增）
				qq : u.match(/\sQQ/i) == " qq" //是否QQ
			};
		}(),
		language : (navigator.browserLanguage || navigator.language)
				.toLowerCase()
	}

	if (browser.versions.mobile || browser.versions.android|| browser.versions.ios) {
		console.log("移动端访问");
		if(returnCitySN.cid!=undefined&&isNaN(returnCitySN.cid)){
			window.location.href="/en_wap";
		}
	}else{
		console.log("PC端访问wap,即将访问web");
		if(returnCitySN.cid!=undefined&&isNaN(returnCitySN.cid)){
			window.location.href="/en_web";
		}else{
			window.location.href="/cn_web";
		}
	}
	
})
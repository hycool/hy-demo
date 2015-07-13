$(function(){
	console.log("IP地址="+window.location.hostname);
	//获取外网IP 
	

})

$(function() {
	var date = new Date();
//	console.log(date.toGMTString(date));
	/*
	document.cookie="username=huaiyang";
	document.cookie="userid=1001";
	document.cookie="password=123456";
	//document.cookie="password=0123";
	console.log("this is webMobile page");
	/*alert("document.documentElement.clientWidth="+document.documentElement.clientWidth+"\n"+
		  "window.devicePixelRatio="+window.devicePixelRatio+"\n"+
		  "window.screen.width="+window.screen.availWidth+"\n"+
		  "window.screen.height="+window.screen.availHeight+"\n"+
		  "actualScreenWidth="+$("#a1").outerWidth(true)+"\n"+
		  "contaner.width="+$("#container").outerWidth(true)+"\n"+
		  "top.width="+$("#top").outerWidth(true)+"\n"+
		  "content.width="+$(".content").outerWidth(true));*/
//	console.log(document.cookie);

	var object1 = {
		apple : 0,
		banana : {weight : 52,price : 100},
		cherry : 97
	};
	var object2 = {
		banana : {price : 200},
		durian : 100
	};
	// Merge object2 into object1
	$.extend(true,object1, object2);
	// Assuming JSON.stringify - not available in IE<8
	$("#log").append(JSON.stringify(object1));
//	console.log(JSON.stringify(object1));
	
	var file="file.js";
//	    file=["file.js","file1.js"];
//	console.log(typeof file=="string"?[file]:file);
//	console.log(file.replace(/^\s|\s$/g, ""));
	
	var str="js";
	var res=str=="css";
//	console.log(res);
	
	
	

})
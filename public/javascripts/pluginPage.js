/*
 * date:2015-03-13
 * author:hy
 * description:快牙3.2之快拿插件页
 */

$(function(){
	//首次加载默认行为
	//tip1:首次加载时列表第一个cellTable背景色置灰
	$(".cellTable").eq(0).css("background-color","#d9e1e5");
	//tip2:首次加载时列表第一个视频默认被播放,需要上报播放数。
	reportBoNum($(".cellTable").eq(0).attr("data-cid"));
	//tip3:首次加载时列表第一个视频默认被播放过,需要记录播放状态,下次点击时不再上报播放数
	$(".cellTable").eq(0).data("boState",true);
	//$(".cellTable").eq(0).data("boPlus",true);
	

	//播放处理,当前播放视频重复点击播放数不变、背景色不变。
	$(".cellTable").click(function(){
		/*
		//当前页面,每个视频播放数最多上报一次
		if(!$(this).data("boPlus")){
			if(!$(this).find(".boNum").data("noAdd")){
				String(parseFloat($(this).find(".boNum").text())+1).length>=6
				?$(this).find(".boNum").text(vagueNum(String(parseFloat($(this).find(".boNum").text())+1),$(this).find(".boNum")))
				:$(this).find(".boNum").text(parseFloat($(this).find(".boNum").text())+1);
			}
			$(this).data("boPlus",true);
			//首次点击上报播放数,ajax请求
			reportBoNum($(this).attr("data-cid"));
		}
		*/
		/*判断当前视频是否处于被选中查看状态,如果是则无需刷新iframe视频播放框,且不计播放数
	               否则需要将当前选中视频置为播放状态并刷新视频播放框,播放计数加1*/
	    if(!$(this).data("boState")){
	    	//当前视频处于未播放状态,则点击后出现如下行为
	    	$(".cellTable").css("background-color","#f0f0f0");
			$(this).css("background-color","#d9e1e5");
		    $(".cellTable").data("boState",false);
		    $(this).data("boState",true);
		    $(window.parent.document).find("#player").attr("src",$(this).attr("data-url"));
		    //由未播放状态视频转到播放状态的视频需要计数
	    	if(!$(this).find(".boNum").data("noAdd")){
				String(parseFloat($(this).find(".boNum").text())+1).length>=6
				?$(this).find(".boNum").text(vagueNum(String(parseFloat($(this).find(".boNum").text())+1),$(this).find(".boNum")))
				:$(this).find(".boNum").text(parseFloat($(this).find(".boNum").text())+1);
			}
		    //上报视频播放数加1
			reportBoNum($(this).attr("data-cid"));
	    }
	});
	
	//点赞处理
	$(".divZan").click(function(){
		if(!$(this).data("plus")){
			if(!$(this).find(".zanNum").data("noAdd")){
				String(parseFloat($(this).find(".zanNum").text())+1).length>=6
				?$(this).find(".zanNum").text(vagueNum(String(parseFloat($(this).find(".zanNum").text())+1),$(this).find(".zanNum")))
				:$(this).find(".zanNum").text(parseFloat($(this).find(".zanNum").text())+1);
			}
			$(this).data("plus",true);
			$(this).find("img").eq(0).hide();
			$(this).find("img").eq(1).show();
			//首次点赞上报点赞数,ajax请求
			reportZanNum($(this).attr("data-cid"));
			
		}
	});
	
	//播放友好提示
	$("#popOver").popover({
		html:true,
		content:"<span class='text-info'><small>想要全屏观看，请安装快拿~</small></span>",
		placement:"left"
	});
	function showPopover(){
		$("#popOver").popover("show");
		var t2=setTimeout("$('#popOver').popover('hide')",2000);
	}
	//var t1=setTimeout(function(){showPopover()},1000);
	
	function formatTimeStamp(timeStamp){
		if(timeStamp!=undefined&&timeStamp!=null){
			var date = new Date(timeStamp);
			//Y = date.getFullYear() + '-';
	        M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '月';
	        D = (date.getDate()<10?'0'+date.getDate():date.getDate()) + '日 ';
	        h = (date.getHours()<10?'0'+date.getHours():date.getHours()) + ':';
	        m = (date.getMinutes()<10?'0'+date.getMinutes():date.getMinutes()) + '';
	        //s = (date.getSeconds()<10?'0'+date.getSeconds():date.getSeconds()); 
			return M+D+h+m;  
		}else{
			return "error:timesta is undefined or null.";
		}
	};
	
	//格式化时间戳,遍历页面中所有需要被格式化的时间戳
	$(".timeStamp").each(function(){
		$(this).text(formatTimeStamp(parseFloat($(this).text())));
	});
	
	//ajax上报播放数
	function reportBoNum(cid){
		var param={"u":"zapya"};
	    $.ajax({
	        type:"PUT",
	        url:"/sns/v1/cards/"+cid,
	        data:{"u":JSON.stringify(param)},
	        success:function(result){
	            if(result != "error"){
	            }else{
	            	console.log("reportBoNum：result=error");
	            }
	        },
	        error:function(){
	            console.log("上报播放数失败");
	        }
	    })
	}
	
	//ajax上报点赞数
	function reportZanNum(cid){
		var param={"u":"zapya"};
	    $.ajax({
	        url:"/sns/v1/cards/"+cid+"/praise",
	        type:"POST",
	        data:{"u":JSON.stringify(param)},
	        success:function(result){
	        	if(result != "error"){
	            }else{
	            	console.log("reportZanNum：result=error");
	            }
	        },
	        error:function(){
	        	 console.log("上报点赞数失败");
	        }
	    })
	}
	
	 /*
     * 模糊处理点赞数、视频播放数
     * tip1:最多显示五位数
     * tip2:超过五位数显示例如"12万"
     */
	function vagueNum(str,obj){
		if(str.length==6){
			$(obj).data("noAdd",true);
			return str.substring(0,2)+"万";
		}else if(str.length==7){
			$(obj).data("noAdd",true);
			return str.substring(0,3)+"万";
		}else if(str.length==8){
			$(obj).data("noAdd",true);
			return str.substring(0,1)+"千万";
		}else if(str.length==9){
			$(obj).data("noAdd",true);
			return str.substring(0,1)+"亿";
		}else{
			$(obj).data("noAdd",false);
			return str;
		}
	}
	
	$(".boNum").each(function(i,item){
		$(this).text(vagueNum($(this).text(),$(this)));
	});
	$(".zanNum").each(function(i,item){
		$(this).text(vagueNum($(this).text(),$(this)));
	});
	
	
	
	//设置hintPic
	//alert("playerContainer.height="+$("#playerContainer").height()+"\n"+"player.height="+$("#player").height()+"\n"+"hintPic.height="+$("#hintPic").height());
	$("#hintPic").css("top",$("#playerContainer").height()-$("#hintPic").height()-5);
	$("#hintPic").click(function(){
		showPopover();
	});
	
})


//自测试
$(function(){
	//console.log($("#playerContainer").height());
	//alert(window.screen.width);
	console.log("屏幕宽度："+window.screen.width);
	console.log("屏幕高度："+window.screen.height);
	console.log("屏幕可用宽度："+window.screen.availWidth);
	console.log("屏幕可用高度："+window.screen.availHeight);
	//alert("width="+window.screen.availWidth+"\n"+"height="+window.screen.availHeight);
	//$("#player").width(window.screen.availWidth);
	//$("#player").height(300);
	console.log("playerContainer.height="+$("#playerContainer").height());
	console.log("player.height="+$("#player").height());
	//$("#listContainer").css("margin-top",$("#player").height());
	
	console.log("iframe content:"+$("#player").height());
	console.log($("#hintPic").height());
	$("#player").click(function(){
		//alert();
	});
	
	if(document.documentElement.clientWidth<333){
		//对于屏幕缩放后横向显示320像素的手机做视频标题显示的特殊处理。
		$(".cardTitle").width(143);
	}
	
})




























package controllers;

import java.util.Map;

import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.demo.bkn;
import views.html.demo.iframe_sns;
import views.html.demo.kn;
import views.html.demo.official_cn_wap;
import views.html.demo.official_cn_web;
import views.html.demo.official_en_wap;
import views.html.demo.official_en_web;
import views.html.demo.pluginPage;
import views.html.demo.sns;
import views.html.demo.video;
import views.html.demo.videomobile;
import views.html.demo.webMobile;

public class DemoController extends Controller {
	public static Result demo1() {
		return ok(kn.render());
	}

	public static Result demo2() {
		return ok(bkn.render());
	}

	public static Result demo3() {
		return ok(sns.render());
	}

	public static Result demo4() {
		return ok(iframe_sns.render());
	}

	public static Result demo5() {
		return ok(video.render());
	}

	public static Result demo6() {
		return ok(videomobile.render());
	}

	public static Result pluginPage() {
		return ok(pluginPage.render());
	}

	public static Result webMobile() {
		return ok(webMobile.render());
	}

	public static Result cn_web() {
		Logger.debug(request().headers().toString());
	    Map<String,String[]> headers=request().headers();
	    for(Map.Entry<String, String[]> entry:headers.entrySet()){
	    	Logger.debug("headerObj="+"\t"+entry.getKey()+":\t"+entry.getValue()[0]);
//	    	for(String value:entry.getValue()){
//	    		Logger.debug("headerValue="+"\t"+value);
//	    	}
	    }
		return ok(official_cn_web.render());
	}

	public static Result cn_wap() {
		return ok(official_cn_wap.render());
	}

	public static Result en_web() {
		return ok(official_en_web.render());
	}
	
	public static Result en_wap() {
		return ok(official_en_wap.render());
	}
}

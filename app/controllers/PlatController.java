package controllers;

import java.util.Map;

import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.plat.*;


public class PlatController extends Controller {
	
	public static Result index(){
		System.out.println("host="+request().host());
		System.out.println("remoteAddress="+request().remoteAddress());
		return ok(index.render());
	}
	
	public static Result dataTables(){
		return ok(dataTables.render());
	}
	
	public static Result highcharts_normal(){
		return ok(highcharts_normal.render());
	}
	
	public static Result highcharts_inverse(){
		return ok(highcharts_inverse.render());
	}
	
	public static Result morris(){
		return ok(morris.render());
	}
	
	public static Result jvectormap(){
		return ok(jvectormap.render());
	}
	
	public static Result highmaps(){
		return ok(highmaps.render());
	}
}

package controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.http.HttpRequest;

import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import com.hy.weixin.model.CoreService;

public class WeixinController extends Controller{
	public static Result insert(String signature,String timestamp,String nonce,String echostr){
		/*不进行校验,直接返回echostr*/
		System.out.println("\r\n"+"insert:"+"\r\n"+
	                       "signature="+signature+"\r\n"+
	                       "timestamp="+timestamp+"\r\n"+
				           "nonce="+nonce+"\r\n"+
				           "echostr="+echostr); 
//		return ok("request is successful"+"\r\n"+
//				  "signature="+signature+"\r\n"+
//                  "timestamp="+timestamp+"\r\n"+
//		          "nonce="+nonce+"\r\n"+
//		          "echostr="+echostr);
		return ok(echostr);
	}
	
	public static Result index(){
		System.out.println("\r\n"+"index:"+"\r\n"+
		                   "signature="+""+"\r\n"+
	                       "timestamp="+""+"\r\n"+
				           "nonce="+""+"\r\n"+
				           "echostr="); 
		return ok("request is successful");
	}
	
	/*核心处理类*/
	/**
	 * 处理微信服务器发送来的消息
	 * @throws UnsupportedEncodingException 
	 * */
	public static Result doPost() throws UnsupportedEncodingException{
		request().body().toString();
		System.out.println("\r\n"+
		                   "WeixinController.doPost():"+"\r\n"+
//				           "body().asJson="+request().body().asJson()+"\r\n"+
//				           "body().asText="+request().body().asText()+"\r\n"+
//				           "body().asXml="+request().body().asXml()+"\r\n"+
//				           "body().toString="+request().body()+"\r\n"+
//				           "body().asMultipartFormData="+request().body().asMultipartFormData()+"\r\n"+
				           "request().toString()="+request().toString()+"\r\n");
//		Map<String, String[]> bodyMap=request().queryString();
//		for(String key:bodyMap.keySet()){
//			System.out.println("\r\n"+
//		                       "key="+key+"\t"+"value="+bodyMap.get(key)+"\r\n");
//			
//		}
		//将请求、响应的编码均置为utf-8
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
		Http.Context.current().request().method().getBytes("UTF-8");
		String requestXml="";
		String bodyStr=request().body().toString();
		System.out.println("bodyStr.length()="+bodyStr.length()+"\r\n"+
		                   "bodyStr.indexof('<xml>')="+bodyStr.indexOf("<xml>")+"\r\n"+
		                   "bodyStr.indexof('</xml>')="+bodyStr.indexOf("</xml>"));
		requestXml=bodyStr.substring(bodyStr.indexOf("<xml>"), bodyStr.indexOf("</xml>")+6);

		System.out.println("\r\n"+"requestXml="+requestXml+"\r\n");
		//调用核心业务类接受、处理消息
		String respMessage=CoreService.processRequest(requestXml);
			
		return ok(respMessage);
	}
	
	
	
	
	
	
	
	
	
}
















































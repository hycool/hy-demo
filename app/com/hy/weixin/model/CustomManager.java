//package com.hy.weixin.model;
//
//import org.json.JSONException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.hy.weixin.pojo.Custom;
//import com.hy.weixin.util.AccessToken;
//import com.hy.weixin.util.MD5Util;
//import com.hy.weixin.util.WeixinUtil;
//
//public class CustomManager {
//
//	private static Logger log=LoggerFactory.getLogger(CustomManager.class);
//	
//	public static void main(String[] arg) throws JSONException{
//		
//		//测试号
//		String appId="wx30761c2a52220d42";
//		String appSecret="a90bf0b4f94f8b0e8471c24920e78973";
//		
//		//调用接口获取access_token;
//		AccessToken at=WeixinUtil.getAccessToken(appId, appSecret);
//		if(null!=at){
//			//调用接口创建菜单
//			int result=WeixinUtil.createCustom(getCustom(), at.getToken());
//			
//			//判断菜单创建结果
//			if(0==result){
//				log.error("菜单创建成功");
//			}else{
//				log.error("菜单创建失败，错误码："+result);
//			}
//		}
//		
//	}
//	
//	private static Custom getCustom(){
//		Custom custom=new Custom();
//		custom.setKf_account("kuaiya1@gh_6742360fd8bc");
//		custom.setNickname("客服");
//		custom.setPassword(MD5Util.getMd5("kuaiya123456"));
//		return custom;
//	}
//}

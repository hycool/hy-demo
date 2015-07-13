package com.hy.weixin.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hy.weixin.resp.Article;
import com.hy.weixin.resp.CustomService;
import com.hy.weixin.resp.KF;
import com.hy.weixin.resp.NewsMessage;
import com.hy.weixin.resp.TextMessage;
import com.hy.weixin.util.ChangeCharset;
import com.hy.weixin.util.MessageUtil;

public class CoreService {
	
	/*处理微信发来的请求*/
	public static String processRequest(String requestXml){
		System.out.println("开始执行 begin to excute processRequest");
		ChangeCharset changeCharset=new ChangeCharset();
		String respMessage="";
		try{
			//默认返回文本消息内容
			String respContent="亲爱的牙粉，等了好久终于把你等来，5月14日~5月18日快牙联合乐视发《何以笙箫默》电影红包，活动详情猛戳 http://dwz.cn/KdAxi";
			
			//xml请求解析
			Map<String,String> requestMap=MessageUtil.parseXml(requestXml);
			
			//发送方账号（OpenId）
			String fromUserName=requestMap.get("FromUserName");
			
			//公众号
			String toUserName=requestMap.get("ToUserName");
			
			//消息类型
			String msgType=requestMap.get("MsgType");
			
			//默认回复文本消息
			TextMessage textMessage=new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setContent(respContent);
//			System.out.println("\r\n"+
//			                   "CoreService.processRequest"+"\r\n"+
//					           "fromUserName="+fromUserName+"\r\n"+
//			                   "toUserName="+toUserName+"\r\n"+
//					           "msgType="+msgType+"\r\n");
			
			//接收文本消息根据消息内容判断返回何种消息格式
			if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)){
				
//				System.out.println("编码 toUTF_8 contnet="+changeCharset.toUTF_8(requestMap.get("Content")));
//				System.out.println("编码 toGBK contnet="+changeCharset.toGBK(requestMap.get("Content")));
//			    System.out.println("编码  toISO_8859_1 contnet="+changeCharset.toISO_8859_1(requestMap.get("Content")));
//			    System.out.println("编码  toUTF_16 contnet="+changeCharset.toUTF_16(requestMap.get("Content")));
//			    System.out.println("编码  toUTF_16BE contnet="+changeCharset.toUTF_16BE(requestMap.get("Content")));
//			    System.out.println("编码  toUTF_16LE contnet="+changeCharset.toUTF_16LE(requestMap.get("Content")));
			    
				if(changeCharset.toUTF_8(requestMap.get("Content")).equals("1")){
					//回复文本消息
					respMessage=MessageUtil.textMessageToXml(textMessage);
				}else if(requestMap.get("Content").equals("2")){
					//回复图文消息
					NewsMessage newsMessage=new NewsMessage();
					newsMessage.setToUserName(fromUserName);
					newsMessage.setFromUserName(toUserName);
					newsMessage.setCreateTime(new Date().getTime());
					newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
					//组织图文消息列表
					List<Article> articleList=new ArrayList<Article>();
					Article article1=new Article();
					article1.setDescription("this is first descriptoin");
					article1.setPicUrl("http://hy987101253.xicp.net/assets/images/icon.png");
					article1.setTitle("this is first title");
					article1.setUrl("http://www.baidu.com");
					Article article2=new Article();
					article2.setDescription("this is second descriptoin");
					article2.setPicUrl("http://hy987101253.xicp.net/assets/images/icon.png");
					article2.setTitle("this is second title");
					article2.setUrl("http://hy987101253.xicp.net/pluginPage");
					articleList.add(article1);
					articleList.add(article2);
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					respMessage=MessageUtil.newsMessageToXml(newsMessage);
				}else if(requestMap.get("Content").equals("3")){
					//回复图文消息
					NewsMessage newsMessage=new NewsMessage();
					newsMessage.setToUserName(fromUserName);
					newsMessage.setFromUserName(toUserName);
					newsMessage.setCreateTime(new Date().getTime());
					newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
					List<Article> articleList=new ArrayList<Article>();
					Article article1=new Article();
					article1.setDescription("this is first descriptoin");
					article1.setPicUrl("http://hy987101253.xicp.net/assets/images/icon.png");
					article1.setTitle("this is first title");
					article1.setUrl("http://hy987101253.xicp.net/pluginPage");
					articleList.add(article1);
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					respMessage=MessageUtil.newsMessageToXml(newsMessage);
				}else{
					textMessage.setContent("指令识别：\n"+
				                           "回复数字1  将得到文字消息\n"+
							               "回复数字2  将得到图文消息\n"+
							               "回复数字3  将得到单图消息\n"+
				                           "回复其他文字内容将得到当前指令");
					//回复文本消息
					respMessage=MessageUtil.textMessageToXml(textMessage);
				}
			}else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)){
				if(requestMap.get("Event").equals("CLICK")){
					System.out.println("Event=\t"+requestMap.get("Event"));
					System.out.println("EventKey=\t"+requestMap.get("EventKey"));
					if(requestMap.get("EventKey").equals("kuaiya_yhfk")){
						CustomService customService=new CustomService();
						customService.setToUserName(fromUserName);
						customService.setFromUserName(toUserName);
						customService.setCreateTime(new Date().getTime());
						customService.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_KF);
						KF transInfo=new KF();
						transInfo.setKfAccount("custom1");
						customService.setTransInfo(transInfo);
						respMessage=MessageUtil.customServiceToXml(customService);
					}else{
						textMessage.setContent("敬请期待！");
						respMessage=MessageUtil.textMessageToXml(textMessage);
					}
					
				}
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("respMessage=\r\n"+respMessage);
		return respMessage;
	}

	
	
}





















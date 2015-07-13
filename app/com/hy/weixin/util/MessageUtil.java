package com.hy.weixin.util;

import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.hy.weixin.resp.Article;
import com.hy.weixin.resp.CustomService;
import com.hy.weixin.resp.KF;
import com.hy.weixin.resp.NewsMessage;
import com.hy.weixin.resp.TextMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;




/**
 * 消息工具类
 * 
 * @author hy
 * @date 2014-03-19
 */
public class MessageUtil {

	/**
	 * 返回消息类型：文本
	 */
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 返回消息类型：音乐
	 */
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

	/**
	 * 返回消息类型：图文
	 */
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";
	
	/**
	 * 多客服服务
	 */
	public static final String RESP_MESSAGE_TYPE_KF="transfer_customer_service";

	/**
	 * 请求消息类型：文本
	 */
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 请求消息类型：图片
	 */
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

	/**
	 * 请求消息类型：链接
	 */
	public static final String REQ_MESSAGE_TYPE_LINK = "link";

	/**
	 * 请求消息类型：地理位置
	 */
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

	/**
	 * 请求消息类型：音频
	 */
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

	/**
	 * 请求消息类型：推送
	 */
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";
	
//
//	/**
//	 * 事件类型：subscribe(订阅)
//	 */
//	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
//
//	/**
//	 * 事件类型：unsubscribe(取消订阅)
//	 */
//	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
//
//	/**
//	 * 事件类型：CLICK(自定义菜单点击事件)
//	 */
//	public static final String EVENT_TYPE_CLICK = "CLICK";

	/**
	 * 解析微信发来的请求（XML）
	 * 
	 * @param requestXml
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(String requestXml) throws Exception {
		//System.out.println("parseXml  将结果存在HashMap中");
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();

//		//从request中取得输入流
//		InputStream inputStream = request.getInputStream();
//		// 读取输入流
//		SAXReader reader = new SAXReader();
		Document document = DocumentHelper.parseText(requestXml);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();

		// 遍历所有子节点
		for (Element e : elementList){
			map.put(e.getName(), e.getText());
//		    System.out.println("\r\n"+
//			                   "MessageUtil.parseXml:"+"\r\n"+
//		    		           "e.getName()="+e.getName()+"\r\n"+
//			                   "e.getText()="+e.getText()+"\r\n"); 
		}

//		 释放资源
//		inputStream.close();
//		inputStream = null;
		//System.out.println("parseXml  完毕！");
		return map;
	}

	/**
	 * 文本消息对象转换成xml
	 * 
	 * @param textMessage 文本消息对象
	 * @return xml
	 */
	public static String textMessageToXml(TextMessage textMessage) {
		xstream.alias("xml", textMessage.getClass());
		//System.out.println("MessageUtil.textMessageToXml:"+xstream.toXML(textMessage));
		return xstream.toXML(textMessage);
	}

//	/**
//	 * 音乐消息对象转换成xml
//	 * 
//	 * @param musicMessage 音乐消息对象
//	 * @return xml
//	 */
//	public static String musicMessageToXml(MusicMessage musicMessage) {
//		xstream.alias("xml", musicMessage.getClass());
//		return xstream.toXML(musicMessage);
//	}
//
	/**
	 * 图文消息对象转换成xml
	 * 
	 * @param newsMessage 图文消息对象
	 * @return xml
	 */
	public static String newsMessageToXml(NewsMessage newsMessage) {
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(newsMessage);
	}

	public static String customServiceToXml(CustomService customService){
		xstream.alias("xml", customService.getClass());
		return xstream.toXML(customService);
	}

	/**
	 * 扩展xstream，使其支持CDATA块
	 */
	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// 对所有xml节点的转换都增加CDATA标记
				boolean cdata = true;

				@SuppressWarnings("unchecked")
				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}

				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});
}

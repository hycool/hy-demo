//package com.hy.weixin.util;
//
//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.net.ConnectException;
//import java.net.URL;
//
//import javax.net.ssl.HttpsURLConnection;
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.SSLSocketFactory;
//import javax.net.ssl.TrustManager;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.hy.weixin.pojo.Custom;
//import com.hy.weixin.pojo.Menu;
//
//
//
//
//
///**
// * 公众平台接口工具类
// * */
//public class WeixinUtil {
//	private static Logger log=LoggerFactory.getLogger(WeixinUtil.class);
//	
//	/**
//	 * 发起https请求并获取结果
//	 * @param requestUrl
//	 * @param requestMethod(GET\PUT)
//	 * @param outputStr
//	 * @return JSONObject
//	 * */
//	
//	public static JSONObject httpRequest(String requestUrl,String requestMethod,String outputStr){
//		JSONObject jsonObject=null;
//		StringBuffer buffer=new StringBuffer();
//		try{
//			//创建SSLContext对象，并使用我们指定的信任管理器初始化
//			TrustManager[] tm={new MyX509TrustManager()};
//			SSLContext sslContext=SSLContext.getInstance("SSL","SunJSSE");
//			sslContext.init(null, tm, new java.security.SecureRandom());
//			
//			//从上述SSLContext对象中得到SSLSocketFactory对象
//			SSLSocketFactory ssf=sslContext.getSocketFactory();
//			
//			URL url=new URL(requestUrl);
//			HttpsURLConnection httpUrlConn=(HttpsURLConnection)url.openConnection();
//			httpUrlConn.setSSLSocketFactory(ssf);
//			httpUrlConn.setDoOutput(true);
//			httpUrlConn.setDoInput(true);
//			httpUrlConn.setUseCaches(true);
//			
//			//设置请求方式
//			httpUrlConn.setRequestMethod(requestMethod); 
//			if ("GET".equalsIgnoreCase(requestMethod)){
//				httpUrlConn.connect();  
//			}
//			
//			// 当有数据需要提交时
//			if (null != outputStr){
//				OutputStream outputStream = httpUrlConn.getOutputStream();
//				// 注意编码格式，防止中文乱码
//				outputStream.write(outputStr.getBytes("UTF-8")); 
//				outputStream.close();  
//			}
//			
//			// 将返回的输入流转换成字符串
//			InputStream inputStream = httpUrlConn.getInputStream();
//			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8"); 
//			BufferedReader bufferedReader = new BufferedReader(inputStreamReader); 
//			
//			String str = null;
//			while ((str = bufferedReader.readLine()) != null) {  
//				buffer.append(str); 
//			}
//			bufferedReader.close();
//			inputStreamReader.close();
//			// 释放资源   
//			inputStream.close();  
//			inputStream = null;
//			httpUrlConn.disconnect();
//			jsonObject =new JSONObject(buffer.toString());
//		}catch (ConnectException ce) {
//			log.error("Weixin server connection timed out.");  
//		}catch(Exception e){
//			log.error("Weixin server connection timed out.");  
//		}
//		return jsonObject;  
//	}
//	
//	// 获取access_token的接口地址（GET） 限200（次/天）
//	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx30761c2a52220d42&secret=a90bf0b4f94f8b0e8471c24920e78973";
////	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxbece9ad9baa9c35e&secret=62e0aba98f7e495f75207e68df4dda0d ";
//	/**
//	 * 获取access_token
//	 * 
//	 * @param appid 凭证
//	 * @param appsecret 密钥
//	 * @return
//	 * @throws JSONException 
//	 */
//	public static AccessToken getAccessToken(String appid, String appsecret) throws JSONException {
//		AccessToken accessToken = null;
//        System.out.println(appid+"    \n"+appsecret);
//		String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
//		System.out.println(requestUrl);
//		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
//		// 如果请求成功
//		if (null != jsonObject) {
//			try {
//				accessToken = new AccessToken();
//				accessToken.setToken(jsonObject.getString("access_token"));
//				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
//			} catch (JSONException e) {
//				accessToken = null;
//				// 获取token失败
//				log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
//			}
//		}
//		return accessToken;
//	}
//		
//		// 菜单创建（POST） 限100（次/天）
//		public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
//		//创建客服url
//		public static String custom_create_url="https://api.weixin.qq.com/customservice/kfaccount/add?access_token=ACCESS_TOKEN";
//
//		/**
//		 * 创建菜单
//		 * 
//		 * @param menu 菜单实例
//		 * @param accessToken 有效的access_token
//		 * @return 0表示成功，其他值表示失败
//		 * @throws JSONException 
//		 */
//		public static int createMenu(Menu menu, String accessToken) throws JSONException {
//			int result = 0;
//			System.out.println("access_token="+accessToken);
//			// 拼装创建菜单的url
//			String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
//			// 将菜单对象转换成json字符串
//			String jsonMenu = new JSONObject(menu).toString();
//			System.out.println("jsonMenu=\r\n"+jsonMenu);
//			// 调用接口创建菜单
//			JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);
//
//			if (null != jsonObject) {
//				if (0 != jsonObject.getInt("errcode")) {
//					result = jsonObject.getInt("errcode");
//					log.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
//				}
//			}
//			return result;
//		}
//		
//		/**
//		 * 创建客服
//		 * @param custom 客服数据
//		 * @param accessToken 有效的access_toke
//		 * @return 0表示成功，其他表示失败
//		 * @throws JSONException
//		 */
//		public static int createCustom(Custom custom,String accessToken) throws JSONException{
//			int result=0;
//			System.out.println("access_token="+accessToken);
//			// 拼装创建客服的url
//		    String url = custom_create_url.replace("ACCESS_TOKEN", accessToken);
//		    // 将客服对象转换成json字符串
//		 	String jsonCustom = new JSONObject(custom).toString();
//		 	System.out.println("createCustom.url="+url);
//		 	System.out.println("jsonMenu=\r\n"+jsonCustom);
//		    // 调用接口创建菜单
//		 	JSONObject jsonObject = httpRequest(url, "POST", jsonCustom);
//		 	if (null != jsonObject) {
//				if (0 != jsonObject.getInt("errcode")) {
//					result = jsonObject.getInt("errcode");
//					log.error("创建客服失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
//				}
//			}
//			return result;
//		}
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

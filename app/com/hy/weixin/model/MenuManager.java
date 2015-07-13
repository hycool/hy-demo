//package com.hy.weixin.model;
//
//import org.json.JSONException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.hy.weixin.pojo.Button;
//import com.hy.weixin.pojo.CommonButton;
//import com.hy.weixin.pojo.ComplexButton;
//import com.hy.weixin.pojo.Menu;
//import com.hy.weixin.util.AccessToken;
//import com.hy.weixin.util.WeixinUtil;
//
///**
// * 菜单管理器
// * */
//
//public class MenuManager {
//	private static Logger log=LoggerFactory.getLogger(MenuManager.class);
//	
//	public static void main(String[] args) throws JSONException{
//		//第三方用户凭证,第三方用户凭证密钥
//		//测试号
//		String appId="wx30761c2a52220d42";
//		String appSecret="a90bf0b4f94f8b0e8471c24920e78973";
//		
////		//个人号
////		String appId="wxbece9ad9baa9c35e";
////		String appSecret="62e0aba98f7e495f75207e68df4dda0d";
//		
//		//调用接口获取access_token;
//		AccessToken at=WeixinUtil.getAccessToken(appId, appSecret);
//		
//		if(null!=at){
//			//调用接口创建菜单
//			int result=WeixinUtil.createMenu(getMenu(), at.getToken());
//			
//			//判断菜单创建结果
//			if(0==result){
//				log.error("菜单创建成功");
//			}else{
//				log.error("菜单创建失败，错误码："+result);
//			}
//		}
//	}
//	
//	/**
//	 * 组装菜单数据
//	 * */
//	private static Menu getMenu(){
////		byte[] buff="快拿用户".getBytes();
////		System.out.println("buff.length="+buff.length);
//		//定义普通二级菜单
////		CommonButton btn11=new CommonButton();
////		btn11.setName("惊喜奖");
////		btn11.setType("click");
////		btn11.setKey("kuaiya_jxj");
//		
//		CommonButton btn12=new CommonButton();
//		btn12.setName("抢电影红包");
//		btn12.setType("click");
//		btn12.setKey("kuaiya_qhb");
//		
//		CommonButton btn13=new CommonButton();
//		btn13.setName("看电影花絮");
//		btn13.setType("view");
//		btn13.setUrl("http://www.letv.com/ptv/vplay/22751820.html#rd");
//		
//		CommonButton btn21 = new CommonButton();  
//		btn21.setName("兴趣部落");  
//		btn21.setType("view");  
//		btn21.setUrl("http://xiaoqu.qq.com/mobile/barindex.html?from=weixin&bid=161218");
//		
//		CommonButton btn31=new CommonButton();
//		btn31.setName("常见问题");
//		btn31.setType("view");
//		btn31.setUrl("http://bbs.kuaiya.cn/forum.php?mod=viewthread&tid=27389&extra=page%3D1");
//		
//		CommonButton btn32=new CommonButton();
//		btn32.setName("下载快牙");
//		btn32.setType("view");
//		btn32.setUrl("http://kuaiya.cn/i/");
//		
//		CommonButton btn33=new CommonButton();
//		btn33.setName("快牙介绍");
//		btn33.setType("click");
//		btn33.setKey("kuaiya_zx_js");
//		
//		CommonButton btn34=new CommonButton();
//		btn34.setName("用户反馈");
//		btn34.setType("click");
//		btn34.setKey("kuaiya_yhfk");
//		
//		//定义一级菜单
//		ComplexButton mainBtn1=new ComplexButton();
//		mainBtn1.setName("精彩活动");
//		mainBtn1.setSub_button(new CommonButton[]{btn12,btn13});
//		
//		ComplexButton mainBtn2=new ComplexButton();
//		mainBtn2.setName("兴趣部落");
//		mainBtn2.setSub_button(new CommonButton[]{btn21});
//		
//		ComplexButton mainBtn3=new ComplexButton();
//		mainBtn3.setName("牙粉服务");
//		mainBtn3.setSub_button(new CommonButton[]{btn31,btn32,btn33,btn34});
//		
//		//定义菜单栏
//		Menu menu=new Menu();
//		menu.setButton(new Button[]{mainBtn1,btn21,mainBtn3});
//		
//		return menu;
//	}
//	
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

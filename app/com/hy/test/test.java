package com.hy.test;



public class test {
	public static void main(String arg[]){
//		Calendar currentDate = new GregorianCalendar();     
//		currentDate.set(Calendar.HOUR_OF_DAY, 0);  
//		currentDate.set(Calendar.MINUTE, 0);  
//		currentDate.set(Calendar.SECOND, 0);  
//		long weeHours=currentDate.getTimeInMillis();
//		long lastTimestamp=currentDate.getTimeInMillis();
//		System.out.println("weeHours="+weeHours);  
//        
//		if("".equals("") && lastTimestamp>=weeHours){
//			System.out.println("***");
//		}
//		System.out.println("nowtime="+System.currentTimeMillis());
//		System.out.println("begin to execute");
//		String content="C ".trim().toLowerCase();
//		System.out.println("content.length="+content.length());
//		if(content.length() ==1 && content.matches("[a-dA-D]+")){
//			System.out.println("yes,ok");
//		}else{
//			System.out.println("no,it's not");
//		}
		
		//20xGuZf3mSg6CEoMhRbrhj71Rf6hLtLMP38bQcSNCEo0zFrsB4iGzCG4ipI14xkzfChSLTogVWl0bCU0-OFXA4h-o7WkOT71A2LyErt-atQ
		//test
//		String appId="wx30761c2a52220d42";
//		String appSecret="a90bf0b4f94f8b0e8471c24920e78973";
		//zapay
		String appId="wx43b2162041ec03f0";
		String appSecret="dc2abacc3035ecb3f63c494bfa6fe550";
		
		//调用接口获取access_token;
//		AccessToken at=WeixinUtil.getAccessToken(appId, appSecret);
//		System.out.println("access_token="+at.getToken()+"\r\n"+"expiresIn="+at.getExpiresIn());
		
//		System.out.println(MD5Util.getMd5("kuaiya123456"));
		String ip1="172.10.16.52";
		String ip2="172.152.45.56";
		System.out.println(ip1.split("\\.").length);
		System.out.println();
		System.out.println(ip1.substring(0,ip1.indexOf(ip1.split("\\.")[3])));
    	System.out.println(ip2.substring(0,ip2.indexOf(ip2.split("\\.")[3])));
	}

}

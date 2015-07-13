package com.hy.weixin.resp;

public class TextMessage extends BaseMessage{
	//消息回复内容
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
	
	
}

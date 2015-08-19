package com.hy.test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class getLocalIp {
	public static void main(String arg[]) throws UnknownHostException{
		System.out.println(InetAddress.getLocalHost().getHostAddress());
	}

}

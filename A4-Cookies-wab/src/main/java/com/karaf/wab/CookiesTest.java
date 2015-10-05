package com.karaf.wab;

import javax.servlet.http.Cookie;

public class CookiesTest {

	private static final String PARM_A4SSO_COOKIE = "A4Profile_A4SSO";
	
	public static void main(String[] args) {
		String a4SSOProfileResult = "test 132\n\r456";
		//String a4SSOProfileResult = "test 132/n/r456";
		Cookie a4ssoCookie = new Cookie(PARM_A4SSO_COOKIE, a4SSOProfileResult);
		a4ssoCookie.setVersion(1);
		System.out.println(a4ssoCookie.getValue());
		

	}

}

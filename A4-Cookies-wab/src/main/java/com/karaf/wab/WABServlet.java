package com.karaf.wab;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
/** A simple servlet */
public class WABServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PARM_A4SSO_COOKIE = "A4Profile_A4SSO";
 
	public void init(ServletConfig config) throws ServletException {
		System.out.println("Initializing the servlet, http://localhost:8181/test/karafWAB");
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("DO GET Success!");
		
		String a4SSOProfileResult = "test 132\n\r456";
		//String a4SSOProfileResult = "test";
		//Cookie a4ssoCookie = new Cookie(PARM_A4SSO_COOKIE, URLEncoder.encode(a4SSOProfileResult, "UTF-8"));
		Cookie a4ssoCookie = new Cookie(PARM_A4SSO_COOKIE, a4SSOProfileResult);
		//a4ssoCookie.setVersion(0);
		resp.addCookie(a4ssoCookie);
		
		
		
//		String cookieValue = "unkown#4?Wn5pZ1JwQnlLEGRJAgB4WQU%3D";
//		Cookie zedoCookie = new Cookie("cookiename", cookieValue);
//		zedoCookie.setMaxAge(31536000); // this is one year duration.
//		zedoCookie.setDomain("somedomain.com");
//		zedoCookie.setPath("/");
//		resp.addCookie(zedoCookie);
		
		
		resp.getWriter().write("Hello Cookies WAB!");
	}
}
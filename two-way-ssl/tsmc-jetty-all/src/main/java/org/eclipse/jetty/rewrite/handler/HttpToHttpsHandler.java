package org.eclipse.jetty.rewrite.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;


public class HttpToHttpsHandler  extends AbstractHandler{
    
	//20150920 SHLINZZI add	
    public void handle(String target, Request baseRequest, HttpServletRequest request, 
    		HttpServletResponse response) throws IOException, ServletException
    {
        if (!baseRequest.isSecure() && target.startsWith("/system/console"))
        {
        	response.sendError(HttpStatus.FORBIDDEN_403,"!Secure");
        }
    }

}

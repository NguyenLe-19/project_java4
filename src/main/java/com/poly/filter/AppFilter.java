package com.poly.filter;



import java.io.IOException;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(filterName = "appFilter", urlPatterns = {"/*","/login"},dispatcherTypes = DispatcherType.REQUEST)
public class AppFilter implements HttpFilter{

	@Override
	public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("app filter running");
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		RRSharer.add(req, resp);
		chain.doFilter(req, resp);
		RRSharer.remove(req, resp);
		
	}

	

}

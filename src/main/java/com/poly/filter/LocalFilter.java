package com.poly.filter;

import java.io.IOException;



import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(filterName = "LocalFilter", urlPatterns = { "/*" })
public class LocalFilter implements HttpFilter {

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;

//		if (req.getSession().getAttribute("lang") == null) {
//			req.getSession().setAttribute("lang", "vi");
//		}
		String lang = req.getParameter("lang");
		if (lang != null) {
			req.getSession().setAttribute("lang", lang);
		}
		chain.doFilter(request, response);

	}

}

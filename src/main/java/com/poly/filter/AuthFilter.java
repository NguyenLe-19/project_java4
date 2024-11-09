package com.poly.filter;

import java.io.IOException;

import com.poly.entity.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(filterName = "AuthFilter", urlPatterns = { "/user/index", "/user/edit/*", "/user/create", "/user/update",
		"/user/delete", "/user/reset", "/video/index", "/video/edit/*", "/video/create", "/video/update", "/video/delete", "/video/reset" })

public class AuthFilter implements HttpFilter {

	@Override
	public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		String uri = req.getRequestURI();
		User user = (User) req.getSession().getAttribute("user");
		String error = "";
		System.out.println("AuthFilter running");
		System.out.println(user);
		if (user == null) {
			error = resp.encodeURL("Please login to use this function!");
			System.out.println("Vui lòng đăng nhập!");
		} else if (!user.getIsAdmin() && (uri.contains("/user/index") || uri.contains("/user/edit/*") || uri.contains("/user/create") || uri.contains("/user/update")
				|| uri.contains("/user/delete") || uri.contains("/user/reset") || uri.contains("/video/index") || uri.contains("/video/edit/*") || uri.contains("/video/create") || uri.contains("/video/update")
				|| uri.contains("/video/delete") || uri.contains("/video/reset"))) {
			System.out.println("Vui lòng đăng nhập với vai trò Admin");
			error = resp.encodeURL("Please login with admin role");

		}

		if (!error.isEmpty()) {
//			req.setCharacterEncoding("utf-8");
//			resp.setCharacterEncoding("utf-8");
//			req.getSession().setAttribute("securi", uri);
			req.getSession().setAttribute("securi", error);
			System.out.println("Error!");
			resp.sendRedirect("/views/user/login.jsp?error=" + resp.encodeURL(error));
//			req.getRequestDispatcher("/views/user/login.jsp").forward(req, resp);
		} else {
			System.out.println("No Error!");
			chain.doFilter(req, resp);
			//req.getSession().setMaxInactiveInterval(5*60);
		}

	}

}

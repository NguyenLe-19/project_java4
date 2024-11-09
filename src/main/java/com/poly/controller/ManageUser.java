package com.poly.controller;

import java.io.IOException;

import org.apache.commons.beanutils.BeanUtils;

import com.poly.entity.User;
import com.poly.service.UserService;
import com.poly.service.impl.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ManageUser
 */
@WebServlet({ "/user/index", "/user/edit/*", "/user/create", "/user/update", "/user/delete", "/user/reset" })
public class ManageUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final UserService userService = new UserServiceImpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ManageUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = new User();
		String uri = req.getRequestURI();

		if (uri.contains("edit")) {
			// String ww = uri.substring(uri.lastIndexOf("/")+1);
			String username = req.getParameter("username");
			user = userService.findByUsername(username);
			req.setAttribute("form", user);

		} else if (uri.contains("create")) {
			try {
				BeanUtils.populate(user, req.getParameterMap());
				
				String username = req.getParameter("username");
				String password = req.getParameter("password");
				String email = req.getParameter("email");
				Boolean admin = req.getParameter("admin")!= null ;
				Boolean active = req.getParameter("active")!= null;


				User createdUser = userService.createU(username, password, email, admin, active);
				req.setAttribute("message", createdUser != null ? "Thêm mới thành công" : "Thêm mới thất bại");
			} catch (Exception e) {
				req.setAttribute("message", "Thêm mới thất bại");
			}
		} else if (uri.contains("update")) {
			
			try {
				BeanUtils.populate(user, req.getParameterMap());

				String username = req.getParameter("username");
				String password = req.getParameter("password");
				String email = req.getParameter("email");
				Boolean admin = req.getParameter("admin")!= null ;
				Boolean active = req.getParameter("active")!= null;
		        
				System.out.println(admin +" " +active);
				userService.updateU(username, password, email, admin, active);
				req.setAttribute("message", "Cập nhật thành công");
			} catch (Exception e) {
				req.setAttribute("message", "Cập nhật thất bại");
			}
		} else if (uri.contains("delete")) {
			try {
				String username = req.getParameter("username");
				userService.delete(username);
				req.setAttribute("message", "Xóa thành công");
			} catch (Exception e) {
				req.setAttribute("message", "Xóa thất bại");
			}
		} else if (uri.contains("reset")) {
			req.setAttribute("form", user);
		}

		req.setAttribute("form", user);
		req.setAttribute("items", userService.findAll());

		req.getRequestDispatcher("/views/user/user.jsp").forward(req, resp);
	}
}

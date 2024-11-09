package com.poly.controller;

import java.io.IOException;
import java.util.List;

import com.poly.constant.SesstionAttr;
import com.poly.entity.User;
import com.poly.entity.Video;
import com.poly.service.EmailService;
import com.poly.service.UserService;
import com.poly.service.VideoService;
import com.poly.service.impl.EmailServiceImpl;
import com.poly.service.impl.UserServiceImpl;
import com.poly.service.impl.VideoServiceImpl;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class UserController
 */
@WebServlet(urlPatterns = { "/login", "/logout", "/register", "/forgotPass", "/changePass" })
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService = new UserServiceImpl();
	private EmailService emailService = new EmailServiceImpl();
	private VideoService videoService = new VideoServiceImpl();


	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String path = request.getServletPath();
		switch (path) {
		case "/login":
			doGetLogin(request, response);
			break;
		case "/register":
			doGetRegister(request, response);
			break;
		case "/logout":
			doGetLogout(session, request, response);
			break;
		case "/forgotPass":
			doGetForgotPass(request, response);
			break;
		case "/changePass":
			doGetChangePass(session, request, response);
			break;

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String path = request.getServletPath();
		switch (path) {
		case "/login":
			doPostLogin(session, request, response);
			break;
		case "/register":
			doPostRegister(session, request, response);
			break;
		case "/forgotPass":
			doPostForgotPass(request, response);
			break;
		case "/changePass":
			doPostChangePass(session, request, response);
			break;
		}
	}

	private void doGetLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/user/login.jsp");
		requestDispatcher.forward(request, response);
	}

	private void doGetRegister(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/user/register.jsp");
		requestDispatcher.forward(request, response);
	}

	private void doGetLogout(HttpSession session, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		session.removeAttribute(SesstionAttr.CURRENT_USER);
		response.sendRedirect("HomeController");
	}

	private void doPostLogin(HttpSession session, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		User user = userService.login(username, password);

		if (user != null) {
			session.setAttribute(SesstionAttr.CURRENT_USER, user);
			response.sendRedirect("HomeController");
		} else {
			response.sendRedirect("login");
		}

	}

	private void doPostRegister(HttpSession session, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");

		User user = userService.create(username, password, email);

		if (user != null) {
			emailService.sendEmail(getServletContext(), user, "welcome");
			session.setAttribute(SesstionAttr.CURRENT_USER, user);
			response.sendRedirect("HomeController");
		} else {
			response.sendRedirect("register");
		}

	}

	private void doGetForgotPass(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/user/forgot-pass.jsp");
		requestDispatcher.forward(request, response);
	}

	private void doPostForgotPass(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			String email = (String) request.getParameter("email");

			User userWithNewPass = null;
			if (email != null) {
				userWithNewPass = userService.resetPassword(email);
				System.out.println("Email received in resetPassword: " + email);
				emailService.sendEmail(getServletContext(), userWithNewPass, "forgot");// enum
				response.setStatus(204);
			} else {
				response.setStatus(404);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void doGetChangePass(HttpSession session, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/user/index.jsp");
		requestDispatcher.forward(request, response);
	}
	
	private void doPostChangePass(HttpSession session, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
//		response.setContentType("application/json");
		String currentPass = request.getParameter("currentPass");
		String newPass = request.getParameter("newPass");

		System.out.println(currentPass + " " + newPass);
		User currentUser = (User) session.getAttribute(SesstionAttr.CURRENT_USER);
		String uri = request.getRequestURI();
		
		if (currentUser.getPassword().equals(currentPass)) {
			currentUser.setPassword(newPass);
			User updatedUser = userService.update(currentUser);
			List<Video> videos = videoService.findAll();
			request.setAttribute("videos", videos);		
			if (updatedUser != null) {
				session.setAttribute(SesstionAttr.CURRENT_USER, updatedUser);
				request.setAttribute("alert", "Cập nhật thành công");
						
				//response.setStatus(204);
			} else {
				//response.setStatus(400);
				request.setAttribute("alert", "Cập nhật thất bại");
				
			}
		} else {
			//response.setStatus(400);
			request.setAttribute("alert", "Cập nhật thất bại");
			
		}
		doGet(request, response);
	}
}

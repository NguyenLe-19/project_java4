package com.poly.controller.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.constant.SesstionAttr;
import com.poly.dto.VideoLikedInfor;
import com.poly.entity.User;
import com.poly.service.StatsService;
import com.poly.service.UserService;
import com.poly.service.impl.StatsServiceImpl;
import com.poly.service.impl.UserServiceImpl;

/**
 * Servlet implementation class HomeController
 */
@WebServlet(name = "HomeControllerOfAdmin", urlPatterns = { "/admin", "/admin/favorites" })
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StatsService statsService = new StatsServiceImpl();
	private UserService userService = new UserServiceImpl();
	List<User> users = new ArrayList<User>();
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HomeController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute(SesstionAttr.CURRENT_USER);

		if (currentUser != null && currentUser.getIsAdmin() == Boolean.TRUE) {

			String path = request.getServletPath();
			switch (path) {
			case "/admin":
				doGetHome(request, response);
				break;
			case "/admin/favorites":
				doGetFavorites(request, response);
				break;
				
			}

		} else {
			response.sendRedirect("HomeController");
		}

	}

	private void doGetHome(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<VideoLikedInfor> videos = statsService.findVideoLikedInfor();
		request.setAttribute("videos", videos);
		request.getRequestDispatcher("/views/admin/home.jsp").forward(request, response);
	}

	// localhost:8080/asmjv4/admin/favorites?href=${videoHref}
	private void doGetFavorites(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    PrintWriter out = response.getWriter();
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("application/json");
	    String videoHref = request.getParameter("href");
	    users = userService.findUserLikedByVideoHref(videoHref);

	    if (users.isEmpty()) {
	        response.setStatus(400);
	    } else {
	        ObjectMapper mapper = new ObjectMapper();
	        String dataResponse = mapper.writeValueAsString(users);

	        response.setStatus(200);
	        out.print(dataResponse);
	        out.flush();
	    }
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("/views/admin/home.jsp").forward(request, response);

	}

}

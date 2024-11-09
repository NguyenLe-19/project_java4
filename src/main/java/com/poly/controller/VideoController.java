package com.poly.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.poly.constant.SesstionAttr;
import com.poly.entity.History;
import com.poly.entity.User;
import com.poly.entity.Video;
import com.poly.service.HistoryService;
import com.poly.service.VideoService;
import com.poly.service.impl.HistoryServiceImpl;
import com.poly.service.impl.VideoServiceImpl;
import com.poly.util.SendMailUtil;

import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class VideoController
 */
@WebServlet({"/video", "/video/share"})
public class VideoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private VideoService videoService = new VideoServiceImpl();
	private HistoryService historyService = new HistoryServiceImpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public VideoController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String actionParam = request.getParameter("action");
		String href = request.getParameter("id");
		HttpSession session = request.getSession();
		switch (actionParam) {
		case "watch":
			doGetWatch(session, href, request, response);
			break;
		case "like":
			doGetLike(session, href, request, response);
			break;
			// Xử lý yêu cầu chia sẻ video
		case "share":
		    String email = request.getParameter("email");
		    doShareVideo(session, href, email, request, response);
		    break;

		}

	}

	// http://localhost:8080/asmjv4/video?action=watch&id={href}
	private void doGetWatch(HttpSession session, String href, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Video video = videoService.findByHref(href);
		session.setAttribute(SesstionAttr.CURRENT_VIDEO, video.getHref());
		request.setAttribute("video", video);

		User currentUser = (User) session.getAttribute(SesstionAttr.CURRENT_USER);
		if (currentUser != null) {
			History history = historyService.create(currentUser, video);
			request.setAttribute("flagLikedBtn", history.getIsLiked());
		}
		List<Video> videos = videoService.findAll();
		request.setAttribute("videos", videos);
		request.getRequestDispatcher("/views/user/video-detail.jsp").forward(request, response);
	}

	// http://localhost:8080/asmjv4/video?action=like&id={href}
	private void doGetLike(HttpSession session, String href, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		User currentUser = (User) session.getAttribute(SesstionAttr.CURRENT_USER);
		boolean result = historyService.updateLikeOrUnlike(currentUser, href);
		if (result == true) {
			response.setStatus(204);// succeed but not response data
		} else {
			response.setStatus(400);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String href = (String) session.getAttribute(SesstionAttr.CURRENT_VIDEO);
		
		String url = request.getRequestURL().toString();
		if (url.contains("video")) {
//			System.out.println(video.getHref());
			response.sendRedirect("http://localhost:8080/asmjv4/video?action=watch&id="+href);
		}
	}

	private void doShareVideo(HttpSession session, String href, String email, HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
		    
		    // Lấy thông tin video từ session
		    Video video = videoService.findByHref(href);
		    String videoUrl = "https://www.youtube.com/embed/" + video.getHref();

		    // Lấy thông tin người dùng hiện tại từ session
		    User currentUser = (User) session.getAttribute(SesstionAttr.CURRENT_USER);

		    // Lấy thông tin cấu hình SMTP từ ServletContext
		    ServletContext context = request.getServletContext();
		    String host = context.getInitParameter("host");
		    String port = context.getInitParameter("port");
		    String user = context.getInitParameter("user");
		    String pass = context.getInitParameter("pass");

		    // Tạo nội dung email
		    String subject = currentUser.getUsername() + " đã chia sẻ 1 video cho bạn";
		    String content = "Video " + videoUrl + " hay lắm";

		    try {
		        // Gửi email
		        SendMailUtil.sendEmail(host, port, user, pass, email, subject, content);
		        System.out.println("Share thành công cho: " + email);
		        response.setStatus(204); // Đã thành công nhưng không có dữ liệu trả về
		    } catch (Exception e) {
		        e.printStackTrace();
		        System.out.println("Lỗi khi chia sẻ video");
		        response.setStatus(400); // Lỗi
		    }
		}

}

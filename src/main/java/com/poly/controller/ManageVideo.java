package com.poly.controller;

import java.io.File;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import org.apache.commons.beanutils.BeanUtils;

import com.poly.entity.Video;
import com.poly.service.VideoService;
import com.poly.service.impl.VideoServiceImpl;

/**
 * Servlet implementation class ManageVideo
 */
@WebServlet({ "/video/index", "/video/edit/*", "/video/create", "/video/update", "/video/delete", "/video/reset" })
public class ManageVideo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private VideoService videoService = new VideoServiceImpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ManageVideo() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Video video = new Video();
		String uri = request.getRequestURI();

		if (uri.contains("edit")) {
			// String ww = uri.substring(uri.lastIndexOf("/")+1);
			String videoHref = request.getParameter("href");
			video = videoService.findByHref(videoHref);
			request.setAttribute("form", video);

		} else if (uri.contains("create")) {
			

			try {
		
				
				BeanUtils.populate(video, request.getParameterMap());
				String title = request.getParameter("title");
				String href = request.getParameter("href");
				String poster = request.getParameter("poster");
				String description = request.getParameter("description");
				video.setTitle(title);
				video.setHref(href);
				video.setDescription(description);
				video.setPoster("https://img.youtube.com/vi/" + href + "/0.jpg");

				Video createdVideo = videoService.createV(video);
				request.setAttribute("message", createdVideo != null ? "Thêm mới thành công" : "Thêm mới thất bại");
				
			} catch (Exception e) {
//				request.setAttribute("message", "Error");
				e.printStackTrace();
			}
		} else if (uri.contains("update")) {

			try {
				BeanUtils.populate(video, request.getParameterMap());

				String title = request.getParameter("title");
				String href = request.getParameter("href");
				String poster = request.getParameter("poster");
				String description = request.getParameter("description");
				Boolean active = request.getParameter("active")!=null;
				video = videoService.findByHref(href);
				video.setTitle(title);
				video.setHref(href);
				video.setDescription(description);
				video.setPoster("https://img.youtube.com/vi/" + href + "/0.jpg");
				video.setIsActive(active);
				videoService.update(video);
				request.setAttribute("message", "Cập nhật thành công");
			} catch (Exception e) {
				request.setAttribute("message", "Cập nhật thất bại");
			}
		} else if (uri.contains("delete")) {
			try {
				String videoHref = request.getParameter("href");
				videoService.delete(videoHref);
				request.setAttribute("message", "Xóa thành công");
			} catch (Exception e) {
				request.setAttribute("message", "Xóa thất bại");
			}
		} else if (uri.contains("reset")) {
			request.setAttribute("form", video);
		}

		request.setAttribute("form", video);
		request.setAttribute("items", videoService.findAll());

		request.getRequestDispatcher("/views/admin/video.jsp").forward(request, response);

	}

}

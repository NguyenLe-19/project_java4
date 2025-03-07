package com.poly.service.impl;

import java.util.List;

import com.poly.dao.VideoDao;
import com.poly.dao.impl.VideoDaoImpl;
import com.poly.entity.Video;
import com.poly.service.VideoService;

public class VideoServiceImpl implements VideoService {

	private VideoDao dao;
	
	public VideoServiceImpl() {
		dao = new VideoDaoImpl();
		
	}

	@Override
	public Video findById(Integer id) {
		return dao.findById(id);
	}

	@Override
	public Video findByHref(String href) {
		return dao.findByHref(href);
	}

	@Override
	public List<Video> findAll() {
		return dao.findAll();
	}

	@Override
	public List<Video> findAll(int pageNumber, int pageSize) {
		return dao.findAll(pageNumber, pageSize);
	}

	@Override
	public Video create(String title, String href, String poster, String description) {
		Video entity= new Video();
		entity.setTitle(title);
		entity.setHref(href);
		entity.setPoster(poster);
		entity.setDescription(description);
		entity.setIsActive(Boolean.TRUE);
		entity.setViews(0);
		entity.setShares(0);
		return dao.create(entity);
	}

	@Override
	public Video update(Video entity) {
		return dao.update(entity);
	}

	@Override
	public Video delete(String href) {
		Video entity = findByHref(href);
		entity.setIsActive(Boolean.FALSE);
		return dao.update(entity);
	}

	@Override
	public Video createV(Video entity) {
		entity.setIsActive(Boolean.TRUE);
		entity.setViews(0);
		entity.setShares(0);
		return dao.create(entity);
	}



}

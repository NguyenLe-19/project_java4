package com.poly.service;

import java.util.List;

import com.poly.entity.Video;

public interface VideoService {
	Video findById(Integer id);

	Video findByHref(String href);

	List<Video> findAll();

	List<Video> findAll(int pageNumber, int pageSize);

	Video create(String title, String href, String poster, String description);

	Video createV(Video entity);
	
	Video update(Video entity);

	Video delete(String href);
}

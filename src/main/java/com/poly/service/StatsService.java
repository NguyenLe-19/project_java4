package com.poly.service;

import java.util.List;

import com.poly.dto.VideoLikedInfor;

public interface StatsService {
	List<VideoLikedInfor> findVideoLikedInfor();
}

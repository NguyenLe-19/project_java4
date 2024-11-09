package com.poly.dao;

import java.util.List;

import com.poly.dto.VideoLikedInfor;

public interface StatsDao {

	List<VideoLikedInfor> findVideoLikedInfor();
		
}

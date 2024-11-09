package com.poly.dao.impl;

import java.util.List;

import com.poly.dao.AbstractDAO;
import com.poly.dao.HistoryDao;
import com.poly.entity.History;

public class HistoryDaoImpl extends AbstractDAO<History> implements HistoryDao {

	public HistoryDaoImpl() {
	}

	@Override
	public List<History> findByUser(String username) {
		String sql = "Select o From History o where o.user.username = ?0 AND o.video.isActive = 1"
				+ " ORDER BY o.viewDate DESC";
		return super.findMany(History.class, sql, username);
	}

	@Override
	public List<History> findByUserAndIsLiked(String username) {
		String sql = "Select o From History o where o.user.username = ?0 AND o.isLiked = 1"
				+ " AND o.video.isActive = 1"
				+ " ORDER BY o.viewDate DESC";
		return super.findMany(History.class, sql, username);
	}

	@Override
	public History findByUserIdAndVideoId(Integer userID, Integer videoID) {
		String sql = "SELECT o FROM History o where o.user.id = ?0 and o.video.id = ?1"
				+ " and o.video.isActive =1";
		return super.findOne(History.class, sql, userID, videoID);
	}

}

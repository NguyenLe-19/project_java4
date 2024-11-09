package com.poly.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.poly.dao.AbstractDAO;
import com.poly.dao.StatsDao;
import com.poly.dto.VideoLikedInfor;

public class StatsDaoImpl extends AbstractDAO<Object[]> implements StatsDao {

	public StatsDaoImpl() {
	}

	@Override
	public List<VideoLikedInfor> findVideoLikedInfor() {
		String sql = "select v.id, v.title, v.href, sum(cast(h.isLiked as int)) as totalLike"
				+ " from video v left join history h on v.id = h.videoID" + " where v.isActive = 1"
				+ " group by  v.id, v.title, v.href" + " ORDER BY sum(cast(h.isLiked as int)) desc";
		List<Object[]> objects = super.findManyByNativeQuery(sql);

		List<VideoLikedInfor> result = new ArrayList<>();
		objects.forEach(object -> {
			VideoLikedInfor videoLikedInfor = setDataVideoLikedInfor(object);
			result.add(videoLikedInfor);
		});
		return result;
	}

	private VideoLikedInfor setDataVideoLikedInfor(Object[] object) {
		VideoLikedInfor videoLikedInfor = new VideoLikedInfor();
		videoLikedInfor.setVideoID((Integer) object[0]);
		videoLikedInfor.setTitle((String) object[1]);
		videoLikedInfor.setHref((String) object[2]);
		videoLikedInfor.setTotalLike((Integer) object[3] == null ? 0 : (Integer) object[3]);
		return videoLikedInfor;
	}

	
}

package com.poly.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.poly.constant.videoHrefParams;
import com.poly.dao.UserDao;
import com.poly.dao.impl.UserDaoImpl;
import com.poly.entity.User;
import com.poly.service.UserService;

public class UserServiceImpl implements UserService {
	private UserDao dao;

	public UserServiceImpl() {
		dao = new UserDaoImpl();
	}

	@Override
	public User findById(Integer id) {
		return dao.findById(id);
	}

	@Override
	public User findByEmail(String email) {
		return dao.findByEmail(email);
	}

	@Override
	public User findByUsername(String username) {
		return dao.findByUsername(username);
	}

	@Override
	public User login(String username, String password) {
		return dao.findByUsernameAndPassword(username, password);
	}

	@Override
	public User resetPassword(String email) {
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		User existUser = findByEmail(email);
		if (existUser != null) {
			String newPass = String.valueOf((int) (Math.random() * ((9999 - 1000) - 1)) + 1000);
			existUser.setPassword(newPass);
			return dao.update(existUser);
		}
			return null;
	}

	@Override
	public List<User> findAll() {
		return dao.findAll();
	}

	@Override
	public List<User> findAll(int pageNumber, int pageSize) {
		return dao.findAll(pageNumber, pageSize);
	}

	@Override
	public User create(String username, String password, String email) {
		User newUser = new User();
		newUser.setUsername(username);
		newUser.setPassword(password); // bcrypt md5
		newUser.setEmail(email);
		newUser.setIsAdmin(Boolean.FALSE);
		newUser.setIsActive(Boolean.TRUE);
		return dao.create(newUser);
	}

	@Override
	public User updateU(String username, String password, String email, Boolean admin, Boolean active) {
		User userUpdate = findByUsername(username);
		userUpdate.setPassword(password);
		userUpdate.setEmail(email);
		userUpdate.setIsAdmin(admin);
		if(active != userUpdate.getIsActive()) {
			userUpdate.setIsActive(active);
		}
		
		return dao.update(userUpdate);
	}

	@Override
	public User delete(String username) {
		User user = dao.findByUsername(username);
//		user.setIsActive(Boolean.FALSE);
//		return dao.update(user);
		return dao.delete(user);
	}

	@Override
	public List<User> findUserLikedByVideoHref(String href) {
		Map<String, Object> params = new HashMap<>();
		params.put(videoHrefParams.VIDEO_HREF_PARAMS, href);
		return dao.findUsersLikedByVideoHref(params);
	}

	@Override
	public User createU(String username, String password, String email, Boolean admin, Boolean active) {
		User user =new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		user.setIsAdmin(admin);
		user.setIsActive(active);
		return dao.create(user);
	}

	@Override
	public User update(User entity) {
		return dao.update(entity);
	}

	@Override
	public User createS(User entity) {
		// TODO Auto-generated method stub
		return dao.create(entity);
	}

}

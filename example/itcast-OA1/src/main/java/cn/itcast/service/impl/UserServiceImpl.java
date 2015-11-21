package cn.itcast.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.dao.IUserDao;
import cn.itcast.domain.PageBean;
import cn.itcast.domain.User;
import cn.itcast.service.IUserService;
import cn.itcast.utils.HQLHelper;
@Service
@Transactional
public class UserServiceImpl implements IUserService {
	@Resource
	private IUserDao userDao;

	public List<User> findAll() {
		return userDao.findAll();
	}

	@Override
	public void delete(User model) {
		userDao.delete(model.getId());
	}

	@Override
	public void save(User model) {
		userDao.save(model);
		
	}

	@Override
	public User getById(Long id) {
		return userDao.getById(id);
	}

	@Override
	public void update(User model) {
		userDao.update(model);
	}

	@Override
	public int findByLoginName(String loginName) {
		return userDao.findByLoginName(loginName);
	}

	@Override
	public User login(User model) {
		return userDao.login(model);
	}

	@Override
	public PageBean getPageBean(HQLHelper hh, int currentPage) {
		return userDao.getPageBean(hh, currentPage);
	}
}

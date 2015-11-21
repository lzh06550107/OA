package cn.itcast.service;


import java.util.List;

import cn.itcast.domain.PageBean;
import cn.itcast.domain.User;
import cn.itcast.utils.HQLHelper;

public interface IUserService {

	public List<User> findAll();

	public void delete(User model);

	public void save(User model);

	public User getById(Long id);

	public void update(User model);

	public int findByLoginName(String loginName);

	public User login(User model);

	public PageBean getPageBean(HQLHelper hh, int currentPage);

	
}

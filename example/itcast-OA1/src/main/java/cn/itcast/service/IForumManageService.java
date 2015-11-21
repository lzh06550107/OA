package cn.itcast.service;

import java.util.List;

import cn.itcast.domain.Forum;
import cn.itcast.domain.PageBean;
import cn.itcast.utils.HQLHelper;

public interface IForumManageService {

	public List<Forum> findAll();

	public void delete(Forum model);

	public void save(Forum model);

	public Forum getById(Long id);

	public void update(Forum model);

	public void moveUp(Long id);
	
	public void moveDown(Long id);

	public PageBean getPageBean(HQLHelper hh, int currentPage);

}

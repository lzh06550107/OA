package cn.itcast.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.dao.IForumManageDao;
import cn.itcast.domain.Forum;
import cn.itcast.domain.PageBean;
import cn.itcast.service.IForumManageService;
import cn.itcast.utils.HQLHelper;
@Service
@Transactional
public class ForumManageServiceImpl implements IForumManageService {
	@Resource
	private IForumManageDao forumManageDao;

	public List<Forum> findAll() {
		return forumManageDao.findAll();
	}

	public void delete(Forum model) {
		forumManageDao.delete(model.getId());
	}

	@Override
	public void save(Forum model) {
		forumManageDao.save(model);
		
	}

	public Forum getById(Long id) {
		return forumManageDao.getById(id);
	}

	@Override
	public void update(Forum model) {
		forumManageDao.update(model);
	}

	public void moveUp(Long id) {
		forumManageDao.moveUp(id);
	}
	
	public void moveDown(Long id){
		forumManageDao.moveDown(id);
	}

	@Override
	public PageBean getPageBean(HQLHelper hh, int currentPage) {
		return forumManageDao.getPageBean(hh, currentPage);
	}

}

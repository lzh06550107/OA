package cn.itcast.service;

import java.util.List;

import cn.itcast.domain.Forum;
import cn.itcast.domain.PageBean;
import cn.itcast.utils.HQLHelper;

public interface IForumService {

	public List<Forum> findAll();

	public Forum getById(Long id);

	public PageBean getPageBean(HQLHelper hh, int currentPage);

}

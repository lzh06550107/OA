package cn.itcast.service;

import java.io.InputStream;

import cn.itcast.domain.Application;
import cn.itcast.domain.PageBean;
import cn.itcast.utils.HQLHelper;

public interface IApplicationService {
	//分页
	public PageBean getPageBean(HQLHelper hh, int currentPage);

	public InputStream getInputStreamById(Long applicationId);

	public Application getById(Long applicationId);

}

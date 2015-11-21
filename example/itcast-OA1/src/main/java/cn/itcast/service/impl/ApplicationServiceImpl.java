package cn.itcast.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.dao.IApplicationDao;
import cn.itcast.domain.Application;
import cn.itcast.domain.PageBean;
import cn.itcast.service.IApplicationService;
import cn.itcast.utils.HQLHelper;
@Service
@Transactional
public class ApplicationServiceImpl implements IApplicationService {
	@Resource
	private IApplicationDao applicationDao;
	@Override
	public PageBean getPageBean(HQLHelper hh, int currentPage) {
		return applicationDao.getPageBean(hh, currentPage);
	}
	@Override
	public InputStream getInputStreamById(Long applicationId) {
		Application application = applicationDao.getById(applicationId);
		String filePath = application.getFilePath();
		InputStream inputStream = null;
		try {
			inputStream =  new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return inputStream;
	}
	@Override
	public Application getById(Long applicationId) {
		return applicationDao.getById(applicationId);
	}

}

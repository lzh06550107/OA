package cn.itcast.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.dao.ITemplateDao;
import cn.itcast.domain.Template;
import cn.itcast.service.ITemplateService;
@Service
@Transactional
public class TemplateServiceImpl implements ITemplateService {
	@Resource
	private ITemplateDao templateDao;

	@Override
	public void save(Template model) {
		templateDao.save(model);
	}

	@Override
	public void delete(Long id) {
		templateDao.delete(id);
		
	}

	@Override
	public List<Template> findAll() {
		return templateDao.findAll();
	}

	@Override
	public Template getById(Long id) {
		return templateDao.getById(id);
	}

	@Override
	public void update(Template model) {
		templateDao.update(model);
		
	}
	/**
	 * 根据模板id获取此模板对应的文件输入流
	 */
	@Override
	public InputStream getInputStreamById(Long id) {
		Template template = templateDao.getById(id);
		String filePath = template.getFilePath();
		InputStream in = null;
		try {
			in = new FileInputStream(new File(filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return in;
	}
}

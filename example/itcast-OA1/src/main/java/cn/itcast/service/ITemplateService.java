package cn.itcast.service;

import java.io.InputStream;
import java.util.List;

import cn.itcast.domain.Template;

public interface ITemplateService {
	/**
	 * 保存模板对象
	 * @param model
	 */
	public void save(Template model);

	public void delete(Long id);

	public List<Template> findAll();

	public Template getById(Long id);

	public void update(Template model);

	public InputStream getInputStreamById(Long id);

}

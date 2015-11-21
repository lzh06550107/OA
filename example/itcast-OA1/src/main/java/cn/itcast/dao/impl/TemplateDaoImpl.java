package cn.itcast.dao.impl;

import java.io.File;

import org.springframework.stereotype.Repository;

import cn.itcast.base.BaseDaoImpl;
import cn.itcast.dao.ITemplateDao;
import cn.itcast.domain.Template;
@Repository
public class TemplateDaoImpl extends BaseDaoImpl<Template> implements ITemplateDao {
	/*
	 * 重载delete方法，删除记录的同时删除文件
	 * @see cn.itcast.base.BaseDaoImpl#delete(java.lang.Long)
	 */
	@Override
	public void delete(Long id) {
		Template template = this.getById(id);
		String filePath = template.getFilePath();
		
		//删除文件
		File file = new File(filePath);
		if(file.exists()){
			file.delete();
		}
		//必须调用父类的方法，否则死循环
		super.delete(id); //输出数据库中的数据
	}	
}

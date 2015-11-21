package cn.itcast.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.jbpm.api.ProcessDefinition;

public interface IProcessDefinitionService {
	/**
	 * 查询最新版本的审批流程列表
	 * @return
	 */
	public List<ProcessDefinition> findLastList();
	/**
	 * 部署流程定义
	 * @param sourceFile
	 */
	public void deploy(File resourceFile);
	/**
	 * 根据key值删除流程定义
	 * @param key
	 */
	public void deleteByKey(String key);
	/**
	 * 根据流程定义的id获取此流程定义对应的图片文件输入流
	 * @param id
	 * @return
	 */
	public InputStream getImageInputStream(String id);

}

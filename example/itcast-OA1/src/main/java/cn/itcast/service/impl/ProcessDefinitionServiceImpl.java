package cn.itcast.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;

import org.jbpm.api.NewDeployment;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.ProcessEngine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.service.IProcessDefinitionService;
@Service
@Transactional
public class ProcessDefinitionServiceImpl implements IProcessDefinitionService {
	@Resource
	private ProcessEngine processEngine;

	@Override
	public List<ProcessDefinition> findLastList() {
		//获得流程定义查询对象
		ProcessDefinitionQuery query = processEngine.getRepositoryService().createProcessDefinitionQuery();
		//按版本排序
		query.orderAsc(ProcessDefinitionQuery.PROPERTY_VERSION);
		List<ProcessDefinition> list = query.list();
		
		Map<String, ProcessDefinition> map = new HashMap<>();
		for(ProcessDefinition pd : list){
			map.put(pd.getKey(), pd);
		}
		
		return new ArrayList<ProcessDefinition>(map.values());
	}

	@Override
	public void deploy(File resourceFile) {
		//获得部署对象
		NewDeployment deployment = processEngine.getRepositoryService().createDeployment();
		ZipInputStream zipInputStream = null;
		try{
			zipInputStream = new ZipInputStream(new FileInputStream(resourceFile));
			deployment.addResourcesFromZipInputStream(zipInputStream);
			deployment.deploy();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}finally{
			if(zipInputStream != null){
				try {
					zipInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	@Override
	public void deleteByKey(String key) {
		//根据key查询流程定义
		ProcessDefinitionQuery query = processEngine.getRepositoryService().createProcessDefinitionQuery();
		//添加查询条件
		query.processDefinitionKey(key);
		List<ProcessDefinition> list = query.list();
		//循环删除
		for(ProcessDefinition pd : list){
			processEngine.getRepositoryService().deleteDeploymentCascade(pd.getDeploymentId());
		}
	}

	@Override
	public InputStream getImageInputStream(String id) {
		ProcessDefinitionQuery query = processEngine.getRepositoryService().createProcessDefinitionQuery();
		query.processDefinitionId(id);
		ProcessDefinition pd = query.uniqueResult();
		String name = pd.getImageResourceName();
		
		return processEngine.getRepositoryService().getResourceAsStream(pd.getDeploymentId(), name);
	}
	
}

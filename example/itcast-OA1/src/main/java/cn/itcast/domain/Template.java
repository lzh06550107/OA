package cn.itcast.domain;
/**
 * 审批流程和相应文档模板
 * @author:LZH
 * @time:2015年6月23日下午6:38:46
 */
public class Template {
	private Long id;
	private String name; //模板名称
	private String processDefinitionKey; //模板对应的流程
	private String filePath; //模板文档路径
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}
	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	
}

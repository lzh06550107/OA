package cn.itcast.action;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;

import org.jbpm.api.ProcessDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.service.IProcessDefinitionService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

//不用模型驱动所以不用继承BaseAction
@Controller
@Scope("prototype")
public class ProcessDefinitionAction extends ActionSupport {
	private File resource; //只需要和file控件名字相同即可
	private String key; //属性驱动，流程定义的key
	InputStream inputStream; //下载图片的流
	private String id; //属性驱动，流程定义的id
	
	@Resource
	private IProcessDefinitionService processDefinitionService;
	/**
	 * 查询流程定义列表
	 * @return
	 */
	public String list(){
		List<ProcessDefinition> list = processDefinitionService.findLastList();
		ActionContext.getContext().getValueStack().set("list", list);
		return "list";
	}
	/**
	 * 根据key删除流程定义
	 * @return
	 */
	public String delete(){
		//注意key存在中文乱码问题
		try {
			key = new String(key.getBytes("iso-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		processDefinitionService.deleteByKey(key);
		return "toList";
	}
	/**
	 * 跳转到流程定义部署页面
	 * @return
	 */
	public String addUI(){
		return "addUI";
	}
	/**
	 * 部署流程定义
	 * @return
	 */
	public String add(){
		//使用上传的文件来部署定义
		processDefinitionService.deploy(resource);
		return "toList";
	}
	/**
	 * 查看流程图
	 * @return
	 */
	public String showImage(){
		try {
			//id = new String(id.getBytes("iso-8859-1"),"UTF-8");
			id = java.net.URLDecoder.decode(id,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		inputStream = processDefinitionService.getImageInputStream(id);
		return "showImage";
	}
	public File getResource() {
		return resource;
	}
	public void setResource(File resource) {
		this.resource = resource;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		
		this.inputStream = inputStream;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}

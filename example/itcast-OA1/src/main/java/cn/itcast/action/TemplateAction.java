package cn.itcast.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.struts2.ServletActionContext;
import org.jbpm.api.ProcessDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import sun.misc.BASE64Encoder;
import cn.itcast.base.BaseAction;
import cn.itcast.domain.Template;
import cn.itcast.utils.DownloadUtils;

/**
 * 模板管理
 * 
 * @author:LZH
 * @time:2015年6月23日下午6:42:56
 */
@Controller
@Scope("prototype")
public class TemplateAction extends BaseAction<Template> {

	private File resource; // 用于文件上传
	private InputStream inputStream; // 用于文件下载
	private String fileName; //下载文件名

	/**
	 * 查询模板列表
	 * 
	 * @return
	 */
	public String list() {
		List<Template> list = templateService.findAll();
		this.getValueStack().set("list", list);

		return "list";
	}

	/**
	 * 根据id删除模板
	 * 
	 * @return
	 */
	public String delete() {
		templateService.delete(model.getId());
		return "toList";
	}

	/**
	 * 跳转到编辑页面
	 * 
	 * @return
	 */
	public String editUI() {
		// 根据ID查询模板对象用于页面回显
		model = templateService.getById(model.getId());
		// 准备数据——流程定义列表
		List<ProcessDefinition> pdList = processDefinitionService
				.findLastList();
		this.getValueStack().set("pdList", pdList);
		return "editUI";
	}

	/**
	 * 修改模板
	 * 
	 * @return
	 */
	public String edit() {
		Template temp = templateService.getById(model.getId());
		temp.setName(model.getName());
		temp.setProcessDefinitionKey(model.getProcessDefinitionKey());
		String filePath = null;
		if (resource != null) {
			// 用户上传了新文件
			filePath = uploadFile(resource);
			// 删除原来的文件
			String path = temp.getFilePath();
			File file = new File(path);
			if (file.exists()) {
				file.delete();
			}
			temp.setFilePath(filePath);
		}
		templateService.update(temp);
		return "toList";
	}

	/**
	 * 跳转到添加页面
	 * 
	 * @return
	 */
	public String addUI() {
		// 准备数据——流程定义列表
		List<ProcessDefinition> pdList = processDefinitionService
				.findLastList();
		this.getValueStack().set("pdList", pdList);
		return "addUI";
	}

	/**
	 * 添加模板
	 * 
	 * @return
	 */
	public String add() {
		String filePath = uploadFile(resource);

		model.setFilePath(filePath);
		templateService.save(model);
		return "toList";
	}

	/**
	 * 下载文档
	 * 
	 * @return
	 */
	public String download() {
		inputStream = templateService.getInputStreamById(model.getId());
		model = templateService.getById(model.getId());
		String browser = ServletActionContext.getRequest().getHeader("user-agent");
		fileName = DownloadUtils.encodeDownloadFilename(model.getName()+".doc", browser);
		return "download";
	}

	/**
	 * 上传文件
	 * 
	 * @param resource
	 * @return
	 */
	private String uploadFile(File resource) {
		// 将上传的文件保存到uploadFiles目录中,必须自己创建uploadFiles目录
		String realPath = ServletActionContext.getServletContext().getRealPath(
				"/WEB-INF/uploadFiles");
		SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");
		String dateStr = sdf.format(new Date());
		dateStr = realPath + dateStr;
		File dateFile = new File(dateStr);
		if(!dateFile.exists()){
			dateFile.mkdirs();
		}
		String filePath = dateStr + UUID.randomUUID().toString() + ".doc";
		File dest = new File(filePath);
		resource.renameTo(dest);
		return filePath;
	}
	
	public File getResource() {
		return resource;
	}

	public void setResource(File resource) {
		this.resource = resource;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
}

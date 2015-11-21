package cn.itcast.action;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.jbpm.api.task.Task;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.domain.Application;
import cn.itcast.domain.ApproveInfo;
import cn.itcast.domain.PageBean;
import cn.itcast.domain.Template;
import cn.itcast.domain.User;
import cn.itcast.service.IApplicationService;
import cn.itcast.service.IApproveInfoService;
import cn.itcast.service.IFlowService;
import cn.itcast.service.ITemplateService;
import cn.itcast.utils.DownloadUtils;
import cn.itcast.utils.HQLHelper;
import cn.itcast.utils.Pair;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 流转Action
 * @author:LZH
 * @time:2015年6月25日下午5:15:17
 */
@Controller
@Scope("prototype")
public class FlowAction extends ActionSupport {
	@Resource
	private ITemplateService templateService;
	@Resource
	private IFlowService flowService;
	@Resource
	private IApplicationService applicationService;
	@Resource
	private IApproveInfoService approveInfoService;
	
	private Long templateId; //属性驱动
	private File resource; //用于文件上传
	private String status; //申请状态
	private int currentPage = 1; //当前页码（分页需要）
	private Long applicationId; //属性驱动
	private String taskId; //属性驱动
	private InputStream inputStream; //用于下载文件
	private String fileName; //用于文件下载
	private Boolean approval; //属性驱动，审批是否通过
	private String comment; //属性驱动，审批意见
	/**
	 * 起草申请(模板列表)
	 * @return
	 */
	public String templateList(){
		List<Template> list = templateService.findAll();
		ServletActionContext.getContext().getValueStack().set("list", list);
		return "templateList";
	}
	/**
	 * 跳转到提交申请页面
	 * @return
	 */
	public String submitUI(){		
		return "submitUI";
	}
	/**
	 * 提交申请
	 * @return
	 */
	public String submit(){
		Template template = templateService.getById(templateId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		//处理上传文件
		String filePath = this.uploadFile(resource);
		//保存一个申请记录,启动一个流程实例,办理提交申请的任务
		Application app = new Application();
		String title = template.getName() + "_" + this.getCurrentUser().getName() + "_" + sdf.format(new Date());
		app.setTitle(title); //申请的标题
		app.setStatus(Application.STATUS_RUNNING); //默认申请的状态
		app.setApplyTime(new Date()); 
		app.setFilePath(filePath);
		app.setTemplate(template);
		app.setApplicant(getCurrentUser());
		
		flowService.submit(app);

		return "toMyApplicationList";
	}
	/**
	 * 我的申请查询列表
	 * @return
	 */
	public String myApplicationList(){
		//准备数据---模板列表(用于下拉列表)
		List<Template> templateList = templateService.findAll();
		ActionContext.getContext().getValueStack().set("templateList", templateList);
		//查询分页数据---我的申请
		HQLHelper hh = new HQLHelper(Application.class);
		//查询当前登录人的申请记录
		hh.addWhere("o.applicant = ?", this.getCurrentUser());
		
		if(templateId != null){ //按照模板检索
			hh.addWhere("o.template.id = ?", templateId);
		}
		//status不能为空串
		if(status != null && status.trim().length() > 0){ //按照申请状态检索
			hh.addWhere("o.status = ?", status);
		}
		hh.addOrderBy("o.applyTime", false);
		
		PageBean pb = applicationService.getPageBean(hh, currentPage);
		
		ActionContext.getContext().getValueStack().push(pb);
		
		return "myApplicationList";
	}
	/**
	 * 查看申请信息(下载申请文件)
	 * @return
	 */
	public String download(){
		inputStream = applicationService.getInputStreamById(applicationId);
		Application app = applicationService.getById(applicationId);
		fileName = app.getTitle() + ".doc";
		String agent = ServletActionContext.getRequest().getHeader("user-agent");
		fileName = DownloadUtils.encodeDownloadFilename(fileName, agent);
		return "download";
	}
	/**
	 * 查看流转记录(审批信息)
	 * @return
	 */
	public String historyApprovedList(){
		//根据申请的id查询对应的审批列表
		List<ApproveInfo> list = approveInfoService.findApproveInfoListByApplicationId(applicationId);
		ActionContext.getContext().getValueStack().set("list", list);
		return "historyApprovedList";
	}
	/**
	 * 待我审批(我的任务列表)
	 * @return
	 */
	public String myTaskList(){
		List<Pair<Application,Task>>  list = flowService.findTaskList(getCurrentUser());
		ActionContext.getContext().getValueStack().set("list", list);
		return "myTaskList";
	}
	/**
	 * 跳转到审批页面
	 * @return
	 */
	public String approveUI(){
		
		return "approveUI";
	}
	/**
	 * 审批处理
	 * @return
	 */
	public String approve(){
		Application application = applicationService.getById(applicationId);
		//保存一个审批信息实体，办理任务，控制流程来结束当前流程实例
		ApproveInfo ai = new ApproveInfo();
		ai.setApplication(application);
		ai.setApproval(approval);
		ai.setApprover(getCurrentUser());
		ai.setApproveTime(new Date());
		ai.setComment(comment);
		
		flowService.approve(ai, taskId);
		
		return "toMyTaskList";
	}
	
	/**
	 * 上传文件
	 * 
	 * @param resource
	 * @return
	 */
	private String uploadFile(File file) {
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
		file.renameTo(dest);
		return filePath;
	}
	
	/**
	 * 获取当前用户名
	 * @return
	 */
	private User getCurrentUser(){
		return (User) ServletActionContext.getRequest().getSession().getAttribute("user");
	}
	public Long getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}
	public File getResource() {
		return resource;
	}
	public void setResource(File resource) {
		this.resource = resource;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public Long getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
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
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public Boolean getApproval() {
		return approval;
	}
	public void setApproval(Boolean approval) {
		this.approval = approval;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}

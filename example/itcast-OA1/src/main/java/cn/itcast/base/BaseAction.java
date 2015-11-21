package cn.itcast.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;

import cn.itcast.domain.User;
import cn.itcast.service.IBookService;
import cn.itcast.service.IDepartmentService;
import cn.itcast.service.IForumManageService;
import cn.itcast.service.IForumService;
import cn.itcast.service.IPrivilegeService;
import cn.itcast.service.IProcessDefinitionService;
import cn.itcast.service.IReplyService;
import cn.itcast.service.IRoleService;
import cn.itcast.service.ITemplateService;
import cn.itcast.service.ITopicService;
import cn.itcast.service.IUserService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {
	
	@Resource
	protected IBookService bookService;
	@Resource
	protected IRoleService roleService;
	@Resource
	protected IDepartmentService departmentService;
	@Resource
	protected IUserService userService;
	@Resource
	protected IPrivilegeService privilegeService;
	@Resource
	protected IForumManageService forumManageService;
	@Resource
	protected IForumService forumService;
	@Resource
	protected ITopicService topicService;
	@Resource
	protected IReplyService replyService;
	@Resource
	protected ITemplateService templateService;
	@Resource
	protected IProcessDefinitionService processDefinitionService;
	
	public BaseAction(){
		//如果实例化BookAction时，先实例化BaseAction，此时的this.getClass()返回子类类型
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		Type[] types = type.getActualTypeArguments();
		Class<T> clazz = (Class<T>) types[0];
		try {
			model = clazz.newInstance(); //创建了领域模型中的对象
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected T model;
	
	public T getModel() {
		return model;
	}
	
	/**
	 * 获得值栈
	 */
	protected ValueStack getValueStack(){
		return ActionContext.getContext().getValueStack();
	}
	/**
	 * 获取客户端IP地址
	 * @Description:TODO
	 * @return
	 */
	public String getIpAddress(){
		return ServletActionContext.getRequest().getRemoteAddr();
	}
	
	public User getLoginUser(){
		return (User) ServletActionContext.getRequest().getSession().getAttribute("user");
	}
	//分页使用的变量
	//因为还有别的action也要分页，需要放到BaseAction中
	protected int currentPage = 1; //设置默认值为第一页

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
}

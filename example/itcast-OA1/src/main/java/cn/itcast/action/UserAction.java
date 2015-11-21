package cn.itcast.action;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.base.BaseAction;
import cn.itcast.domain.Department;
import cn.itcast.domain.PageBean;
import cn.itcast.domain.Role;
import cn.itcast.domain.User;
import cn.itcast.utils.DepartmentUtils;
import cn.itcast.utils.HQLHelper;
import cn.itcast.utils.MD5Utils;

import com.opensymphony.xwork2.Action;

/**
 * @Description:TODO
 * @author:LZH
 * @time:2015年6月12日下午5:53:02
 */
@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> {
	private Long departmentId;
	private Long[] roleIds;
	
	
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	public Long[] getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(Long[] roleIds) {
		this.roleIds = roleIds;
	}
	
	/**
	 * 查询用户列表
	 * @return
	 */
	public String list(){
		//List<User> list = userService.findAll();
		//this.getValueStack().set("userList", list);
		//分页
		HQLHelper hh = new HQLHelper(User.class);
		PageBean pb = userService.getPageBean(hh, currentPage);
		this.getValueStack().push(pb);
		
		return "list";
	}
	/**
	 * 根据id删除用户
	 * @return
	 */
	public String delete(){
		userService.delete(model);
		return "toList";
	}
	/**
	 * 跳转到用户添加页面
	 * @return
	 */
	public String addUI(){
		//准备数据(部门树形列表，岗位列表)
		List<Department> topList = departmentService.findTopList();
		List<Department> treeList = DepartmentUtils.getTreeList(topList, null);
		List<Role> roleList = roleService.finaAll();
		
		this.getValueStack().set("treeList", treeList);
		this.getValueStack().set("roleList", roleList);
		
		return "addUI";
	}
	/**
	 * 添加用户
	 * @return
	 */
	public String add(){
		if(departmentId != null){
			Department dept = departmentService.getById(departmentId);
			model.setDepartment(dept);
		}
		if(roleIds != null && roleIds.length > 0){
			List<Role> roleList = roleService.getByIds(roleIds);
			model.setRoles(new HashSet<Role>(roleList));
		}
		
		userService.save(model);
		
		return "toList";
	}
	/**
	 * 跳转到用户修改页面
	 * @return
	 */
	public String editUI(){
		List<Department> topList = departmentService.findTopList();
		List<Department> treeList = DepartmentUtils.getTreeList(topList, null);
		List<Role> roleList = roleService.finaAll();
		
		this.getValueStack().set("treeList", treeList);
		this.getValueStack().set("roleList", roleList);
		model = userService.getById(model.getId());
		if(model.getDepartment() != null){
			//回显部门
			departmentId = model.getDepartment().getId();
		}
		Set<Role> roles = model.getRoles();
		if(roles != null && roles.size()>0){
			roleIds = new Long[roles.size()];
			int i = 0;
			for(Role r : roles){
				roleIds[i++] = r.getId();
			}
		}
		
		return "editUI";
	}
	/**
	 * 根据id修改用户
	 * @return
	 */
	public String edit(){
		//关联属性需要处理
		if(departmentId != null){
			Department dept = departmentService.getById(departmentId);
			model.setDepartment(dept);
		}else{
			model.setDepartment(null);
		}
		if(roleIds != null && roleIds.length > 0){
			List<Role> roles = roleService.getByIds(roleIds);
			model.setRoles(new HashSet<Role>(roles));
		}else{
			model.setRoles(null);
		}
		userService.update(model);
		
		return "toList";
	}
	
	public String intiPassword(){
		model = userService.getById(model.getId());
		model.setPassword(MD5Utils.md5("1234")); //重置密码
		
		userService.update(model);
		
		return "toList";
	}
	/**
	 * @Description:添加用户时验证输入的用户名是否有效
	 * @return
	 */
	public String findByLoginName(){
		int count = userService.findByLoginName(model.getLoginName());
		
		ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
		String flag = "1";
		if(count > 0){
			//当前登录名已经存在，不能使用
			flag = "0";
		}
		try{
			ServletActionContext.getResponse().getWriter().print(flag);
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return Action.NONE;
	}
	/**
	 * @Description:跳转到登录页面
	 * @param 
	 * @return
	 */
	public String loginUI(){
		return "loginUI";
	}
	/**
	 * @Description:用户登录
	 * @param 
	 * @return
	 */
	public String login(){
		User user = userService.login(model);
		if(user != null){
			//登录成功，将登录用户放入Session
			ServletActionContext.getRequest().getSession().setAttribute("user", user);
			return "home";
		}else{
			//登录失败，设置错误提示
			this.addActionError("用户名或者密码错误！");
			return "loginUI";
		}
	}
	/**
	 * @Description:用户退出系统
	 * @return
	 */
	public String logout(){
		ServletActionContext.getRequest().getSession().removeAttribute("user");
		return "loginUI";
	}
}



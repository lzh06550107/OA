package cn.itcast.action;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.base.BaseAction;
import cn.itcast.domain.Privilege;
import cn.itcast.domain.Role;

import com.opensymphony.xwork2.util.ValueStack;
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role> {
	private Long[] privilegeIds;
	
	public Long[] getPrivilegeIds() {
		return privilegeIds;
	}
	public void setPrivilegeIds(Long[] privilegeIds) {
		this.privilegeIds = privilegeIds;
	}
	/**
	 * 查询岗位列表
	 */
	public String list(){
		List<Role> list = roleService.finaAll();
		
		ValueStack vs = this.getValueStack();
		vs.set("list", list);
		
		return "list";
	}
	/**
	 * 删除岗位
	 */
	//从客户端传来的参数，struts2根据request的参数名匹配Action中的setter方法并调用
	public String delete(){
		roleService.delete(model);
		return "toList";
	}
	/**
	 * 跳转到修改页面
	 */
	public String editUI(){
		//方法一：使用struts2内部机制简化操作
		Long id = model.getId();
		//根据id查询岗位，用于回显
		model = roleService.getById(id);
		return "editUI";
		/*方法二：
		 Role role = roleService.getById(model.getId());
		 this.getValueStack().push(role);
		 return "editUI";
		 * */
	}
	/**
	 * 保存修改
	 */
	public String edit(){
		//方法一：使用struts2内部机制简化操作
		roleService.update(model);
		return "toList";
		//方法二：
		//先查询，再修改
		/*Role role = roleService.getById(model.getId());
		role.setName(model.getName());
		role.setDescription(model.getDescription());
		roleService.update(role);
		return "toList";*/
	}
	/**
	 * 跳转到添加页面
	 */
	public String addUI(){
		return "addUI";
	}
	/**
	 * 添加岗位
	 */
	public String add(){
		roleService.save(model);
		return "toList";
	}
	/**
	 * 跳转到权限设置页面
	 * @return
	 */
	public String setPrivilegeUI(){
		//1、根据id查询当前要设置的角色，用于回显
		model = roleService.getById(model.getId());
		
		//2、查询所有权限数据，用于页面显示
		//List<Privilege> privilegeList = privilegeService.findAll();
		//为了配合treeView控件，需要查找顶级对象集合
		List<Privilege> privilegeList = privilegeService.findTopList();
		this.getValueStack().set("privilegeList", privilegeList);
		
		//3、查询角色相应的权限，用于回显
		Set<Privilege> privileges = model.getPrivileges();
		if(privileges != null && privileges.size() > 0){
			privilegeIds = new Long[privileges.size()];
			int i = 0;
			for(Privilege p : privileges){
				privilegeIds[i++] = p.getId();
			}
		}
		return "setPrivilegeUI";
	}
	/**
	 * 为角色设置权限
	 * @return
	 */
	public String setPrivilege(){
		model = roleService.getById(model.getId());
		
		if(privilegeIds != null && privilegeIds.length > 0){
			//如果权限id数组不为空，就根据权限id数组查询对应的多个权限
			List<Privilege> priviList = privilegeService.getByIds(privilegeIds);
			model.setPrivileges(new HashSet<Privilege>(priviList));
		}else{
			model.setPrivileges(null);//如没有设置，则权限为空
		}
		roleService.update(model);

		return "toList";
	}
}

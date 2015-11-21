package cn.itcast.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.base.BaseAction;
import cn.itcast.domain.Department;
import cn.itcast.utils.DepartmentUtils;

@Controller
@Scope("prototype")
public class DepartmentAction extends BaseAction<Department> {
	private Long parentId;
	
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	/**
	 * 查询所有的部门
	 */
	public String list(){
		//List<Department> list = departmentService.findAll();
		List<Department> list = null;
		//需要查询各个等级的部门
		if(parentId == null){
			//查询顶级部门列表
			list = departmentService.findTopList();
		}else{
			//查询子部门列表
			list = departmentService.findChildren(parentId);
			model = departmentService.getById(parentId);
		}
		this.getValueStack().set("list", list);
		return "list";
	}
	/**
	 * 根据id删除部门
	 */
	public String delete(){
		departmentService.delete(model);
		return "toList";
	}
	/**
	 * 跳转到部门添加页面
	 * @return
	 */
	public String addUI(){
		//select框需要部门列表数据
		//List<Department> list = departmentService.findAll();
		//获取所有顶级部门
		List<Department> topList = departmentService.findTopList();
		//处理该List为树形结构
		List<Department> treeList = DepartmentUtils.getTreeList(topList, null);
		this.getValueStack().set("departmentList", treeList);
		return "addUI";
	}
	/**
	 * 添加部门
	 * @return
	 */
	public String add(){
		if(parentId != null){
			Department parent = departmentService.getById(parentId);
			model.setParent(parent);//设置上级部门
		}else{
			model.setParent(null); //表明为顶级部门
		}
		departmentService.save(model);
		return "toList";
	}
	/**
	 * 跳转到修改页面
	 * @return
	 */
	public String editUI(){
		//准备数据：部门列表给select控件
		//List<Department> list = departmentService.findAll();
		List<Department> topList = departmentService.findTopList();
		//处理该List为树形结构
		List<Department> treeList = DepartmentUtils.getTreeList(topList, model.getId());
		//准备数据：选择的部门给表中其它控件
		Long id = model.getId();
		//通过id从数据库检索该对象的信息来填充model。
		model = departmentService.getById(id);
		this.getValueStack().set("departmentList", treeList);
		//防止空指针异常
		if(model.getParent() != null){
			//传入父id
			parentId = model.getParent().getId();
		}
		return "editUI";
	}
	/**
	 * 修改部门
	 * @return
	 */
	public String edit(){
		//返回的model包含id
		
		if(parentId != null){
			Department parent = departmentService.getById(parentId);
			model.setParent(parent);
		}else{
			model.setParent(null);
		}
		departmentService.update(model);
		return "toList";
	}
}

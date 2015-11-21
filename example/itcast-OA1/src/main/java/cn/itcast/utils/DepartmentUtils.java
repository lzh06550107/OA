package cn.itcast.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import cn.itcast.domain.Department;

public class DepartmentUtils {

	public static List<Department> getTreeList(List<Department> topList, Long removeId) {
		List<Department> treeList = new ArrayList<Department>();
		walkTree(topList, treeList, "┣", removeId);
		return treeList;
	}
	/**
	 * 转换为树形输出结构
	 * @param topList 原始树的数据
	 * @param treeList 处理后树的数据
	 * @param prefix 需要添加前缀
	 * @param removeId 需要删除的部门id
	 */
	private static void walkTree(Collection<Department> topList,
			List<Department> treeList, String prefix, Long removeId) {
		//注意：topList是持久化对象，若是修改会导致数据库同步更新
		//此处for循环遍历遍历b不是对topList中的引用？？？？
		for(Department d : topList){
			//剪切掉当前部门及其子部门
			if(removeId != null && d.getId().equals(removeId)){
				continue;
			}
			
			//需要处理的部分
			Department dept = new Department();
			dept.setName(prefix + d.getName());
			dept.setId(d.getId()); //必须要加上，否则就不会自动选择相应的上级部门
			treeList.add(dept);
			
			Set<Department> children = d.getChildren();
			//注意该处空格必须用全角，半角的浏览器会把多个空格缩减为一个
			walkTree(children, treeList, "　" + prefix, removeId);
		}
		
	}
	
}

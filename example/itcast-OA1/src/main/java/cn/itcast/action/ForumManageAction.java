package cn.itcast.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.base.BaseAction;
import cn.itcast.domain.Forum;
import cn.itcast.domain.PageBean;
import cn.itcast.utils.HQLHelper;
/**
 * 板块管理操作
 * @Description:TODO
 * @author:LZH
 * @time:2015年6月15日下午5:43:01
 */
@Controller
@Scope("prototype")
public class ForumManageAction extends BaseAction<Forum> {
	/**
	 * @Description:查询板块列表
	 * @return
	 */
	public String list() {
		//List<Forum> list = forumManageService.findAll();
		//this.getValueStack().set("list", list);
		//分页
		HQLHelper hh = new HQLHelper(Forum.class);
		hh.addOrderBy("o.position", true);
		
		PageBean pb = forumManageService.getPageBean(hh, currentPage);
		this.getValueStack().push(pb);
		
		return "list";
	}

	/**
	 * @Description:根据id删除板块
	 * @return
	 */
	public String delete() {
		forumManageService.delete(model);
		return "toList";
	}

	/**
	 * 
	 * @Description:跳转到增加板块页面
	 * @return
	 */
	public String addUI() {
		return "addUI";
	}

	/**
	 * 
	 * @Description:添加板块
	 * @return
	 */
	public String add() {
		forumManageService.save(model);
		return "toList";
	}

	/**
	 * 
	 * @Description:跳转到编辑板块页面
	 * @return
	 */
	public String editUI() {
		model = forumManageService.getById(model.getId());
		return "editUI";
	}

	/**
	 * 
	 * @Description:修改板块
	 * @return
	 */
	public String edit() {
		forumManageService.update(model);
		return "toList";
	}

	/**
	 * 
	 * @Description:上移
	 * @return
	 */
	public String moveUp() {
		forumManageService.moveUp(model.getId());	

		return "toList";
	}

	/**
	 * 
	 * @Description:下移
	 * @return
	 */
	public String moveDown() {
		forumManageService.moveDown(model.getId());
		
		return "toList";
	}
}

package cn.itcast.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.base.BaseAction;
import cn.itcast.domain.Forum;
import cn.itcast.domain.PageBean;
import cn.itcast.domain.Topic;
import cn.itcast.utils.HQLHelper;
/**
 * 论坛中板块操作
 * @Description:TODO
 * @author:LZH
 * @time:2015年6月15日下午5:43:37
 */
@Controller
@Scope("prototype")
public class ForumAction extends BaseAction<Forum> {
	
	private int viewType; //显示那种类型的主题
	private int orderBy; //排序字段
	private boolean asc; //升序或者降序
	
	/**
	 * 查询板块列表
	 * @Description:TODO
	 * @return
	 */
	public String list(){
		//List<Forum> list = forumService.findAll();
		//this.getValueStack().set("list", list);
		//分页
		HQLHelper hh = new HQLHelper(Forum.class);
		hh.addOrderBy("o.position", true);
		
		PageBean pb = forumService.getPageBean(hh, currentPage);
		this.getValueStack().push(pb);
		
		return "list";
	}
	/**
	 * 查询主题列表(单个板块)
	 * @Description:TODO
	 * @return
	 */
	public String show(){
		//根据板块id查询板块，用于在页面中显示
		model = forumService.getById(model.getId());
		//根据板块的id查询主题列表
		//List<Topic> topicList = topicService.findTopicByForum(model);
		//this.getValueStack().set("topicList", topicList);
		//有条件的分页
		HQLHelper hh = new HQLHelper(Topic.class);
		if(viewType == 1){
			//查询精华帖
			hh.addWhere("o.type = ?", 1);
		}
		if(orderBy == 0){
			//默认排序（按最后更新时间排序，但所有置顶帖都在前面）
			hh.addOrderBy("CASE o.type WHEN 2 THEN 2 ELSE 1 END", false);
			hh.addOrderBy("o.postTime", false);
		}else if(orderBy == 1){
			//按最后更新时间排序
			hh.addOrderBy("o.lastUpdateTime", asc);
		}else if(orderBy == 2){
			//按主题发表时间排序
			hh.addOrderBy("o.postTime", asc);
		}else{
			//按回复数量排序
			hh.addOrderBy("o.replyCount", asc);
		}
		
		PageBean pb = topicService.getPageBean(hh, currentPage);
		this.getValueStack().push(pb);
		
		return "forumShow";
	}
	public int getViewType() {
		return viewType;
	}
	public void setViewType(int viewType) {
		this.viewType = viewType;
	}
	public int getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}
	public boolean isAsc() {
		return asc;
	}
	public void setAsc(boolean asc) {
		this.asc = asc;
	}
	
	
}

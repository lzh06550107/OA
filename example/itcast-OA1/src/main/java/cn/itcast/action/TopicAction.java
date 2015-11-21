package cn.itcast.action;

import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.base.BaseAction;
import cn.itcast.domain.Forum;
import cn.itcast.domain.PageBean;
import cn.itcast.domain.Reply;
import cn.itcast.domain.Topic;
import cn.itcast.utils.HQLHelper;
@Controller
@Scope("prototype")
public class TopicAction extends BaseAction<Topic> {

	private Long forumId;
	
	/**
	 * 跳转到发表主题页面
	 * @Description:TODO
	 * @return
	 */
	public String addUI(){
		//根据版块id查询版块信息，用于页面显示
		Forum forum = forumService.getById(forumId);
		this.getValueStack().push(forum);
		
		return "addUI";
	}
	/**
	 * 发表主题
	 * @Description:TODO
	 * @return
	 */
	public String add(){
		Forum forum = forumService.getById(forumId);
		model.setForum(forum); //主题关联版块
		
		model.setIpAddress(this.getIpAddress());
		model.setLastUpdateTime(new Date());
		model.setPostTime(model.getLastUpdateTime());
		model.setReplyCount(0);
		model.setType(0);
		model.setAuthor(this.getLoginUser());
		model.setLastReply(null); //默认没有回复
		
		topicService.save(model);
		
		return "toTopicList";
	}
	/**
	 * 显示单个主题(即是回复列表)
	 * @Description:TODO
	 * @return
	 */
	public String show(){
		//根据id查询主题
		model = topicService.getById(model.getId());
		//根据主题查询对应的回复列表
		//List<Reply> replyList = replyService.getReplyByTopic(model);
		//this.getValueStack().set("replyList", replyList);
		//分页查询
		HQLHelper hh = new HQLHelper(Reply.class);
		hh.addWhere("o.topic = ?", model);
		hh.addOrderBy("o.postTime", true);
		PageBean pb = replyService.getPageBean(hh, currentPage);
		this.getValueStack().push(pb);
		
		return "show";
	}
	public void setForumId(Long forumId){
		this.forumId = forumId;
	}
	public Long getForumId() {
		return forumId;
	}
}

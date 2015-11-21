package cn.itcast.action;

import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.base.BaseAction;
import cn.itcast.domain.Reply;
import cn.itcast.domain.Topic;
@Controller
@Scope("prototype")
public class ReplyAction extends BaseAction<Reply> {
	private Long topicId; 
	public Long getTopicId() {
		return topicId;
	}
	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}
	/**
	 * 跳转到回复页面
	 * @Description:TODO
	 * @return
	 */
	public String addUI(){
		Topic topic = topicService.getById(topicId);
		this.getValueStack().push(topic);
		
		return "addUI";
	}
	/**
	 * 发表回复
	 * @Description:TODO
	 * @return
	 */
	public String add(){
		Topic topic = topicService.getById(topicId);
		model.setTopic(topic);//回复的关联主题
		
		model.setDeleted(0); //默认为未删除
		model.setIpAddress(getIpAddress());
		model.setPostTime(new Date());
		model.setAuthor(getLoginUser());
		
		replyService.save(model);
		
		return "toReplyList"; //跳转到回复列表
	}
	
}

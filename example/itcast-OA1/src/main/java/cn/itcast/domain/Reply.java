package cn.itcast.domain;
/**
 * 
 * @Description:回复
 * @author:LZH
 * @time:2015年6月15日下午4:24:15
 */
public class Reply extends Article {
	private Topic topic; //当前回复属于哪个主题
	private int deleted; //删除标志1表示已经删除0表示没有删除
	
	public Topic getTopic() {
		return topic;
	}
	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	public int getDeleted() {
		return deleted;
	}
	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}	
	
}

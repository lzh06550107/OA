package cn.itcast.action;

import org.springframework.stereotype.Controller;

/**
 * 主页Action
 * @author Administrator
 * 因为只是跳转页面不用继承BaseAction类，又因为没有成员变量，所以不用考虑
 * 线程安全问题，范围不用使用原型。
 */
@Controller
public class HomeAction {
	/**
	 * 跳转到首页面
	 * @return
	 */

	public String index(){
		return "index";
	}
	/**
	 * 跳转到top.jsp页面
	 * @return
	 */
	public String top(){
		return "top";
	}
	/**
	 * 跳转到left.jsp页面
	 * @return
	 */
	public String left(){
		return "left";
	}
	/**
	 * 跳转到right.jsp页面
	 * @return
	 */
	public String right(){
		return "right";
	}
	/**
	 * 跳转到bottom.jsp页面
	 * @return
	 */
	public String bottom(){
		return "bottom";
	}
}

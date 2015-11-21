package cn.itcast.action;

import javax.annotation.Resource;

import org.jbpm.api.ProcessEngine;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.base.BaseAction;
import cn.itcast.domain.Book;

import com.opensymphony.xwork2.Action;
@Controller
@Scope("prototype")
public class BookAction extends BaseAction<Book> {
	@Resource
	private ProcessEngine processEngine;
	public String execute() throws Exception{
		System.out.println(processEngine);
		return Action.NONE;
	}
}

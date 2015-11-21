package cn.itcast.action;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.apache.struts2.StrutsTestCase;
import org.junit.Test;

import com.opensymphony.xwork2.ActionProxy;

public class TestUserAction extends StrutsTestCase{
	ActionProxy proxy = null;
	UserAction userAction = null;
	@Test
	public void listTest() throws Exception{
		
		proxy = this.getActionProxy("/user_list.action");
		userAction = (UserAction) proxy.getAction();
		
		String result = userAction.list();
		
		assertThat("list", is(result));
	}
}

package cn.itcast.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.service.impl.UserServiceImpl;

@ParentPackage("basePackage")
// 使用convention-plugin插件提供的@Action注解将一个普通java类标注为一个可以处理用户请求的Action，Action的名字为test
@Namespace("/")
// 使用convention-plugin插件提供的@Namespace注解为这个Action指定一个命名空间
public class TestAction {
	@Autowired
	private UserServiceImpl userService;
	@Action(value = "test")
	public void test() {
		System.out.println("进入TestAction");
		//userService.test();
	}
}

package cn.itcast.interceptor;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import cn.itcast.domain.User;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class CheckPrivilegeInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		User user = (User) ServletActionContext.getRequest().getSession()
				.getAttribute("user");

		String actionName = invocation.getProxy().getActionName();
		String namespace = invocation.getProxy().getNamespace();
		String url = namespace + actionName;
		
		//由于以UI结尾的路径不是权限，需要截取掉UI成为具有权限路径
		if(url.endsWith("UI")){
			url = url.substring(0, url.length()-2);
		}
		// 1、如果用户没有登录
		if (null == user) {
			// 1.1、如果用户访问的是登录页面，则放行
			if ("/user_login".equals(url)) {
				return invocation.invoke();
			} else {
				// 1.2、非登录页面，则重定向到登录页面
				return "loginUI";
			}
		} else {
			// 2、用户已经登录
			List<String> allUrl = (List<String>) ServletActionContext
					.getServletContext().getAttribute("allUrl");
			if (!allUrl.contains(url)) {
				// 2.1、如果用户访问的是不用控制权限的页面，则直接放行
				return invocation.invoke();
			} else {
				// 2.2、如果用户访问的是需要控制权限的页面，则如下
				// 2.2.1、如果用户没有权限，则跳转到没有权限提示页面
				if (!user.hasPrivilegeByUrl(url)) {
					return "noPrivilegeUI";
				} else {
					// 2.2.2、如果具有该权限，则放行
					return invocation.invoke();
				}
			}
		}
	}
}

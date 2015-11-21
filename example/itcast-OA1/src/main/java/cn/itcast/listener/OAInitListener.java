package cn.itcast.listener;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.itcast.domain.Privilege;
import cn.itcast.service.IPrivilegeService;
import cn.itcast.service.impl.PrivilegeServiceImpl;



public class OAInitListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}
    /**
     * 存在懒加载session关闭的问题，它与使用过滤器解决方案不同，不是请求
     * 如果解决该问题，可以取消懒加载。
     */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		//查询所有的权限，并根据与用户具有的权限的权限比较来控制相应功能的显示
		//1、获取容器
		WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext());
		//2、从spring容器中获取PrivilegeService
		IPrivilegeService privilegeService = (IPrivilegeService) applicationContext.getBean("privilegeServiceImpl");
		//3、使用service查询权限数据
		List<Privilege> topList = privilegeService.findTopList();
		//4、将权限数据放入application作用域
		arg0.getServletContext().setAttribute("privilegeTopList", topList);
		
		//查询所有的权限对应的URL
		List<String> allUrl = privilegeService.findAllUrl();
		arg0.getServletContext().setAttribute("allUrl", allUrl);
	}
}

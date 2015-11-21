package cn.itcast.jk.controller.cargo.contract;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.jk.controller.BaseController;
@Controller
@RequestMapping("/cargo/outproduct")
public class OutProductController  extends BaseController {
	
	@RequestMapping("/toprint.action")
	public String toedit(){
		
		return "/cargo/outproduct/jOutProduct.jsp";
	}
	
	@RequestMapping("/outProductPrint.action")
	public void outProductPrint(String inputDate, HttpServletRequest request, HttpServletResponse response){
		//获取浏览器类型
		String userAgent = request.getHeader("user-agent");
		//打印
		try {
			contractService.outProductPrint(inputDate, response, userAgent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

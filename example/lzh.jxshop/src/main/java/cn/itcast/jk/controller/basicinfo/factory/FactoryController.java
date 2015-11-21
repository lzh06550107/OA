package cn.itcast.jk.controller.basicinfo.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.itcast.jk.controller.BaseController;
import cn.itcast.jk.domain.Factory;
import cn.itcast.jk.service.FactoryService;

@Controller
@RequestMapping("/basicinfo/factory")
public class FactoryController extends BaseController {
	
	//显示所有的工厂信息
	@RequestMapping("/list.action")
	public String list(Factory factory, Model model, HttpServletRequest httpServletRequest){
		List<Factory> dataList = factoryService.find(factory);
		model.addAttribute("dataList", dataList);
		
		System.out.println(httpServletRequest.getServletContext().getRealPath("/"));
		
		return "/basicinfo/factory/jFactoryList.jsp";
	}
	//转向新增页面
	@RequestMapping("/tocreate.action")
	public String tocreate(){
		return "/basicinfo/factory/jFactoryCreate.jsp";
	}
	//新增工厂信息
	@RequestMapping("/insert.action")
	public String insert(Factory factory){
		factoryService.insert(factory);
		
		return "redirect:/basicinfo/factory/list.action";
	}
	//转向修改页面
	@RequestMapping("/toupdate.action")
	public String toupdate(String id, Model model){
		//准备修改的对象
		Factory obj = factoryService.get(id);
		model.addAttribute("obj", obj);
		
		return "/basicinfo/factory/jFactoryUpdate.jsp";
	}
	//修改页面
	@RequestMapping("/update.action")
	public String update(Factory factory){
		factoryService.update(factory);
		
		return "redirect:/basicinfo/factory/list.action";
	}
	//单个删除（演示用）
	@RequestMapping("/delete.action")
	public String delete(String id){
		factoryService.delete(id);
		
		return "redirect:/basicinfo/factory/list.action";
	}
	//批量删除（包含了单个删除）
	@RequestMapping("/deletebatch.action")
	public String delete(@RequestParam("id") String[] ids){
		factoryService.delete(ids);
		
		return "redirect:/basicinfo/factory/list.action";
	}
	//转向查看页面
	@RequestMapping("/toview.action")
	public String toview(String id, Model model){
		Factory obj = factoryService.get(id);
		model.addAttribute("obj", obj);
		
		return "/basicinfo/factory/jFactoryView.jsp";
	}
	//更新状态
	@RequestMapping({"/{state}/submit.action","/{state}/cancel.action"})
	public String start(@RequestParam("id") String[] ids, @PathVariable("state") String state){
		Map<String, Object> map =  new HashMap<String, Object>();
		map.put("state", state);
		map.put("ids", ids);		
		factoryService.changeState(map);
		
		return "redirect:/basicinfo/factory/list.action";
	}
	//打印
	@RequestMapping("/print.action")
	public void print(HttpServletRequest request, HttpServletResponse response){
		Factory factory = new Factory();
		factory.setState(1);
		factoryService.print(factory, request, response);
	}
}

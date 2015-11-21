package cn.itcast.jk.controller.cargo.contract;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.jk.controller.BaseController;
import cn.itcast.jk.domain.ContractProduct;
import cn.itcast.jk.domain.Factory;
import cn.itcast.jk.service.ContractProductService;
import cn.itcast.jk.service.FactoryService;
import cn.itcast.utils.Arith;

/**
 * @Description:
 * @Author:	nutony
 * @Company:	http://java.itcast.cn
 * @CreateDate:	2014-3-16
 */
@Controller
@RequestMapping("/cargo/contractproduct")
public class ContractProductController extends BaseController{
	
/*	@RequestMapping("/list.action")
	public String list(ContractProduct contractProduct, Model model){
		List<ContractProduct> dataList = contractProductService.find(contractProduct);
		model.addAttribute("dataList", dataList);
		
		return "/cargo/contractproduct/jContractProductList.jsp";
	}*/
	//为了减少操作的深度，把新增和显示列表在一个页面，新增位于页面上面，列表位于页面下面
	@RequestMapping("/tocreate.action")
	public String tocreate(ContractProduct contractProduct, Model model){
		//传递主表Id给插入货物外键
		model.addAttribute("contractId", contractProduct.getContractId());
		//传递厂家的信息给页面
		List<Factory> factoryList = factoryService.combo();
		model.addAttribute("factoryList", factoryList);
		//传递给显示列表数据
		List<ContractProduct> dataList = contractProductService.find(contractProduct);
		model.addAttribute("dataList", dataList);
		
		return "/cargo/contractproduct/jContractProductCreate.jsp";
	}
	
	@RequestMapping("/insert.action")
	public String insert(ContractProduct contractProduct){
		//设置货物主键
		contractProduct.setId(UUID.randomUUID().toString());
		
		//数据验证确保两个输入框必须有值
		//计算并设置总金额，注意小数计算的精度问题
		Arith arith = new Arith();
		contractProduct.setAmount(arith.mul(contractProduct.getCnumber(), contractProduct.getPrice()));
		contractProductService.insert(contractProduct);
		 //批量新增
		return "redirect:/cargo/contractproduct/tocreate.action?contractId="+ contractProduct.getContractId(); //没有传入新增货物合同外键
	}
	
	@RequestMapping("/toupdate.action")
	public String toupdate(String id, Model model){
		//获取货物
		ContractProduct contractProduct = contractProductService.get(id);
		model.addAttribute("obj", contractProduct);
		//厂家的显示列表
		List<Factory> factoryList = factoryService.combo();
		model.addAttribute("factoryList", factoryList);
		
		return "/cargo/contractproduct/jContractProductUpdate.jsp";
	}
	
	@RequestMapping("/update.action")
	public String update(ContractProduct contractProduct){
		Arith arith = new Arith();
		contractProduct.setAmount(arith.mul(contractProduct.getCnumber(), contractProduct.getPrice()));
		contractProductService.update(contractProduct);
		//注意重定向问题，需要自己传递参数
		return "redirect:/cargo/contractproduct/tocreate.action?contractId="+ contractProduct.getContractId(); 
	}
	
	@RequestMapping("/delete.action")
	public String delete(String id, String contractId){
		contractProductService.delete(id);
		
		return "redirect:/cargo/contractproduct/tocreate.action?contractId="+ contractId; 
	}
}

package cn.itcast.jk.controller.cargo.contract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.itcast.jk.controller.BaseController;
import cn.itcast.jk.dao.ContractDao;
import cn.itcast.jk.domain.Contract;
import cn.itcast.utils.ContractPrint;

@Controller
@RequestMapping("/cargo/contract")
public class ContractController extends BaseController {

	@RequestMapping("/list.action")
	public String find(Contract contract, Model model) {
		List<Contract> dataList = contractService.find(contract);
		model.addAttribute("dataList", dataList);

		return "/cargo/contract/jContractList.jsp";
	}

	@RequestMapping("/tocreate.action")
	public String tocreate() {
		return "/cargo/contract/jContractCreate.jsp";
	}

	@RequestMapping("/insert.action")
	public String insert(Contract contract) {

		contract.setState(0); // 新增默认为草稿
		contractService.insert(contract);

		return "redirect:/cargo/contract/list.action";
	}

	@RequestMapping("/toupdate.action")
	public String toupdate(String id, Model model) {
		Contract contract = contractService.get(id);
		model.addAttribute("obj", contract);

		return "/cargo/contract/jContractUpdate.jsp";
	}

	@RequestMapping("/update.action")
	public String update(Contract contract) {
		contractService.update(contract);

		return "redirect:/cargo/contract/list.action";
	}

	@RequestMapping("/deletebatch.action")
	public String delete(@RequestParam("id") String[] ids) {
		contractService.delete(ids);

		return "redirect:/cargo/contract/list.action";
	}

	// 只能查询合同信息能力
	/*
	 * @RequestMapping("/toview.action") public String toview(String id, Model
	 * model){ Contract contract = contractService.get(id);
	 * model.addAttribute("obj", contract);
	 * 
	 * return "/cargo/contract/jContractView.jsp"; }
	 */
	// 具有查询合同、货物、附件信息的功能
	@RequestMapping("/toview.action")
	public String toview(String id, Model model) {
		cn.itcast.jk.domain.vo.Contract contract = contractService.view(id);
		model.addAttribute("obj", contract);

		return "/cargo/contract/jContractView.jsp";
	}

	// 更新状态
	@RequestMapping({ "/{state}/submit.action", "/{state}/cancel.action" })
	public String start(@RequestParam("id") String[] ids,
			@PathVariable("state") String state) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", state);
		map.put("ids", ids);
		contractService.changeState(map);

		return "redirect:/cargo/contract/list.action";
	}
	
	//打印
	@RequestMapping("/print.action")
	public void print(String contractId, HttpServletRequest request, HttpServletResponse response){
		System.out.println(contractId);
		contractService.print(contractId, request, response);
	}

}

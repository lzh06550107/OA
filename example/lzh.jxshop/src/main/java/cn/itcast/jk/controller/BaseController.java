package cn.itcast.jk.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import cn.itcast.jk.service.ContractProductService;
import cn.itcast.jk.service.ContractService;
import cn.itcast.jk.service.ExportProductService;
import cn.itcast.jk.service.ExportService;
import cn.itcast.jk.service.ExtCproductService;
import cn.itcast.jk.service.ExtEproductService;
import cn.itcast.jk.service.FactoryService;

/**
 * @Description:
 * @Author:	nutony
 * @Company:	http://java.itcast.cn
 * @CreateDate:	2014-3-4
 */
public abstract class BaseController {
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@Resource
	protected ContractService contractService;
	@Resource
	protected FactoryService factoryService;
	@Resource
	protected ContractProductService contractProductService;
	@Resource
	protected ExtCproductService extCproductService;
	@Resource
	protected ExportService exportService;
	@Resource
	protected ExportProductService exportProductService;
	@Resource
	protected ExtEproductService extEproductService;
}

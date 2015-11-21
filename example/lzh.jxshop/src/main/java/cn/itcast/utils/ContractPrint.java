package cn.itcast.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import cn.itcast.jk.dao.ContractDao;
import cn.itcast.jk.domain.vo.Contract;
import cn.itcast.jk.domain.vo.ContractProduct;
import cn.itcast.util.file.PoiUtil;

public class ContractPrint{

	/*
	 * response 打印输出到response中，直接下载
	 * */
	
	public void print(String contractId, ContractDao cDao, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		Contract contract = cDao.view(contractId); //返回某个合同的所有关联产品和附件信息
		UtilFuns utilFuns = new UtilFuns();
		
		//String rootPath = request.getRealPath("/");
		String rootPath = request.getSession().getServletContext().getRealPath("/");
	
		String tempXlsFile = rootPath + "make/xlsprint/tCONTRACT.xls";		//获取模板文件
		
		//将数据和业务相分离,先把每一页的数据准备好放在map中，把所有页面集中在一个链表中，再打印每一页
		//填写每页的内容，之后在循环每页读取打印,用Map为了方便取数据，不使用String[]、arrayList。容易插入一个漏了的元素。访问时按key访问，这样无需管它的顺序。
		Map<String,String> pageMap = null;
		List<Map> pageList = new ArrayList();			//所有的打印页，选用arrayList方便知道有几页，方便插入分页
		
		ContractProduct oProduct = null;
		String stars = "";
		for(int j=0; contract!=null&&j<contract.getImportNum();j++){		//重要程度
			stars += "★";
		}
		
		String oldFactory = "";
		List<ContractProduct> oList = contract.getContractProducts();
		
		for(int i=0;i<oList.size();i++){
			oProduct = oList.get(i);	//获得货物
			pageMap = new HashMap();	//每页的内容
			
			pageMap.put("Offeror", "收 购 方：" + contract.getOfferor());
			pageMap.put("Factory", "生产工厂：" + oProduct.getFactory().getFullName());
			pageMap.put("ContractNo", "合 同 号：" + contract.getContractNo());
			pageMap.put("Contractor", "联 系 人：" + oProduct.getFactory().getContractor());
			pageMap.put("SigningDate", "签单日期："+UtilFuns.formatDateTimeCN(UtilFuns.dateTimeFormat(contract.getSigningDate())));
			pageMap.put("Phone", "电    话：" + oProduct.getFactory().getPhone());
			pageMap.put("InputBy", "制单：" + contract.getInputBy());
			pageMap.put("CheckBy", "审单："+ utilFuns.fixSpaceStr(contract.getCheckBy(),26)+"验货员："+utilFuns.convertNull(contract.getInspector()));
			pageMap.put("Remark", "  "+contract.getRemark());
			pageMap.put("Request", "  "+contract.getCrequest());
			
			pageMap.put("ProductImage1", oProduct.getProductImage());
			pageMap.put("ProductDesc1", oProduct.getProductDesc());
			pageMap.put("Cnumber1", String.valueOf(oProduct.getCnumber().doubleValue()));
			if(oProduct.getPackingUnit().equals("PCS")){
				pageMap.put("PackingUnit1", "只");
			}else if(oProduct.getPackingUnit().equals("SETS")){
				pageMap.put("PackingUnit1", "套");
			}
			pageMap.put("Price1", String.valueOf(oProduct.getPrice().doubleValue()));
			pageMap.put("ProductNo1", oProduct.getProductNo());
			
			oldFactory = oProduct.getFactory().getFactoryName();
			
			if(contract.getPrintStyle().equals("2")){
				i++;	//读取第二个货物信息
				if(i<oList.size()){
					oProduct = oList.get(i);
					
					if(oProduct.getFactory().getFactoryName().equals(oldFactory)){	//厂家不同另起新页打印，除去第一次的比较
						
						pageMap.put("ProductImage2", oProduct.getProductImage());
						pageMap.put("ProductDesc2", oProduct.getProductDesc());
						pageMap.put("Cnumber2", String.valueOf(oProduct.getCnumber().doubleValue()));
						if(oProduct.getPackingUnit().equals("PCS")){
							pageMap.put("PackingUnit2", "只");
						}else if(oProduct.getPackingUnit().equals("SETS")){
							pageMap.put("PackingUnit2", "套");
						}						
						pageMap.put("Price2", String.valueOf(oProduct.getPrice().doubleValue()));
						//pageMap.put("Amount2", String.valueOf(oProduct.getAmount().doubleValue()));			//在excel中金额采用公式，所以无需准备数据
						pageMap.put("ProductNo2", oProduct.getProductNo());
					}else{
						i--;	//tip:list退回
					}
				}else{
					pageMap.put("ProductNo2", null);	//后面依据此判断是否有第二个货物，该标志明确该页有没有第二个货物
				}
			}
			
			pageMap.put("ContractDesc", stars+" 货物描述");			//重要程度 + 货物描述
			
			pageList.add(pageMap);
			
		}
		
		int cellHeight = 96;	//一个货物的高度			用户需求，一个货物按192高度打印，后来又嫌难看，打印高度和2款高度一样。
//		if(contract.getPrintStyle().equals("2")){
//			cellHeight = 96;	//两个货物的高度
//		}
		
		PoiUtil poiUtil = new PoiUtil();
		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(tempXlsFile));	//打开模板excel文件
		HSSFFont defaultFont10 = poiUtil.defaultFont10(wb);		//设置字体
		HSSFFont defaultFont12 = poiUtil.defaultFont12(wb);		//设置字体
		HSSFFont blackFont = poiUtil.blackFont12(wb);			//设置字体
		Short rmb2Format = poiUtil.rmb2Format(wb);				//设置金额显示格式
		Short rmb4Format = poiUtil.rmb4Format(wb);				//设置金额显示格式
		

		HSSFSheet sheet = wb.getSheetAt(0);				//选择第一个工作簿
		wb.setSheetName(0, "购销合同");					//设置工作簿的名称


		//sheet.setDefaultColumnWidth( 20); 		// 设置每列默认宽度
		
//		POI分页符有BUG，必须在模板文件中插入一个分页符，然后再此处删除预设的分页符；最后在下面重新设置分页符。
//		sheet.setAutobreaks(false);
//		int iRowBreaks[] = sheet.getRowBreaks();
//		sheet.removeRowBreak(3);
//		sheet.removeRowBreak(4);
//		sheet.removeRowBreak(5);
//		sheet.removeRowBreak(6);
		
		Region region = null;
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();		//add picture

		HSSFRow nRow = null;
		Cell nCell   = null;
		int curRow = 0;
		
		//打印每页
		Map<String,String> printMap = null;
		for(int p=0;p<pageList.size();p++){
			printMap = pageList.get(p);
			
			if(p>0){
				sheet.setRowBreak(curRow++);	//在第startRow行设置分页符
			}
			
			
			//设置logo图片
			poiUtil.setPicture(wb, patriarch, rootPath+"make/xlsprint/logo.jpg", curRow, 2, curRow+4, 3);
			
			//header
			nRow = sheet.createRow(curRow++);
			nRow.setHeightInPoints(21);
			
			nCell   = nRow.createCell((3));
			nCell.setCellValue("SHAANXI");
			nCell.setCellStyle(headStyle(wb));

			//header
			nRow = sheet.createRow(curRow++);
			nRow.setHeightInPoints(41);
			
			nCell   = nRow.createCell((3));
			nCell.setCellValue("     JK INTERNATIONAL ");
			nCell.setCellStyle(tipStyle(wb));

			curRow++;
			
			//header
			nRow = sheet.createRow(curRow++);
			nRow.setHeightInPoints(20);
			
			nCell   = nRow.createCell((1));
			nCell.setCellValue("                 西经济技术开发区西城一路27号无迪大厦19楼");
			nCell.setCellStyle(addressStyle(wb));
			
			//header
			nCell   = nRow.createCell((6));
			nCell.setCellValue(" CO., LTD.");
			nCell.setCellStyle(ltdStyle(wb));

			//header
			nRow = sheet.createRow(curRow++);
			nRow.setHeightInPoints(15);
			
			nCell   = nRow.createCell((1));
			nCell.setCellValue("                   TEL: 0086-29-86339371  FAX: 0086-29-86303310               E-MAIL: ijackix@glass.cn");
			nCell.setCellStyle(telStyle(wb));
			
			//line
			nRow = sheet.createRow(curRow++);
			nRow.setHeightInPoints(7);
			
			poiUtil.setLine(wb, patriarch, curRow, 2, curRow, 8);	//draw line

			//header
			nRow = sheet.createRow(curRow++);
			nRow.setHeightInPoints(30);
			
			nCell   = nRow.createCell((4));
			nCell.setCellValue("    购   销   合   同");
			nCell.setCellStyle(titleStyle(wb));
			
			//Offeror
			nRow = sheet.createRow(curRow++);
			nRow.setHeightInPoints(20);
			
			nCell   = nRow.createCell((1));
			nCell.setCellValue(printMap.get("Offeror"));
			nCell.setCellStyle(poiUtil.titlev12(wb, blackFont));

			//Facotry
			nCell   = nRow.createCell((5));
			nCell.setCellValue(printMap.get("Factory"));
			nCell.setCellStyle(poiUtil.titlev12(wb, blackFont));
			
			//ContractNo
			nRow = sheet.createRow(curRow++);
			nRow.setHeightInPoints(20);
			
			nCell   = nRow.createCell(1);
			nCell.setCellValue(printMap.get("ContractNo"));
			nCell.setCellStyle(poiUtil.titlev12(wb, blackFont));
			
			//Contractor
			nCell  = nRow.createCell(5);
			nCell.setCellValue(printMap.get("Contractor"));
			nCell.setCellStyle(poiUtil.titlev12(wb, blackFont));
			
			//SigningDate
			nRow = sheet.createRow(curRow++);
			nRow.setHeightInPoints(20);
			
			nCell = nRow.createCell(1);
			nCell.setCellValue(printMap.get("SigningDate"));
			nCell.setCellStyle(poiUtil.titlev12(wb, blackFont));
			
			//Phone
			nCell = nRow.createCell(5);
			nCell.setCellValue(printMap.get("Phone"));
			nCell.setCellStyle(poiUtil.titlev12(wb, blackFont));
			
			//表格头
			nRow = sheet.createRow(curRow++);
			nRow.setHeightInPoints(24);
			
			nCell = nRow.createCell(1);
			nCell.setCellValue("产品");
			nCell.setCellStyle(thStyle(wb));
			poiUtil.doMerge(curRow-1, 1, 1, 3, sheet); //横向合并
			
			nCell = nRow.createCell(4);
			nCell.setCellValue(printMap.get("ContractDesc"));
			nCell.setCellStyle(thStyle(wb));	
			
			nCell = nRow.createCell(5);
			nCell.setCellValue("数量");
			nCell.setCellStyle(thStyle(wb));						
			poiUtil.doMerge(curRow-1, 5, 1, 2, sheet); //横向合并
			
			nCell = nRow.createCell(7);
			nCell.setCellValue("单价");
			nCell.setCellStyle(thStyle(wb));						
			
			nCell = nRow.createCell(8);
			nCell.setCellValue("总金额");
			nCell.setCellStyle(thStyle(wb));						
			
			//具体货物打印，首先判断一页打印几项货物
			int endk = 0;
			if(contract.getPrintStyle().equals("1") || UtilFuns.isEmpty(printMap.get("ProductNo2"))){	//一个货物
				endk = 1;
			}else if(contract.getPrintStyle().equals("2")){
				endk = 2;
			}
			//开始打印货物
			for(int i=1; i<=endk; i++){
				nRow = sheet.createRow(curRow++);
				nRow.setHeightInPoints(cellHeight);
				//productImage
				nCell = nRow.createCell(1); 
				nCell.setCellStyle(thStyle(wb)); //设置单元格样式
				poiUtil.doMerge(curRow-1, 1, 1, 3, sheet); //合并单元格
				if(UtilFuns.isNotEmpty(printMap.get("ProductImage"+i))){
					System.out.println(printMap.get("ProductImage"+i));
					poiUtil.setPicture(wb, patriarch, rootPath+"ufiles/jquery/"+printMap.get("ProductImage"+i), curRow-1, 1, curRow, 4);
				}
				
				//处理第二行,经验总结，当表格有纵向合并单元格时，先把合并的几行先创建出来，再来纵向合并，就不会覆盖合并样式
				Row nRow2 = sheet.createRow(curRow);
				nRow2.setHeightInPoints(24);
				//ProductNo
				nCell = nRow2.createCell(1);
				nCell.setCellValue(printMap.get("ProductNo"+i));
				nCell.setCellStyle(poiUtil.notecv10_BorderThin(wb, defaultFont10));
				poiUtil.doMerge(curRow, 1, 1, 3, sheet);
				
				//ProductDesc
				nCell = nRow.createCell(4);
				nCell.setCellValue(printMap.get("ProductDesc"+i));
				nCell.setCellStyle(poiUtil.notehv10_BorderThin(wb, defaultFont10));	
				poiUtil.doMerge(curRow-1, 4, 2, 1, sheet);
				//Cnumber
				nCell = nRow.createCell(5);
				nCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				nCell.setCellValue(Double.parseDouble(printMap.get("Cnumber"+i)));
				nCell.setCellStyle(poiUtil.numberrv10_BorderThin(wb, defaultFont10));	
				poiUtil.doMerge(curRow-1, 5, 2, 1, sheet);
				//Unit
				nCell = nRow.createCell(6);
				nCell.setCellValue(printMap.get("PackingUnit"+i));
				nCell.setCellStyle(poiUtil.moneyrv10_BorderThin(wb, defaultFont10, rmb4Format));
				poiUtil.doMerge(curRow-1, 6, 2, 1, sheet);
				//Price
				nCell = nRow.createCell(7);
				nCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				nCell.setCellValue(Double.parseDouble(printMap.get("Price"+i)));
				nCell.setCellStyle(poiUtil.moneyrv10_BorderThin(wb, defaultFont10, rmb4Format));
				poiUtil.doMerge(curRow-1, 7, 2, 1, sheet);
				//Amount
				nCell = nRow.createCell(8);
				if(UtilFuns.isNotEmpty(printMap.get("Cnumber"+i)) && UtilFuns.isNotEmpty(printMap.get("Price"+i))){
					nCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
					nCell.setCellFormula("F"+String.valueOf(curRow)+"*H"+String.valueOf(curRow));
				}
				nCell.setCellStyle(poiUtil.moneyrv10_BorderThin(wb, defaultFont10, rmb4Format));
				poiUtil.doMerge(curRow-1, 8, 2, 1, sheet);
				//因为合并处理了两行，所以需要再跳过一行
				curRow++;
			}
			
			//InputBy
			nRow = sheet.createRow(curRow++);
			nRow.setHeightInPoints(24);		
			nCell = nRow.createCell(1);
			nCell.setCellValue(printMap.get("InputBy"));
			nCell.setCellStyle(poiUtil.bnormalv12(wb,defaultFont12));
			poiUtil.doMerge(curRow-1, 1, 1, 2, sheet);
			//CheckBy+inspector
			nCell = nRow.createCell(4);
			nCell.setCellValue(printMap.get("CheckBy"));
			nCell.setCellStyle(poiUtil.bnormalv12(wb,defaultFont12));
			poiUtil.doMerge(curRow-1, 4, 1, 2, sheet);
			//
			nCell = nRow.createCell(7);
			nCell.setCellValue("总金额：");
			nCell.setCellStyle(bcv12(wb));
			//TotalAmount,计算总金额
			
			nCell  = nRow.createCell(8);
			nCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
			//这里有一个碰巧，当只有一个货物时，就是正好I"+String.valueOf(curRow-4)单元格为空
			nCell.setCellFormula("SUM(I"+String.valueOf(curRow-4)+":I"+String.valueOf(curRow-1)+")");
			nCell.setCellStyle(poiUtil.moneyrv12_BorderThin(wb,defaultFont12,rmb2Format));		
			
			//note
			nRow = sheet.createRow(curRow++);
			nRow.setHeightInPoints(21);		
			nCell = nRow.createCell(2);
			nCell.setCellValue(printMap.get("Remark"));
			nCell.setCellStyle(noteStyle(wb));	
			
			//Request
			nRow = sheet.createRow(curRow++);
			//nRow.setHeightInPoints(21);	 //如何根据内容自动调整高度????
			float height = poiUtil.getCellAutoHeight(printMap.get("Request"), 12f);		//自动高度
			nRow.setHeightInPoints(height);
			nCell = nRow.createCell(1);
			nCell.setCellValue(printMap.get("Request"));
			nCell.setCellStyle(noteStyle(wb));
			
			poiUtil.doMerge(curRow-1, 1, 1, 8, sheet);
			
			//space line
			nRow = sheet.createRow(curRow++);
			nRow.setHeightInPoints(20);
			
			//duty
			nRow = sheet.createRow(curRow++);
			nRow.setHeightInPoints(32);			
			nCell = nRow.createCell(1);
			nCell.setCellValue("未按以上要求出货而导致客人索赔，由供方承担。");
			nCell.setCellStyle(dutyStyle(wb));	
			
			//space line
			nRow = sheet.createRow(curRow++);
			nRow.setHeightInPoints(32);
			
			//buyer
			nRow = sheet.createRow(curRow++);
			nRow.setHeightInPoints(25);
			
			nCell = nRow.createCell(1);
			nCell.setCellValue("    收购方负责人：");
			nCell.setCellStyle(dutyStyle(wb));				
			
			//seller
			nCell = nRow.createCell(5);
			nCell.setCellValue("供方负责人：");
			nCell.setCellStyle(dutyStyle(wb));	
			
			curRow++; //分隔下一份合同
		}
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();			//生成流对象
		wb.write(byteArrayOutputStream);													//将excel写入流

		//工具类，封装弹出下载框：		
		String outFile = "购销合同.xls";
		DownloadUtil down = new DownloadUtil();
		String agent = request.getHeader("user-agent");
		down.download(byteArrayOutputStream, response, outFile, agent);

	}
	
	private HSSFCellStyle leftStyle(HSSFWorkbook wb){
		HSSFCellStyle curStyle = wb.createCellStyle();
		curStyle.setWrapText(true);  						//换行   
		HSSFFont curFont = wb.createFont();					//设置字体
		curFont.setCharSet(HSSFFont.DEFAULT_CHARSET);		//设置中文字体，那必须还要再对单元格进行编码设置
		//fTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);	//加粗
		curFont.setFontHeightInPoints((short) 10);
		curStyle.setFont(curFont);
		curStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);		//单元格垂直居中
		
		curStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);				//实线右边框
		curStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);				//实线右边框
		
		return curStyle;
	}  
	
	private HSSFCellStyle headStyle(HSSFWorkbook wb){
		HSSFCellStyle curStyle = wb.createCellStyle();
		HSSFFont curFont = wb.createFont();					//设置字体
		curFont.setFontName("Comic Sans MS");
		curFont.setCharSet(HSSFFont.DEFAULT_CHARSET);		//设置中文字体，那必须还要再对单元格进行编码设置
		
		curFont.setItalic(true);
		curFont.setFontHeightInPoints((short) 16);
		curStyle.setFont(curFont);
		curStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);		//单元格垂直居中
		
		return curStyle;
	}  
	
	private HSSFCellStyle tipStyle(HSSFWorkbook wb){
		HSSFCellStyle curStyle = wb.createCellStyle();
		HSSFFont curFont = wb.createFont();					//设置字体
		curFont.setFontName("Georgia");
		curFont.setCharSet(HSSFFont.DEFAULT_CHARSET);		//设置中文字体，那必须还要再对单元格进行编码设置
		
		curFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);	//加粗
		curFont.setFontHeightInPoints((short) 28);
		curStyle.setFont(curFont);
		curStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);		//单元格垂直居中
		
		return curStyle;
	}  
	
	private HSSFCellStyle addressStyle(HSSFWorkbook wb){
		HSSFCellStyle curStyle = wb.createCellStyle();
		HSSFFont curFont = wb.createFont();					//设置字体
		curFont.setFontName("宋体");
		curFont.setCharSet(HSSFFont.DEFAULT_CHARSET);		//设置中文字体，那必须还要再对单元格进行编码设置
		
		//fTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);	//加粗
		curFont.setFontHeightInPoints((short) 10);
		curStyle.setFont(curFont);
		curStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);		//单元格垂直居中
		
		return curStyle;
	}  
	
	private HSSFCellStyle ltdStyle(HSSFWorkbook wb){
		HSSFCellStyle curStyle = wb.createCellStyle();
		HSSFFont curFont = wb.createFont();					//设置字体
		curFont.setFontName("Times New Roman");
		curFont.setCharSet(HSSFFont.DEFAULT_CHARSET);		//设置中文字体，那必须还要再对单元格进行编码设置
		
		curFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);	//加粗
		curFont.setItalic(true);
		curFont.setFontHeightInPoints((short) 16);
		curStyle.setFont(curFont);
		curStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);		//单元格垂直居中
		
		return curStyle;
	} 	
	
	private HSSFCellStyle telStyle(HSSFWorkbook wb){
		HSSFCellStyle curStyle = wb.createCellStyle();
		HSSFFont curFont = wb.createFont();					//设置字体
		curFont.setFontName("宋体");
		curFont.setCharSet(HSSFFont.DEFAULT_CHARSET);		//设置中文字体，那必须还要再对单元格进行编码设置
		
		//fTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);	//加粗
		curFont.setFontHeightInPoints((short) 9);
		curStyle.setFont(curFont);
		curStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);		//单元格垂直居中
		
		return curStyle;
	} 	
	
	private HSSFCellStyle titleStyle(HSSFWorkbook wb){
		HSSFCellStyle curStyle = wb.createCellStyle();
		HSSFFont curFont = wb.createFont();					//设置字体
		curFont.setFontName("黑体");
		curFont.setCharSet(HSSFFont.DEFAULT_CHARSET);		//设置中文字体，那必须还要再对单元格进行编码设置
		
		curFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);	//加粗
		curFont.setFontHeightInPoints((short) 18);
		curStyle.setFont(curFont);
		curStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);		//单元格垂直居中
		
		return curStyle;
	} 	
	
	private HSSFCellStyle requestStyle(HSSFWorkbook wb){
		HSSFCellStyle curStyle = wb.createCellStyle();
		curStyle.setWrapText(true);  						//换行   
		HSSFFont curFont = wb.createFont();					//设置字体
		curFont.setFontName("宋体");
		curFont.setCharSet(HSSFFont.DEFAULT_CHARSET);		//设置中文字体，那必须还要再对单元格进行编码设置
		
		curFont.setFontHeightInPoints((short) 10);
		curStyle.setFont(curFont);
		curStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);		//单元格垂直居中
		
		return curStyle;
	} 	
	
	private HSSFCellStyle dutyStyle(HSSFWorkbook wb){
		HSSFCellStyle curStyle = wb.createCellStyle();
		HSSFFont curFont = wb.createFont();					//设置字体
		curFont.setFontName("黑体");
		curFont.setCharSet(HSSFFont.DEFAULT_CHARSET);		//设置中文字体，那必须还要再对单元格进行编码设置
		
		curFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);	//加粗
		curFont.setFontHeightInPoints((short) 16);
		curStyle.setFont(curFont);
		curStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);		//单元格垂直居中
		
		return curStyle;
	} 	
	
	private HSSFCellStyle noteStyle(HSSFWorkbook wb){
		HSSFCellStyle curStyle = wb.createCellStyle();
		HSSFFont curFont = wb.createFont();					//设置字体
		curFont.setFontName("宋体");
		curFont.setCharSet(HSSFFont.DEFAULT_CHARSET);		//设置中文字体，那必须还要再对单元格进行编码设置
		
		curFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);	//加粗
		curFont.setFontHeightInPoints((short) 12);
		curStyle.setFont(curFont);
		curStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);		//单元格垂直居中
		curStyle.setWrapText(true); //强制单元格自动换行
		return curStyle;
	} 
	
	public HSSFCellStyle thStyle(HSSFWorkbook wb){
		HSSFCellStyle curStyle = wb.createCellStyle();
		HSSFFont curFont = wb.createFont();					//设置字体
		curFont.setFontName("宋体");
		curFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);	//加粗
		curFont.setFontHeightInPoints((short) 12);
		curFont.setCharSet(HSSFFont.DEFAULT_CHARSET);		//设置中文字体，那必须还要再对单元格进行编码设置
		
		curStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);				//实线右边框
		curStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);				//实线右边框
		curStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);			//实线右边框
		curStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);				//实线右边框
		
		curStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		curStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);		//单元格垂直居中
		
		return curStyle;
	}  
	
	public HSSFCellStyle bcv12(HSSFWorkbook wb){
		HSSFCellStyle curStyle = wb.createCellStyle();
		HSSFFont curFont = wb.createFont();						//设置字体
		curFont.setFontName("Times New Roman");
		curFont.setCharSet(HSSFFont.DEFAULT_CHARSET);			//设置中文字体，那必须还要再对单元格进行编码设置
		
		curFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);		//加粗
		curFont.setFontHeightInPoints((short) 12);
		curStyle.setFont(curFont);
		
		curStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);				//实线
		curStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);			//粗实线
		curStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);			//实线
		curStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);				//实线
		
		curStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		curStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);		//单元格垂直居中
		
		return curStyle;
	}		
	
}

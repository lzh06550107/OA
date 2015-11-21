package cn.itcast.jk.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.jk.dao.ContractDao;
import cn.itcast.jk.dao.ContractProductDao;
import cn.itcast.jk.dao.ExtCproductDao;
import cn.itcast.jk.domain.Contract;
import cn.itcast.jk.domain.vo.OutProduct;
import cn.itcast.jk.service.ContractService;
import cn.itcast.utils.ContractPrint;
import cn.itcast.utils.DownloadUtil;

@Service
@Transactional
public class ContractServiceImpl implements ContractService {

	@Resource
	private ContractDao contractDao;
	@Resource
	private ContractProductDao contractProductDao;
	@Resource
	private ExtCproductDao extCproductDao;

	public List<Contract> find(Contract entity) {
		return contractDao.find(entity);
	}

	public Contract get(Serializable id) {
		return contractDao.get(id);
	}

	public void insert(Contract entity) {
		contractDao.insert(entity);
	}

	public void update(Contract entity) {
		contractDao.update(entity);
	}

	public void delete(Serializable id) {
		contractDao.delete(id);
	}

	public void delete(Serializable[] ids) {
		for (Serializable id : ids) {
			extCproductDao.deleteByContractId(id);
		}
		for (Serializable id : ids) {
			contractProductDao.deleteByContractId(id);
		}
		contractDao.delete(ids);
	}

	public cn.itcast.jk.domain.vo.Contract view(Serializable id) {
		return contractDao.view(id);
	}

	public void changeState(Map<String, Object> map) {
		contractDao.changeState(map);
	}

	public void outProductPrint(String inputDate, HttpServletResponse response,
			String agent) throws Exception {
		List<OutProduct> list = contractDao.findOutProduct(inputDate);
		Workbook wb = new HSSFWorkbook(new FileInputStream(
				"D:\\tOutProduct.xls"));
		Sheet sheet = wb.getSheetAt(0);
		

		Row nRow = null;
		Cell nCell = null;
		int rowNo = 2; // 从模板第三行开始输入数据
		int colNo = 1; // 空第一列为了打印

		// 处理标题
		nRow = sheet.getRow(0); // 获得行对象
		nCell = nRow.getCell(1); // 获得单元格对象
		nCell.setCellValue(inputDate.replaceFirst("-0", "-").replaceFirst("-","年") + "月份出货表"); // yyyy-MM 2010-08
		// 获取第一行数据的样式
		nRow = sheet.getRow(2);
		int firstColNum = nRow.getFirstCellNum();
		int lastColNum = nRow.getLastCellNum();
		//System.out.println(firstColNum+":"+lastColNum);
		int currentColNum = lastColNum-firstColNum;
		CellStyle[] cellStyles = new CellStyle[currentColNum];
		for(int i=0; i < currentColNum; i++){
			nCell = nRow.getCell(i+firstColNum);
			cellStyles[i] = nCell.getCellStyle();
		}
		
		for (int i = 0; i < list.size(); i++) {
			int j = 0; //控制单元格样式下标
			colNo = 1;
			OutProduct op = list.get(i);
			nRow = sheet.createRow(rowNo++);

			nCell = nRow.createCell(colNo++); // 创建单元格
			nCell.setCellValue(op.getCustomName());
			nCell.setCellStyle(cellStyles[j++]);

			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(op.getContractNo());
			nCell.setCellStyle(cellStyles[j++]);

			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(op.getProductNo());
			nCell.setCellStyle(cellStyles[j++]);

			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(op.getCnumber());
			nCell.setCellStyle(cellStyles[j++]);

			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(op.getFactoryName());
			nCell.setCellStyle(cellStyles[j++]);

			nCell = nRow.createCell(colNo++);
			List<String> extNameList = contractDao.getExtName(op.getContractProductId());
			String _extProductName = "无";
			if(extNameList!=null && extNameList.size()>0){
				_extProductName = "";
				for(String s : extNameList){
					_extProductName +=(s + "\n");
				}
				_extProductName = _extProductName.substring(0, _extProductName.length()-1); //除去最后一个换行符
			}
			nCell.setCellValue(_extProductName);
			nCell.setCellStyle(cellStyles[j++]);

			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(op.getDeliveryPeriod()); //日期可能存在问题可以转换为字符串UtilFun.dateFormat()
			nCell.setCellStyle(cellStyles[j++]);

			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(op.getShipTime());
			nCell.setCellStyle(cellStyles[j++]);

			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(op.getTradeTerms());
			nCell.setCellStyle(cellStyles[j++]);
		}
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		wb.write(bao);
		DownloadUtil download = new DownloadUtil();
		download.download(bao, response, "出货表.xls", agent);
	}

	public void print(String contractId, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			new ContractPrint().print(contractId, contractDao, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

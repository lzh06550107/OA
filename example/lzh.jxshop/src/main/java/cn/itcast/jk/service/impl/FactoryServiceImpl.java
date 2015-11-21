package cn.itcast.jk.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.jk.dao.FactoryDao;
import cn.itcast.jk.domain.Factory;
import cn.itcast.jk.service.FactoryService;
import cn.itcast.utils.DownloadUtil;
import cn.itcast.utils.file.FileUtil;

@Service
@Transactional
public class FactoryServiceImpl implements FactoryService {

	@Resource
	FactoryDao factoryDao;

	public List<Factory> find(Factory entity) {
		return factoryDao.find(entity);
	}

	public Factory get(Serializable id) {
		return factoryDao.get(id);
	}

	public void insert(Factory entity) {
		factoryDao.insert(entity);
	}

	public void update(Factory entity) {
		factoryDao.update(entity);
	}

	public void delete(Serializable id) {
		factoryDao.delete(id);
	}

	public void delete(Serializable[] ids) {
		factoryDao.delete(ids);
	}

	public void changeState(Map<String, Object> map) {
		factoryDao.changeState(map);
	}

	public void print(Factory factory, HttpServletRequest request, HttpServletResponse response) {
		// 查找打印数据
		List<Factory> dataList = factoryDao.find(factory);
		// 输出格式
		// 表格标题
		String[] headers = new String[] { "厂家全名", "缩写", "联系人", "电话", "手机",
				"传真", "备注" };

		// 创建工作簿
		Workbook wb = new HSSFWorkbook();
		// 创建工作表sheet
		Sheet sheet = wb.createSheet();
		sheet.setColumnWidth(0, 30 * 256); // 设置列的属性

		int rowNo = 0;
		int colNo = 0;
		Row nRow = null;
		Cell nCell = null;

		// 创建文章题目
		nRow = sheet.createRow(rowNo); // 文章题目行
		nRow.setHeightInPoints(40);
		nCell = nRow.createCell(0);
		nCell.setCellValue("厂商联系方式");
		nCell.setCellStyle(this.titleStyle(wb)); //设置当前单元格样式
		this.doMerge(rowNo++, colNo, 1, 7, sheet); //合并单元格

		// 创建标题行对象
		nRow = sheet.createRow(rowNo++);
		nRow.setHeightInPoints(28); // 设置行的属性

		// 创建表格标题
		for (int i = 0; i < headers.length; i++) {
			// 创建单元格对象
			nCell = nRow.createCell(i);
			nCell.setCellValue(headers[i]);
			nCell.setCellStyle(this.headerStyle(wb)); // 设置单元格的属性
		}

		// 写数据
		for (int j = 0; j < dataList.size(); j++) {
			colNo = 0;
			Factory f = dataList.get(j);
			nRow = sheet.createRow(rowNo++); // 创建行

			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(f.getFullName());
			nCell.setCellStyle(this.textStyle(wb));

			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(f.getFactoryName());
			nCell.setCellStyle(this.textStyle(wb));

			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(f.getContractor());
			nCell.setCellStyle(this.textStyle(wb));

			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(f.getPhone());
			nCell.setCellStyle(this.textStyle(wb));

			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(f.getMobile());
			nCell.setCellStyle(this.textStyle(wb));

			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(f.getFax());
			nCell.setCellStyle(this.textStyle(wb));

			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(f.getCnote());
			nCell.setCellStyle(this.textStyle(wb));
		}

		FileOutputStream os;
		try {
			/*String root = request.getServletContext().getRealPath("/tempfile");
			File file = new File(root);
			if(!file.exists())
				file.mkdirs();
			FileUtil fileUtil = new FileUtil();
			String fileName = root + File.separator + fileUtil.newFile(root, "factory.xls");
			os = new FileOutputStream(fileName);
			wb.write(os);
			os.flush();
			os.close();*/
			//下载输出
			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			wb.write(bao);
			String agent = request.getHeader("user-agent"); //获取浏览器类型
			DownloadUtil download = new DownloadUtil();
			download.download(bao, response, "生成厂家通讯录.xls", agent);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	//按照左上角单元格延展合并单元格且样式为左上角单元格的样式
	private void doMerge(int rowIndex, int columnIndex, int rowSpan, int columnSpan, Sheet sheet) {
	    Cell cell = sheet.getRow(rowIndex).getCell(columnIndex);
	    CellRangeAddress range = new CellRangeAddress(rowIndex, rowIndex + rowSpan - 1, columnIndex, columnIndex
	            + columnSpan - 1);

	    sheet.addMergedRegion(range);

	    RegionUtil.setBorderBottom(cell.getCellStyle().getBorderBottom(), range, sheet, sheet.getWorkbook());
	    RegionUtil.setBorderTop(cell.getCellStyle().getBorderTop(), range, sheet, sheet.getWorkbook());
	    RegionUtil.setBorderLeft(cell.getCellStyle().getBorderLeft(), range, sheet, sheet.getWorkbook());
	    RegionUtil.setBorderRight(cell.getCellStyle().getBorderRight(), range, sheet, sheet.getWorkbook());

	}

	// 文章题目样式设计
	private CellStyle titleStyle(Workbook wb) {
		CellStyle xStyle = wb.createCellStyle();
		// 设置字体
		Font xFont = wb.createFont();
		xFont.setFontName("宋体");
		xFont.setFontHeightInPoints((short) 40);
		xStyle.setFont(xFont);
		// 居中
		xStyle.setAlignment(CellStyle.ALIGN_CENTER);
		xStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		// 设置边框
		xStyle.setBorderLeft(CellStyle.BORDER_THIN);
		xStyle.setBorderTop(CellStyle.BORDER_THIN);
		xStyle.setBorderRight(CellStyle.BORDER_THIN);
		xStyle.setBorderBottom(CellStyle.BORDER_THIN);

		return xStyle;
	}

	// 表格头样式设计
	private CellStyle headerStyle(Workbook wb) {
		CellStyle xStyle = wb.createCellStyle();
		// 设置字体
		Font xFont = wb.createFont();
		xFont.setFontName("宋体");
		xFont.setFontHeightInPoints((short) 12);
		xStyle.setFont(xFont);
		// 居中
		xStyle.setAlignment(CellStyle.ALIGN_CENTER);
		xStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		// 设置边框
		xStyle.setBorderLeft(CellStyle.BORDER_THIN);
		xStyle.setBorderTop(CellStyle.BORDER_THIN);
		xStyle.setBorderRight(CellStyle.BORDER_THIN);
		xStyle.setBorderBottom(CellStyle.BORDER_THIN);

		return xStyle;
	}

	// 表格文本样式设计
	private CellStyle textStyle(Workbook wb) {
		CellStyle xStyle = wb.createCellStyle();
		// 设置字体
		Font xFont = wb.createFont();
		xFont.setFontName("宋体");
		xFont.setFontHeightInPoints((short) 10);
		xStyle.setFont(xFont);
		// 居中
		// xStyle.setAlignment(CellStyle.ALIGN_CENTER);
		xStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		// 设置边框
		xStyle.setBorderLeft(CellStyle.BORDER_THIN);
		xStyle.setBorderTop(CellStyle.BORDER_THIN);
		xStyle.setBorderRight(CellStyle.BORDER_THIN);
		xStyle.setBorderBottom(CellStyle.BORDER_THIN);

		return xStyle;
	}

	public List<Factory> combo() {
		return factoryDao.combo();
	}

}

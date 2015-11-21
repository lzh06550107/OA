package cn.itcast.demo;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import cn.itcast.util.file.PoiUtil;

public class Test {
	public static void main(String[] args) throws Exception{
		FileOutputStream out = new FileOutputStream("d:\\workbook.xls");
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet();
		Row nRow = sheet.createRow(1);
		PoiUtil poiUtil = new PoiUtil();
		Cell nCell = nRow.createCell(1);
		poiUtil.doMerge(1, 1, 2, 2, sheet);
		String temp = "读一个文件非常简单。为了读取一个文件，创建一个org.apache.poi.poifs.Filesystem的实例，并传递InputStream给构造函数。构造一个org.apache.poi.hssf.usermodel.HSSFWorkbook并把文件系统实例传递给它。这样你可以通过该对象的所有访问方法来访问所有模型对象。修改已经读入的文件很容易，你通过访问方法来检索对象，通过父对象的移除方法来移除它。修改完成后，调用workbook.write(outputStream)来保存它。";
		nCell.setCellValue(temp);
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		nCell.setCellStyle(cellStyle);
		Test.calcAndSetRowHeigt(nRow);
//		sheet.autoSizeColumn(1);
		wb.write(out);
		out.close();
	}
	/**
	 * 根据行内容重新计算行高
	 * @param row
	 */
	public static void calcAndSetRowHeigt(Row sourceRow) {
		for (int cellIndex = sourceRow.getFirstCellNum(); cellIndex <= sourceRow.getPhysicalNumberOfCells(); cellIndex++) {
			//行高
			double maxHeight = sourceRow.getHeight();
			Cell sourceCell = sourceRow.getCell(cellIndex);
			//单元格的内容
			String cellContent = getCellContentAsString(sourceCell);
			if(null == cellContent || "".equals(cellContent)){
				continue;
			}
			//单元格的宽高及单元格信息
			Map<String, Object> cellInfoMap = getCellInfo(sourceCell);
			Integer cellWidth = (Integer)cellInfoMap.get("width");
			Integer cellHeight = (Integer)cellInfoMap.get("height");
			if(cellHeight > maxHeight){
				maxHeight = cellHeight; //获取当前行中高度最高的单元格
			}
			System.out.println("单元格的宽度 : " + cellWidth + "    单元格的高度 : " + maxHeight + ",    单元格的内容 : " + cellContent);
			CellStyle cellStyle = sourceCell.getCellStyle();
			Font font = sourceRow.getSheet().getWorkbook().getFontAt(cellStyle.getFontIndex());
			//字体的高度
			short fontHeight = font.getFontHeight();
			
			//cell内容字符串总宽度
			double cellContentWidth = cellContent.getBytes().length * 2 * 256;
			
	        //字符串需要的行数 不做四舍五入之类的操作
	        double stringNeedsRows =(double)cellContentWidth / cellWidth;
	        //小于一行补足一行
	        if(stringNeedsRows < 1.0){
	        	stringNeedsRows = 1.0;
	        }
	        
	        //需要的高度 			(Math.floor(stringNeedsRows) - 1) * 40 为两行之间空白高度
	        double stringNeedsHeight = (double)fontHeight * stringNeedsRows;
	        //需要重设行高
	        if(stringNeedsHeight > maxHeight){
	        	maxHeight = stringNeedsHeight;
	        	//超过原行高三倍 则为5倍 实际应用中可做参数配置
	    		if(maxHeight/cellHeight > 5){
	    			maxHeight = 5 * cellHeight;
	    		}
	    		//最后取天花板防止高度不够
	    		maxHeight = Math.ceil(maxHeight);
	    		//重新设置行高 同时处理多行合并单元格的情况
	    		Boolean isPartOfRowsRegion = (Boolean)cellInfoMap.get("isPartOfRowsRegion");
	    		if(isPartOfRowsRegion){
	    			Integer firstRow = (Integer)cellInfoMap.get("firstRow");
	    			Integer lastRow = (Integer)cellInfoMap.get("lastRow");
	    			//平均每行需要增加的行高
	    			double addHeight = (maxHeight - cellHeight)/(lastRow - firstRow + 1);
	    			for (int i = firstRow; i <= lastRow; i++) {
	    				double rowsRegionHeight =sourceRow.getSheet().getRow(i).getHeight() + addHeight;
	    				sourceRow.getSheet().getRow(i).setHeight((short)rowsRegionHeight);
	    			}
	    		}else{
	    			sourceRow.setHeight((short)maxHeight);
	    		}
	        }
	        System.out.println("字体高度 : " + fontHeight + ",    字符串宽度 : " + cellContentWidth + ",    字符串需要的行数 : " + stringNeedsRows + ",   需要的高度 : " + stringNeedsHeight + ",   现在的行高 : " + maxHeight);
	        System.out.println();
		}
	}
	
	/**
	 * 解析一个单元格得到数据
	 * @param cell
	 * @return
	 */
	private static String getCellContentAsString(Cell cell) {
		if(null == cell){
			return "";
		}
		String result = "";
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC:
			String s = String.valueOf(cell.getNumericCellValue());
			if (s != null) {
				if (s.endsWith(".0")) {
					s = s.substring(0, s.length() - 2);
				}
			}
			result = s;
			break;
		case Cell.CELL_TYPE_STRING:
			result = String.valueOf(cell.getStringCellValue());
			break;
		case Cell.CELL_TYPE_BLANK:
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			result = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_ERROR:
			break;
		default:
			break;
		}
		return result;
	}
	
	/**
     * 获取单元格及合并单元格的宽度
     * @param cell
     * @return
     */
    private static Map<String, Object> getCellInfo(Cell cell) {
    	Sheet sheet = cell.getSheet(); //获取当前纸张
    	int rowIndex = cell.getRowIndex(); //
    	int columnIndex = cell.getColumnIndex();
    	
    	boolean isPartOfRegion = false;
    	int firstColumn = 0;
    	int lastColumn = 0;
    	int firstRow = 0;
    	int lastRow = 0;
		int sheetMergeCount = sheet.getNumMergedRegions(); //获取合并区域
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress ca = sheet.getMergedRegion(i); 
			firstColumn = ca.getFirstColumn();
			lastColumn = ca.getLastColumn();
			firstRow = ca.getFirstRow();
			lastRow = ca.getLastRow();
			if (rowIndex >= firstRow && rowIndex <= lastRow) {
				if (columnIndex >= firstColumn && columnIndex <= lastColumn) {
					isPartOfRegion = true; //表名当前单元格是合并区域
					break;
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Integer width = 0;
		Integer height = 0;
		boolean isPartOfRowsRegion = false;
		if(isPartOfRegion){
			for (int i = firstColumn; i <= lastColumn; i++) {
				width += sheet.getColumnWidth(i); //加总所有的宽度
			}
			for (int i = firstRow; i <= lastRow; i++) {
				height += sheet.getRow(i).getHeight(); //加总所有的高度
			}
			if(lastRow > firstRow){
				isPartOfRowsRegion = true; //有行合并
			}
		}else{
			width = sheet.getColumnWidth(columnIndex);
			height += cell.getRow().getHeight();
		}
		map.put("isPartOfRowsRegion", isPartOfRowsRegion);
		map.put("firstRow", firstRow);
		map.put("lastRow", lastRow);
		map.put("width", width);
		map.put("height", height);
		return map;
	}

}

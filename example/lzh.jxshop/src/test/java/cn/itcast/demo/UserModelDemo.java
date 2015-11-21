package cn.itcast.demo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

public class UserModelDemo {
	@Test
	public void test() throws Exception{
		// create a new file
		FileOutputStream out = new FileOutputStream("d:\\workbook.xls");
		// create a new workbook
		Workbook wb = new HSSFWorkbook();
		// create a new sheet
		Sheet s = wb.createSheet();
		// declare a row object reference
		Row r = null;
		// declare a cell object reference
		Cell c = null;
		// create 3 cell styles
		CellStyle cs = wb.createCellStyle();
		CellStyle cs2 = wb.createCellStyle();
		CellStyle cs3 = wb.createCellStyle();
		DataFormat df = wb.createDataFormat();
		// create 2 fonts objects
		Font f = wb.createFont();
		Font f2 = wb.createFont();

		//set font 1 to 12 point type
		f.setFontHeightInPoints((short) 12);
		//make it blue
		f.setColor( (short)0xc );
		// make it bold
		//arial is the default font
		f.setBoldweight(Font.BOLDWEIGHT_BOLD);

		//set font 2 to 10 point type
		f2.setFontHeightInPoints((short) 10);
		//make it red
		f2.setColor( (short)Font.COLOR_RED );
		//make it bold
		f2.setBoldweight(Font.BOLDWEIGHT_BOLD);

		f2.setStrikeout( true );

		//set cell stlye
		cs.setFont(f);
		//set the cell format 
		cs.setDataFormat(df.getFormat("#,##0.0"));

		//set a thin border
		cs2.setBorderBottom(cs2.BORDER_THIN);
		//fill w fg fill color
		cs2.setFillPattern((short) CellStyle.SOLID_FOREGROUND);
		//set the cell format to text see DataFormat for a full list
		cs2.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));

		// set the font
		cs2.setFont(f2);

		// set the sheet name in Unicode
		wb.setSheetName(0, "\u0422\u0435\u0441\u0442\u043E\u0432\u0430\u044F " + 
		                   "\u0421\u0442\u0440\u0430\u043D\u0438\u0447\u043A\u0430" );
		// in case of plain ascii
		// wb.setSheetName(0, "HSSF Test");
		// create a sheet with 30 rows (0-29)
		int rownum;
		for (rownum = (short) 0; rownum < 30; rownum++)
		{
		    // create a row
		    r = s.createRow(rownum);
		    // on every other row
		    if ((rownum % 2) == 0)
		    {
		        // make the row height bigger  (in twips - 1/20 of a point)
		        r.setHeight((short) 0x249);
		    }

		    //r.setRowNum(( short ) rownum);
		    // create 10 cells (0-9) (the += 2 becomes apparent later
		    for (short cellnum = (short) 0; cellnum < 10; cellnum += 2)
		    {
		        // create a numeric cell
		        c = r.createCell(cellnum);
		        // do some goofy math to demonstrate decimals
		        c.setCellValue(rownum * 10000 + cellnum
		                + (((double) rownum / 1000)
		                + ((double) cellnum / 10000)));

		        String cellValue;

		        // create a string cell (see why += 2 in the
		        c = r.createCell((short) (cellnum + 1));
		        
		        // on every other row
		        if ((rownum % 2) == 0)
		        {
		            // set this cell to the first cell style we defined
		            c.setCellStyle(cs);
		            // set the cell's string value to "Test"
		            c.setCellValue( "Test" );
		        }
		        else
		        {
		            c.setCellStyle(cs2);
		            // set the cell's string value to "\u0422\u0435\u0441\u0442"
		            c.setCellValue( "\u0422\u0435\u0441\u0442" );
		        }


		        // make this column a bit wider
		        s.setColumnWidth((short) (cellnum + 1), (short) ((50 * 8) / ((double) 1 / 20)));
		    }
		}

		//draw a thick black border on the row at the bottom using BLANKS
		// advance 2 rows
		rownum++;
		rownum++;

		r = s.createRow(rownum);

		// define the third style to be the default
		// except with a thick black border at the bottom
		cs3.setBorderBottom(cs3.BORDER_THICK);

		//create 50 cells
		for (short cellnum = (short) 0; cellnum < 50; cellnum++)
		{
		    //create a blank type cell (no value)
		    c = r.createCell(cellnum);
		    // set it to the thick black border style
		    c.setCellStyle(cs3);
		}

		//end draw thick black border


		// demonstrate adding/naming and deleting a sheet
		// create a sheet, set its title then delete it
		s = wb.createSheet();
		wb.setSheetName(1, "DeletedSheet");
		wb.removeSheetAt(1);
		//end deleted sheet

		// write the workbook to the output stream
		// close our file (don't blow out our file handles
		wb.write(out);
		out.close();
	}
	@Test
	public void demo() throws Exception{
		Workbook wb = new HSSFWorkbook();
	    //Workbook wb = new XSSFWorkbook();
	    CreationHelper createHelper = wb.getCreationHelper();
	    Sheet sheet = wb.createSheet("new sheet");

	    // Create a row and put some cells in it. Rows are 0 based.
	    Row row = sheet.createRow(0);

	    // 第一个单元格不能作为一个日期类型
	    Cell cell = row.createCell(0);
	    cell.setCellValue(new Date());

	    //我们为第二个单元格设置日期样式，使用workbook.createCellStyle创建一个新单元格样式很重要
	    //同样地，你可以修该一个内建的样式，但是它不仅影响当前单元格，可能会影响其它的单元格。
	    CellStyle cellStyle = wb.createCellStyle();
	    cellStyle.setDataFormat(
	        createHelper.createDataFormat().getFormat("m/d/yy h:mm"));
	    cell = row.createCell(1);
	    cell.setCellValue(new Date());
	    cell.setCellStyle(cellStyle);

	    //you can also set date as java.util.Calendar
	    cell = row.createCell(2);
	    cell.setCellValue(Calendar.getInstance());
	    cell.setCellStyle(cellStyle);

	    // Write the output to a file
	    FileOutputStream fileOut = new FileOutputStream("d:\\workbook.xls");
	    wb.write(fileOut);
	    fileOut.close();
	}
}

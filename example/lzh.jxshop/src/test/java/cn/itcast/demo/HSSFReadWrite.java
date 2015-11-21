package cn.itcast.demo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

public final class HSSFReadWrite {

	/**
	 * creates an {@link HSSFWorkbook} the specified OS filename.
	 */
	private static HSSFWorkbook readFile(String filename) throws IOException {
		return new HSSFWorkbook(new FileInputStream(filename)); //连接一个已经存在文件
	}

	/**
	 * given a filename this outputs a sample sheet with just a set of
	 * rows/cells.
	 */
	private static void testCreateSampleSheet(String outputFilename) throws IOException {
		int rownum;
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet s = wb.createSheet();
		HSSFCellStyle cs = wb.createCellStyle();
		HSSFCellStyle cs2 = wb.createCellStyle();
		HSSFCellStyle cs3 = wb.createCellStyle();
		HSSFFont f = wb.createFont();
		HSSFFont f2 = wb.createFont();

		f.setFontHeightInPoints((short) 12);
		f.setColor((short) 0xA);
		f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		f2.setFontHeightInPoints((short) 10);
		f2.setColor((short) 0xf);
		f2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		cs.setFont(f);
		cs.setDataFormat(HSSFDataFormat.getBuiltinFormat("($#,##0_);[Red]($#,##0)"));
		cs2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cs2.setFillPattern((short) 1); // fill w fg
		cs2.setFillForegroundColor((short) 0xA);
		cs2.setFont(f2);
		wb.setSheetName(0, "HSSF Test");
		for (rownum = 0; rownum < 300; rownum++) {
			HSSFRow r = s.createRow(rownum);
			if ((rownum % 2) == 0) {
				r.setHeight((short) 0x249); //设置行的高度
			}

			for (int cellnum = 0; cellnum < 50; cellnum += 2) {
				HSSFCell c = r.createCell(cellnum); 
				c.setCellValue(rownum * 10000 + cellnum
						+ (((double) rownum / 1000) + ((double) cellnum / 10000)));
				if ((rownum % 2) == 0) {
					c.setCellStyle(cs); //第一个单元格样式，隔行设置样式
				}
				c = r.createCell(cellnum + 1);
				c.setCellValue(new HSSFRichTextString("TEST"));
				// 50 characters divided by 1/20th of a point
				s.setColumnWidth(cellnum + 1, (int) (50 * 8 / 0.05));
				if ((rownum % 2) == 0) {
					c.setCellStyle(cs2); //第二个单元格样式
				}
			}
		}

		// draw a thick black border on the row at the bottom using BLANKS
		rownum++;
		rownum++;
		HSSFRow r = s.createRow(rownum);
		cs3.setBorderBottom(HSSFCellStyle.BORDER_THICK);
		for (int cellnum = 0; cellnum < 50; cellnum++) {
			HSSFCell c = r.createCell(cellnum);
			c.setCellStyle(cs3);
		}
		s.addMergedRegion(new CellRangeAddress(0, 3, 0, 3));
		s.addMergedRegion(new CellRangeAddress(100, 110, 100, 110));

		// end draw thick black border
		// create a sheet, set its title then delete it
		s = wb.createSheet();
		wb.setSheetName(1, "DeletedSheet");
		wb.removeSheetAt(1);

		// end deleted sheet
		FileOutputStream out = new FileOutputStream(outputFilename);
		wb.write(out);
		out.close();
	}

	/**
     * Method main
     *
     * 给定一个参数作为文件名，将会输出文件单元格内的所有的值以及值类型
   
     * 给定二个参数，第一个参数作为文件名，第二个参数是write,则创建指定文件名文件

     * 给定二个参数，第一个参数作为文件名，第二个不是write，则为输出文件名
   
     * 给定三个参数，第一个参数作为文件名，第二个参数是输出文件名，第三个是modify1，则修改文件后输出
     */
	public static void main(String[] args) {
		if (args.length < 1) {
			System.err.println("At least one argument expected");
			return;
		}

		String fileName = args[0];
		try {
			if (args.length < 2) {

				HSSFWorkbook wb = HSSFReadWrite.readFile(fileName);

				System.out.println("Data dump:\n");

				for (int k = 0; k < wb.getNumberOfSheets(); k++) {
					HSSFSheet sheet = wb.getSheetAt(k);
					int rows = sheet.getPhysicalNumberOfRows(); //获取有内容的行数
					System.out.println("Sheet " + k + " \"" + wb.getSheetName(k) + "\" has " + rows + " row(s).");
					for (int r = 0; r < rows; r++) {
						HSSFRow row = sheet.getRow(r); //整行没有值返回null
						if (row == null) {
							continue;
						}

						int cells = row.getPhysicalNumberOfCells();
						System.out.println("\nROW " + row.getRowNum() + " has " + cells + " cell(s).");
						for (int c = 0; c < cells; c++) {
							HSSFCell cell = row.getCell(c); //没有值返回null
							String value = null;
							if(null == cell) { //如果在有值的单元数内出现空的单元格，说明还有有值的单元格没有遍历到，
								++cells; //需要增加一个单元格范围，直到最后一个有值单元格获取到
								continue;
							}
							switch (cell.getCellType()) {
								case HSSFCell.CELL_TYPE_FORMULA:
									value = "FORMULA value=" + cell.getCellFormula();
									break;

								case HSSFCell.CELL_TYPE_NUMERIC:
									value = "NUMERIC value=" + cell.getNumericCellValue();
									break;

								case HSSFCell.CELL_TYPE_STRING:
									value = "STRING value=" + cell.getStringCellValue();
									break;

								default:
							}
							System.out.println("CELL col=" + cell.getColumnIndex() + " VALUE="+ value);
						}
					}
				}
			} else if (args.length == 2) {
				if (args[1].toLowerCase().equals("write")) {
					System.out.println("Write mode");
					long time = System.currentTimeMillis();
					HSSFReadWrite.testCreateSampleSheet(fileName); //直接以指定的文件名创建文件

					System.out.println("" + (System.currentTimeMillis() - time) + " ms generation time");
				} else {
					System.out.println("readwrite test");
					HSSFWorkbook wb = HSSFReadWrite.readFile(fileName); //把指定的文件读入，然后在写入到其它地方，相当于复制和移动
					FileOutputStream stream = new FileOutputStream(args[1]);

					wb.write(stream);
					stream.close();
				}//修改文件
			} else if (args.length == 3 && args[2].toLowerCase().equals("modify1")) {
				// delete row 0-24, row 74 - 99 && change cell 3 on row 39 to string "MODIFIED CELL!!"

				HSSFWorkbook wb = HSSFReadWrite.readFile(fileName);
				FileOutputStream stream = new FileOutputStream(args[1]);
				HSSFSheet sheet = wb.getSheetAt(0);

				for (int k = 0; k < 25; k++) {
					HSSFRow row = sheet.getRow(k);

					sheet.removeRow(row);
				}
				for (int k = 74; k < 100; k++) {
					HSSFRow row = sheet.getRow(k);

					sheet.removeRow(row);
				}
				HSSFRow row = sheet.getRow(39);
				HSSFCell cell = row.getCell(3);
				cell.setCellValue("MODIFIED CELL!!!!!");

				wb.write(stream);
				stream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

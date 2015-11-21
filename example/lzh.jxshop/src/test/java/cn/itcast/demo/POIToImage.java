package cn.itcast.demo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

public class POIToImage {
	@Test
	public void insertImage() throws Exception {
		// create a new workbook
		Workbook wb = new HSSFWorkbook(); // or new HSSFWorkbook();

		// add picture data to this workbook.
		InputStream is = new FileInputStream("d:\\624ab3d2-12f3-4dd8-b438-c03a3018b170.png");
		//如果不是标准格式的图片，可以转换图片类型
		ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
		BufferedImage bufferImg = ImageIO.read(is);
		ImageIO.write(bufferImg, "jpg", byteArrayOut); //转换为jpg格式图片
		
		//byte[] bytes = IOUtils.toByteArray(is); //转换为字节
		int pictureIdx = wb.addPicture(byteArrayOut.toByteArray(), Workbook.PICTURE_TYPE_JPEG);
		is.close();

		CreationHelper helper = wb.getCreationHelper();

		// create sheet
		Sheet sheet = wb.createSheet();

		// Create the drawing patriarch. This is the top level container for all shapes.
		Drawing drawing = sheet.createDrawingPatriarch();

		// add a picture shape
		ClientAnchor anchor = helper.createClientAnchor();
		// set top-left corner of the picture,
		// subsequent call of Picture#resize() will operate relative to it
		anchor.setCol1(3);
		anchor.setRow1(2);
		//自己创建详细位置信息
		HSSFClientAnchor anchor1 = new HSSFClientAnchor(200, 100, 0, 0, (short) 3, 2, (short) 6, 7);
		Picture pict = drawing.createPicture(anchor1, pictureIdx);

		// auto-size picture relative to its top-left corner
		//pict.resize();使用帮助类设置的位置信息，是图片按照自身大小显示

		// save workbook
		String file = "d:\\picture.xls";
		if (wb instanceof XSSFWorkbook)
			file += "x";
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		fileOut.close();
	}
}

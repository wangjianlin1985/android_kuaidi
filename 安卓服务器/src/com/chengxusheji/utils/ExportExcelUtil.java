package com.chengxusheji.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class ExportExcelUtil {

	public void exportExcel(Collection<String[]> dataset, OutputStream out) {
		exportExcel("","����POI����EXCEL�ĵ�", null, dataset, out, "yyyy-MM-dd");
	}

	public void exportExcel(String rootPath,String title, String[] headers,
			Collection<String[]> dataset, OutputStream out) {
		exportExcel(rootPath,title, headers, dataset, out, "yyyy-MM-dd");
	}

	public void exportExcel(String[] headers, Collection<String[]> dataset,
			OutputStream out, String pattern) {
		exportExcel("","����POI����EXCEL�ĵ�", headers, dataset, out, pattern);
	}

	/**
	 * ����һ��ͨ�õķ��������Խ�������JAVA�����в��ҷ���һ��������������EXCEL ����ʽ�����ָ��IO�豸��
	 * 
	 * @param title
	 *            ��������
	 * @param headers
	 *            ���������������
	 * @param dataset
	 *            ��Ҫ��ʾ�����ݼ���
	 * @param out
	 *            ������豸�����������󣬿��Խ�EXCEL�ĵ������������ļ�����������
	 * @param pattern
	 *            �����ʱ�����ݣ��趨�����ʽ��Ĭ��Ϊ"yyy-MM-dd"
	 */
	@SuppressWarnings("unchecked")
	public void exportExcel(String rootPath,String title, String[] headers,
			Collection<String[]> dataset, OutputStream out, String pattern) {
		// ����һ��������
		HSSFWorkbook workbook = new HSSFWorkbook();
		// ����һ�����
		HSSFSheet sheet = workbook.createSheet(title);
		// ���ñ��Ĭ���п��Ϊ15���ֽ�
		sheet.setDefaultColumnWidth((short) 15);
		// ����һ����ʽ
		HSSFCellStyle style = workbook.createCellStyle();
		// ������Щ��ʽ
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// ����һ������
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// ������Ӧ�õ���ǰ����ʽ
		style.setFont(font);
		// ���ɲ�������һ����ʽ
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// ������һ������
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// ������Ӧ�õ���ǰ����ʽ
		style2.setFont(font2);

		// ����һ����ͼ�Ķ���������
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// ����ע�͵Ĵ�С��λ��,����ĵ�
		HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
				0, 0, 0, (short) 4, 2, (short) 6, 5));
		// ����ע������
		comment.setString(new HSSFRichTextString("������POI�����ע�ͣ�"));
		// ����ע�����ߣ�������ƶ�����Ԫ�����ǿ�����״̬���п���������.
		comment.setAuthor("leno");

		// ������������
		HSSFRow row = sheet.createRow(0);
		for (short i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}

		// �����������ݣ�����������
		Iterator<String[]> it = dataset.iterator();
		int index = 0;
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			String[] t = (String[]) it.next();
			for (short i = 0; i < t.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(style2);
				// �ж�ֵ�����ͺ����ǿ������ת��
				String textValue = t[i];
				 
				if(textValue.startsWith("upload/"))
				{
					// ��ͼƬʱ�������и�Ϊ50px;  
                    row.setHeightInPoints(50);   
					// ����ͼƬ�����п��Ϊ80px,ע�����ﵥλ��һ������
					sheet.setColumnWidth(i, (short) (35.7 * 80)); // 
					//sheet.autoSizeColumn(i);  
					BufferedInputStream bis;
					byte[] buf = null;
					try {
						bis = new BufferedInputStream(  
						        new FileInputStream(rootPath + textValue));
						buf = new byte[bis.available()];  
			            while ((bis.read(buf)) != -1) {}   
			            
			            HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023,
								255, (short) i, index, (short) i, index);
						anchor.setAnchorType(2); 
						patriarch.createPicture(anchor,
								workbook.addPicture( buf,
						HSSFWorkbook.PICTURE_TYPE_JPEG));  
					} catch (Exception e) {  
						e.printStackTrace();
					}     
					
				} else {
					HSSFRichTextString richString = new HSSFRichTextString(
							textValue);
					HSSFFont font3 = workbook.createFont();
					font3.setColor(HSSFColor.BLUE.index);
					richString.applyFont(font3);
					cell.setCellValue(richString);
				}
				
				/*
				 * else if (value instanceof byte[]) { // ��ͼƬʱ�������и�Ϊ60px;
				 * row.setHeightInPoints(60); // ����ͼƬ�����п��Ϊ80px,ע�����ﵥλ��һ������
				 * sheet.setColumnWidth(i, (short) (35.7 * 80)); //
				 * sheet.autoSizeColumn(i); byte[] bsValue = (byte[]) value;
				 * HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023,
				 * 255, (short) 6, index, (short) 6, index);
				 * anchor.setAnchorType(2); patriarch.createPicture(anchor,
				 * workbook.addPicture( bsValue,
				 * HSSFWorkbook.PICTURE_TYPE_JPEG)); } else{ //�����������Ͷ������ַ����򵥴���
				 * textValue = value.toString(); }
				 */

			}
		}
		try {
			workbook.write(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

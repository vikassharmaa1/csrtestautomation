/**
 * 
 */
package com.csr.utils;


import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;



/**
 * @author akaushi3
 *
 */

public class DataProviderUtils {
	
	public static String LoginTestData = "LoginTest";
	static Workbook book;
	static Sheet sheet;
	public static String SHEET_PATH = System.getProperty("user.dir") + "/src/test/resources/Data/TestData.xlsx";
	
	public static Object[][] getSheetData(String sheetName){
		
		FileInputStream file = null;
		
		try {
			
			file = new FileInputStream(SHEET_PATH);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			
			book = WorkbookFactory.create(file);
		} catch (InvalidFormatException e) {
			// TODO: handle exception
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sheet = book.getSheet(sheetName);
		
		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		
		for(int rowNum = 0; rowNum<sheet.getLastRowNum(); rowNum++){
			for(int col=0; col<sheet.getRow(0).getLastCellNum(); col++){
				
				data[rowNum][col] = sheet.getRow(rowNum+1).getCell(col).toString();
				//System.out.println("Data is " + data[rowNum][col]);
			}
		}
		return data;
	}


}




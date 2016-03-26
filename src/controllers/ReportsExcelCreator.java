package com.sarf.excel.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ReportsExcelCreator {

      @SuppressWarnings("deprecation")
      public String downloadExcel(ServletOutputStream out){
           
            int nRow = 1;
            String strQuery = null;
            Connection con = null;
           
            HSSFRow row;
            HSSFCell cell;
                 
            try {
                 
                  HSSFWorkbook wb = new HSSFWorkbook();
                  HSSFSheet sheet = wb.createSheet("Banned Students");
                 
                  /* Getting connection here for mysql database */
                  Class.forName("com.mysql.jdbc.Driver").newInstance();
                  con = DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/test","sarfuser","password");
                 
                  if(con==null)
                        return "Connection Failed";
                  /* Database Query */               
                  strQuery = "select * from banned";
                  Statement stmt=con.createStatement();
                ResultSet rs=stmt.executeQuery(strQuery);
               
                /* Setting Font Style for Header Row */
                  sheet.setColumnWidth(0, 5000);
                  sheet.setColumnWidth(1, 7000);
                  sheet.setColumnWidth(3, 5000);
                  sheet.setColumnWidth(4, 5000);
                  sheet.setColumnWidth(5, 7000);
                  sheet.setColumnWidth(6, 5000);
                  sheet.setColumnWidth(7, 5000);
                 
                  /* Creating the Font Style here */
                  HSSFFont boldFont = wb.createFont();
                  boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                  boldFont.setColor(HSSFFont.COLOR_RED);
                  HSSFCellStyle cellStyle = wb.createCellStyle();
                  cellStyle.setFont(boldFont);
                 
                 
                  // Create a row for header
                  row = sheet.createRow( (short)0);
                 
                  cell = row.createCell( (short)0);
                  cell.setCellValue("Banned Id");
                  cell.setCellStyle(cellStyle);
                 
                  cell = row.createCell( (short)1);
                  cell.setCellValue("UserID");
                  cell.setCellStyle(cellStyle);
                 
                  cell = row.createCell( (short)2);
                  cell.setCellValue("AdminID");
                  cell.setCellStyle(cellStyle);
                 
                  cell = row.createCell( (short)3);
                  cell.setCellValue("Ban Start Date");
                  cell.setCellStyle(cellStyle);
                  
                  cell = row.createCell( (short)4);
                  cell.setCellValue("Ban End Date");
                  cell.setCellStyle(cellStyle);
                  
                  cell = row.createCell( (short)5);
                  cell.setCellValue("Penalty Count");
                  cell.setCellStyle(cellStyle);
                  
                  cell = row.createCell( (short)6);
                  cell.setCellValue("Description");
                  cell.setCellStyle(cellStyle);
                  
                  cell = row.createCell( (short)7);
                  cell.setCellValue("Status");
                  cell.setCellStyle(cellStyle);
                 
                 
                  // Reading one row of table at a time and putting the values into excel cell
                  while(rs.next()){
                        row = sheet.createRow( (short)nRow);
                        // Create a cell and put a value in it.
                        cell = row.createCell( (short)0);
                       
                        cell.setCellValue(rs.getString(1));
                        cell = row.createCell( (short)1);
                        cell.setCellValue(rs.getString(2));
                        cell = row.createCell( (short)2);
                        cell.setCellValue(rs.getString(3));
                        cell = row.createCell( (short)3);
                        cell.setCellValue(rs.getString(4));
                        cell = row.createCell( (short)4);
                        cell.setCellValue(rs.getString(5));
                        cell = row.createCell( (short)5);
                        cell.setCellValue(rs.getString(6));
                        cell = row.createCell( (short)6);
                        cell.setCellValue(rs.getString(7));
                        cell = row.createCell( (short)7);
                        nRow++;
                  }
                  wb.write(out);
                  return "File downloaded successfully";
            }
            catch (Exception e) {
                  return e.getMessage();
            }
      }
}
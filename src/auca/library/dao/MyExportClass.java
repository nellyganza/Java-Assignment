/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auca.library.dao;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

/**
 *
 * @author NISHIMWE Elyse
 */
public class MyExportClass {
    
    public String getFileName(String baseName){
        DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String dateTimeInfo = dateformat.format(new Date());
        return baseName.concat(String.format("_%s.xlsx",dateTimeInfo)); 
    }
    public void export(JTable table,String tablename){
        String path = getFileName(tablename.concat("_Export"));
        try {
            Workbook book = new XSSFWorkbook();
            Sheet sheet = book.createSheet();
            writeHeaderLine(table, sheet);
            writeDataLines(table, book, sheet);
            FileOutputStream output = new FileOutputStream(path);
            book.write(output);
            book.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null ,ex.getMessage());
        }
        
    }
    public void writeHeaderLine(JTable table,Sheet sheet){
        int colnum = table.getColumnCount();
        Row headerrow = sheet.createRow(0);
        for(int i =0 ;i<colnum;i++){
            String colname = table.getColumnName(i);
            Cell cell = headerrow.createCell(i);
            cell.setCellValue(colname);
        }
        
    }
    public void writeDataLines(JTable table,Workbook book,Sheet sheet){
        int colnum = table.getColumnCount();
        int rowcount = 1;
        for(int j=0;j<table.getRowCount();j++){
            Row row = sheet.createRow(rowcount++);
            
            for(int i = 0;i<colnum;i++){
                Object valueObject = table.getValueAt(j,i);
                Cell cell = row.createCell(i);
                if(valueObject instanceof Boolean)
                    cell.setCellValue((Boolean) valueObject);
                else if(valueObject instanceof Double)
                    cell.setCellValue((Double) valueObject);
                else if(valueObject instanceof Integer)
                    cell.setCellValue((Integer) valueObject);
                else if(valueObject instanceof Float)
                    cell.setCellValue((Float) valueObject);
                else if(valueObject instanceof Date)
                    cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(valueObject));
                else if(valueObject instanceof BigDecimal)
                    cell.setCellValue(valueObject.toString());
                else
                    cell.setCellValue((String) valueObject);
            }
        }
        
    }
     private void formatDateCell(XSSFWorkbook workbook, Cell cell)
     {        
         CellStyle cellStyle = workbook.createCellStyle();
        CreationHelper creationHelper = workbook.getCreationHelper();
        cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));        
        cell.setCellStyle(cellStyle);    
     }
}
package com.rahul.zeppelinonlinebank.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.rahul.zeppelinonlinebank.pojo.PrimaryTransaction;
import com.rahul.zeppelinonlinebank.pojo.SavingsTransaction;

public class ExcelSavingsStatementReportView extends AbstractXlsView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Content-disposition", "attachment; filename=\"SavingsStatement.xls\"");
		@SuppressWarnings("unchecked")
		List<SavingsTransaction> transactionList = (List<SavingsTransaction>) model.get("transactionList");
		Sheet sheet = workbook.createSheet("Primary Transaction Statment");
		
		Row header = sheet.createRow(0);
		
		
		header.createCell(0).setCellValue("Date");
		header.createCell(1).setCellValue("Description");
		header.createCell(2).setCellValue("Type");
		header.createCell(3).setCellValue("Status");
		header.createCell(4).setCellValue("Amount");
		header.createCell(5).setCellValue("Available Balance");
		header.createCell(6).setCellValue("Transaction Type");
		header.createCell(6).setCellValue("Receivers Account Number");
		
		int rowNum = 1;
		for(SavingsTransaction t : transactionList) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(t.getDate());
			row.createCell(1).setCellValue(t.getDescription());
			row.createCell(2).setCellValue(t.getType());
			row.createCell(3).setCellValue(t.getStatus());
			row.createCell(4).setCellValue(t.getAmount());
			row.createCell(5).setCellValue(String.valueOf(t.getAvailableBalance()));
			row.createCell(6).setCellValue(t.getTransactionType());
			row.createCell(7).setCellValue(t.getReceiversAccountNumber());
			
		}
		
	}
	

}

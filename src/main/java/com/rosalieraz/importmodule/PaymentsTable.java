package com.rosalieraz.importmodule;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rosalieraz.importmodule.model.Payments;
import com.rosalieraz.importmodule.repository.PaymentsRepository;

@Component
public class PaymentsTable {

	@Autowired
	private IteratorLoading itr;
	
	@Autowired
	private Enumerations enums;
	
	
	@Autowired
	PaymentsRepository paymentsRepository;

	
	public List<Object[]> read (XSSFWorkbook workbook, List<String> paymentFields,
			Map<String, Object> config) throws ConstraintViolationException {
		
		
		Iterator<Row> iterator = itr.load(workbook);
		List<Object[]> errors = new ArrayList<>();

		iterator.next();
		while (iterator.hasNext()) {

			Integer paymentId = 0;
			Integer userId = 0;
			Integer paymentType = 0;
			String code = "";
			Integer isRecurring = 0;
			Date pDate = new Date();
			String fileName = "";
			String desc = "";
			Date bDate = new Date();
			Date createDate = new Date();
			Date updateDate = new Date();
			Integer createUserId = 0;
			Integer updateUserId = 0;

			XSSFRow row = (XSSFRow) iterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();

			while (cellIterator.hasNext()) {

				XSSFCell cell = (XSSFCell) cellIterator.next();
				int cellIndex = cell.getColumnIndex();

				switch(cellIndex) {

				case 0:
						paymentId = (int) cell.getNumericCellValue();
					break;
					
				case 1:
						userId = (int) cell.getNumericCellValue();
					break;
					
				case 2:
						paymentType = (int) enums.read(workbook, paymentFields.get(cellIndex))
								.get(cell.getStringCellValue().trim());
					break;
					
				case 3:
						code = cell.getStringCellValue();
					break;
					
				case 4:
						isRecurring = (int) enums.read(workbook, paymentFields.get(cellIndex))
								.get(cell.getStringCellValue().trim());
					break;
					
				case 5:
						pDate = cell.getDateCellValue();
					break;
					
				case 6:
						fileName = cell.getStringCellValue();
					break;
					
				case 7:
						desc = cell.getStringCellValue();
					break;
					
				case 8:
						bDate = cell.getDateCellValue();
					break;
					
				case 9:
						createDate = cell.getDateCellValue();
					break;
					
				case 10:
						updateDate = cell.getDateCellValue();
					break;
					
				case 11:
						createUserId = (int) cell.getNumericCellValue();
					break;
					
				case 12:
						updateUserId = (int) cell.getNumericCellValue();
					break;
					
				default:
					break;
				
				}
			}
			
			try {
			
				paymentsRepository.save(new Payments(paymentId, userId, paymentType, code, isRecurring, pDate, fileName, desc, bDate, createDate, updateDate, createUserId, updateUserId));
			} catch (ConstraintViolationException e) {
				
				e.getConstraintViolations().forEach(
						v -> errors.add(new Object[] { config.get("Table"), config.get("Identifier"), v.getMessage() }));
			}
		}
		return errors;
	}
	
}

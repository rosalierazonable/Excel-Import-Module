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

import com.rosalieraz.importmodule.model.EventAttendance;
import com.rosalieraz.importmodule.repository.EventAttendanceRepository;

@Component
public class EventAttendanceTable {

	
	@Autowired
	private IteratorLoading itr;
	
	@Autowired
	EventAttendanceRepository eARepository;
	
	
	public List<Object[]> read (XSSFWorkbook workbook, List<String> interestFields,
			Map<String, Object> config) throws ConstraintViolationException {
		
		
		Iterator<Row> iterator = itr.load(workbook);
		List<Object[]> errors = new ArrayList<>();

		iterator.next();
		while (iterator.hasNext()) {

			Integer eventId = 0;
			Integer userId = 0;
			Integer paymentId = 0;
			Date signUpDate = new Date();

			XSSFRow row = (XSSFRow) iterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();

			while (cellIterator.hasNext()) {

				XSSFCell cell = (XSSFCell) cellIterator.next();
				int cellIndex = cell.getColumnIndex();

				switch(cellIndex) {
					
				case 0:
						eventId = (int) cell.getNumericCellValue();
					break;
					
				case 1:
						userId = (int) cell.getNumericCellValue();
					break;
					
				case 2:
						paymentId = (int) cell.getNumericCellValue();
					break;
					
				case 3:
						signUpDate = cell.getDateCellValue();
					break;
					
				default:
					break;
					
				}
			}
			
			try {

				eARepository.save(new EventAttendance(eventId, userId, paymentId, signUpDate));

			} catch (ConstraintViolationException e) {
				e.getConstraintViolations().forEach(
						v -> errors.add(new Object[] { config.get("Table"), config.get("Identifier"), v.getMessage() }));
			}
		}
		
		return errors;
	}
	
}

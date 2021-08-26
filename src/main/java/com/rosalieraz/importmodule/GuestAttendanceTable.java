package com.rosalieraz.importmodule;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rosalieraz.importmodule.model.GuestAttendance;
import com.rosalieraz.importmodule.repository.GuestAttendanceRepository;

@Component
public class GuestAttendanceTable {
	
	@Autowired
	private IteratorLoading itr;
		
	@Autowired
	GuestAttendanceRepository gARepository;
	
	
	public List<Object[]> read (XSSFWorkbook workbook, List<String> gAFields,
			Map<String, Object> config) throws ConstraintViolationException {
		
		Iterator<Row> iterator = itr.load(workbook);
		List<Object[]> errors = new ArrayList<>();
		
		DataFormatter dataFormatter= new DataFormatter();

		iterator.next();
		while (iterator.hasNext()) {
		
			Integer userId = 0;
			Integer eventId = 0;
			Integer paymentId = 0;
			String fName = "";
			String lName = "";
			String mName = "";
			String suffix = "";
			String mail = "";
			String cellular = "";
			String street = "";
			String city = "";
			String state = "";
			String country = "";
			String zipcode = "";
			String bikeModel = "";
			Date createDate = new Date();
			Date updateDate = new Date();
			Integer createUserId = 0;
			Integer updateUserId = 0;
			Integer createTS = 0;
			Integer updateTS = 0;
			
			XSSFRow row = (XSSFRow) iterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();

			while (cellIterator.hasNext()) {

				XSSFCell cell = (XSSFCell) cellIterator.next();
				int cellIndex = cell.getColumnIndex();

				switch(cellIndex) {
				
				case 0: 
						userId = (int) cell.getNumericCellValue();
					break;
					
				case 1: 
						fName = cell.getStringCellValue();
					break;
					
				case 2: 
						lName = cell.getStringCellValue();
					break;
					
				case 3: 
						mName = cell.getStringCellValue();
					break;
					
				case 4: 
						suffix = cell.getStringCellValue();
					break;
					
				case 5: 
						mail = cell.getStringCellValue();
					break;
					
				case 6: 
						cellular = dataFormatter.formatCellValue(cell);
					break;
					
				case 7: 
						street = cell.getStringCellValue();
					break;
					
				case 8: 
						city = cell.getStringCellValue();
					break;
					
				case 9: 
						state = cell.getStringCellValue();
					break;
					
				case 10: 
						zipcode = dataFormatter.formatCellValue(cell);
					break;
					
				case 11:
						country = cell.getStringCellValue();
					break;
					
				case 12: 
						bikeModel = cell.getStringCellValue();
					break;
					
				case 13: 
						createDate = cell.getDateCellValue();
					break;
					
				case 14: 
						createUserId = (int) cell.getNumericCellValue();
					break;
					
				case 15: 
						updateDate = cell.getDateCellValue();
					break;
					
				case 16: 
						updateUserId = (int) cell.getNumericCellValue();
					break;
					
				case 17: 
						updateTS = (int) cell.getNumericCellValue();
					break;
					
				case 18: 
						createTS = (int) cell.getNumericCellValue();
					break;
					
				case 19: 
						eventId = (int) cell.getNumericCellValue();
					break;
					
				case 20: 
						paymentId = (int) cell.getNumericCellValue();
					break;
					
				default:
					break;
				
				}
			}
			
			try {

				gARepository.save(new GuestAttendance(userId, eventId, paymentId, fName, lName, mName, suffix, mail, cellular, street, 
							city, state, country, zipcode, bikeModel, createDate, updateDate, createUserId, updateUserId, updateTS, createTS));

			} catch (ConstraintViolationException e) {
				e.getConstraintViolations().forEach(
						v -> errors.add(new Object[] { config.get("Table"), config.get("Identifier"), v.getMessage() }));
			}
		}
		
		return errors;
	}
	
}

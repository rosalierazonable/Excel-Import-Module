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

import com.rosalieraz.importmodule.model.Interests;
import com.rosalieraz.importmodule.repository.InterestsRepository;

@Component
public class InterestsTable {

	@Autowired
	private IteratorLoading itr;
	
	@Autowired
	private Enumerations enums;
	
	
	@Autowired
	InterestsRepository iRepository;

	
	public List<Object[]> read (XSSFWorkbook workbook, List<String> interestFields,
			Map<String, Object> config) throws ConstraintViolationException {
		
		Iterator<Row> iterator = itr.load(workbook);
		List<Object[]> errors = new ArrayList<>();

		iterator.next();
		while (iterator.hasNext()) {

			Integer id = 0;
			String name = "";
			Integer isDeleted = 0;
			Date createDate = new Date();
			Date updateDate = new Date();
			Integer createUserId = 0;
			Integer updateUserId = 0;
			String desc = "";
			

			XSSFRow row = (XSSFRow) iterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
	
			while (cellIterator.hasNext()) {
	
				XSSFCell cell = (XSSFCell) cellIterator.next();
				int cellIndex = cell.getColumnIndex();
	
				switch(cellIndex) {
	
				case 0:
						id = (int) cell.getNumericCellValue();
					break;
					
				case 1:
						name = cell.getStringCellValue();
					break;
					
				case 2:
						isDeleted = (int) enums.read(workbook, interestFields.get(cellIndex))
						.get(cell.getStringCellValue().trim());
					break;
					
				case 3:
						createDate = cell.getDateCellValue();
					break;
					
				case 4:
						createUserId = (int) cell.getNumericCellValue();
					break;

				case 5:
					updateDate = cell.getDateCellValue();
					break;
					
				case 6:
					updateUserId = (int) cell.getNumericCellValue();
				break;
				
				case 7:
					desc = cell.getStringCellValue();
					break;
					
				default:
					break;
				}
			}
			
			try {

				iRepository.save(new Interests(id, name, isDeleted, createDate, createUserId, updateDate, updateUserId, desc));

			} catch (ConstraintViolationException e) {
				e.getConstraintViolations().forEach(
						v -> errors.add(new Object[] { config.get("Table"), config.get("Identifier"), v.getMessage() }));
			}
		}
		return errors;
	}
}

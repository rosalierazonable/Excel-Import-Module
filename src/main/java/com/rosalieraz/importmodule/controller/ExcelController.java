package com.rosalieraz.importmodule.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rosalieraz.importmodule.model.Events;
import com.rosalieraz.importmodule.repository.EventsRepository;
import com.rosalieraz.importmodule.service.FileHandlingService;

@RestController
public class ExcelController {

	@Autowired
	private FileHandlingService fhService;

	@Autowired
	private EventsRepository eRepository;

	/*
	 * Get List of Files
	 */

	@GetMapping("/file-list")
	public List<String> getFileList() {
		return fhService.getFileList(".\\src\\main\\resources\\static\\files\\", "xlsx");
	}

	/*
	 * Get Configs
	 */

	@GetMapping("/configs")
	public Map<String, Object> getConfigs() throws IOException {

		return fhService.readingConfigDetails(
				new XSSFWorkbook(new FileInputStream(".\\src\\main\\resources\\static\\files\\Events1.xlsx")));
	}

	/*
	 * Add Events from Excel: List of List of events, across all excel files
	 */

	@PostMapping("/addEvents")
	public void addEvents() throws IOException {
		List<List<Events>> eventsList = fhService.loopThroughFiles();
		for (List<Events> events : eventsList)
			eRepository.saveAll(events);
	}

	/*
	 * Add Events from Excel: List of Events per excel file
	 */

	@PostMapping("/addEvent")
	public List<Events> addEvents(@Valid @RequestBody List<Events> eventsList) {
		return eRepository.saveAll(eventsList);
	}

	/*
	 * Get All Eevents
	 */

	@GetMapping("/events")
	public List<List<Events>> getEventsList() throws IOException {
		return fhService.loopThroughFiles();
	}
	
	
	/*
	 * Writing to Excel
	 */

	@GetMapping("/logError")
	public List<Object[]>  writeToExcel( @RequestBody List<Object[]> log ) throws IOException {
		return fhService.writeIntoExcel(log);
	}

	
	
	/*
	 * Copy Files
	 */
	@GetMapping("/copyFiles")
	public void copyFiles(@RequestBody List<String> fileNames) throws IOException {
		fhService.copyFile(fileNames);
	}
	
	
	
	/*
	 * Move a File
	 */

	@GetMapping("/moveFile")
	public void moveFile () {
		
		String fileName = "sample2.jpg";
		String targetDir = "server";
		
		fhService.moveFile(fileName, targetDir);
		
	}

}

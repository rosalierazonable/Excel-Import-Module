package com.rosalieraz.importmodule.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
/*
 * import java.util.HashMap;
 * import java.io.IOException;
 * import java.util.HashMap;
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	public List<String> getFileList () {
		return fhService.getFileList();
	}
	
	/*
	 * Get Configs
	 */
	
	@GetMapping("/configs")
	public Map<String, Object> getConfigs () throws IOException {
		
		return fhService.readingConfigDetails(new XSSFWorkbook(new FileInputStream(".\\src\\main\\resources\\static\\files\\Events1.xlsx")));
	}
	
	/*
	 * Add Events from Excel
	 */
	
	@PostMapping("/addEvents") 
	public void addEvents () throws IOException { 
		
		  List<List<Events>> eventsList = fhService.loopThroughFiles();
		  for(List<Events> events: eventsList) eRepository.saveAll(events);
		 
	}
}

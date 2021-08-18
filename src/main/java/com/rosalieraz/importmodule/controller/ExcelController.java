package com.rosalieraz.importmodule.controller;

import java.io.IOException;
/*import java.io.IOException;
import java.util.HashMap;*/
import java.util.List;

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
	 * Add Events from Excel
	 */
	
	@PostMapping("/addEvents") 
	public void getEvents () throws IOException { 
		List<List<Events>> eventsList = fhService.loopThroughFiles();
		for(List<Events> events: eventsList)
			eRepository.saveAll(events);
	}
}

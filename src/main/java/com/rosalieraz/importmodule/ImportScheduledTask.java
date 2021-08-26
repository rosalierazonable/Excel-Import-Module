package com.rosalieraz.importmodule;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.rosalieraz.importmodule.service.FileHandlingService;

@Component
public class ImportScheduledTask {

	@Autowired
	FileHandlingService fhService;
	
	@Scheduled(cron = "0 49 9 * * 1-5")
	public void importExcel () {
		
		try {
			fhService.loopThroughFiles();
		} catch (IOException e) {
			e.getMessage();
		}
	}
	
}

package com.rosalieraz.importmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.purge.starter.dispose.annotation.EnableGlobalDispose;


@EnableGlobalDispose
@SpringBootApplication
public class ExcelImportModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExcelImportModuleApplication.class, args);
	}

}

@Configuration
@EnableScheduling
@ConditionalOnProperty(name="scheduling.enabled", matchIfMissing = true)
class ScheddulingConfiguration {
	
}

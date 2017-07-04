package com.example.demo;

import com.example.demo.processor.DataSourceImportExportProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

@SpringBootApplication
public class DemoApplication {

	@Resource
	private DataSourceImportExportProcessor dataSourceImportExportProcessor;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@PostConstruct
	public void init() {
		dataSourceImportExportProcessor.importDB();
	}

	@PreDestroy
	public void onDestroy() {
		dataSourceImportExportProcessor.exportDB();
	}
}

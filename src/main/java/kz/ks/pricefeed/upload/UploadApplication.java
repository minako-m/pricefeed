package kz.ks.pricefeed.upload;

import javax.annotation.Resource;

import kz.ks.pricefeed.upload.service.FilesStorageServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import kz.ks.pricefeed.upload.service.FilesStorageService;

@SpringBootApplication
public class UploadApplication {

	public static void main(String[] args) {
		SpringApplication.run(UploadApplication.class, args);
	}

}
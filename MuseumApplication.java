package com.azuga.museum;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The execution of the project starts from this class
 */
@SpringBootApplication
public class MuseumApplication {
	public static final Logger logger = LogManager.getLogger(MuseumApplication.class.getName());

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		SpringApplication.run(MuseumApplication.class, args);
		long end = System.currentTimeMillis();
		logger.info("Execution of the project took {} ms to complete", (end - start));
	}

}

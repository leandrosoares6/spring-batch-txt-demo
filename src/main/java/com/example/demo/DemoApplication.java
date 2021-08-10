package com.example.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableBatchProcessing
public class DemoApplication {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	public Step step() {
		return stepBuilderFactory.get("step1").tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				try {
					File myObj = new File("tables.txt");
					Scanner myReader = new Scanner(myObj);
					while (myReader.hasNextLine()) {
						String data = myReader.nextLine();
						System.out.println(data);
					}
					myReader.close();
				} catch (FileNotFoundException e) {
					System.out.println("An error occurred.");
					e.printStackTrace();
				}
				return RepeatStatus.FINISHED;
			}
		}).build();
	}

	@Bean
	Job job() {
		return jobBuilderFactory.get("jobName").start(step()).build();
	}
	
}

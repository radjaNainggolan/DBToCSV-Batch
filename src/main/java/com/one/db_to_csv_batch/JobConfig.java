package com.one.db_to_csv_batch;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class JobConfig {

	
	@Autowired
	JobRepository jobRepository;
	
	@Autowired
	PlatformTransactionManager transactionManager;
	
	@Autowired
	DataSource dataSource;
	
	@Bean
	public FlatFileItemWriter<Employee> writer(){
		FlatFileItemWriter<Employee> writer = new FlatFileItemWriter<Employee>();
		writer.setResource(new FileSystemResource("data.csv"));
		writer.setLineAggregator(new EmployeeLineAggregator());
		return writer;
	}
	
	
	@Bean
	public Step step() {
		
		return new StepBuilder("step", jobRepository)
				.<Employee, Employee>chunk(2,transactionManager)
				.reader(new EmployeeDbReader(dataSource))
				.writer(writer())
				.build();
		
	}
	
	@Bean
	public Job job() {
		
		return new JobBuilder("job", jobRepository)
				.incrementer(new RunIdIncrementer())
				.start(step())
				.build();
	}
	
	
}

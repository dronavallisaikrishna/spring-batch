package com.springbatch.config;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class JobConfig {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	
	 /**
	  * Simple Flat file reader
	  * @return
	  */
	@Bean
	public FlatFileItemReader<Customer> itemReader(){
		FlatFileItemReader<Customer> fileItemReader= new FlatFileItemReader<>();
		fileItemReader.setLinesToSkip(1);
		fileItemReader.setResource(new ClassPathResource("/data/customer.csv"));
		DefaultLineMapper<Customer> defaultLineMapper= new DefaultLineMapper<>();
		DelimitedLineTokenizer tokenizer= new DelimitedLineTokenizer();
		tokenizer.setNames(new String[] {"id","firstName","lastName","birthdate"});
		
		defaultLineMapper.setLineTokenizer(tokenizer);
		defaultLineMapper.setFieldSetMapper(new CustomerFieldSerMapper());
		defaultLineMapper.afterPropertiesSet();
		fileItemReader.setLineMapper(defaultLineMapper);
		return fileItemReader;
	}
	@Bean
	public ItemWriter<Customer > itemWriter(){
		return new ItemWriter<Customer>() {
			
			@Override
			public void write(List<? extends Customer> customers) throws Exception {
				for(Customer c: customers) {
					System.out.println("Customer details are"+c.getFirstName()+"\n"+c.getId()+"\n"+c.getLastName()+"\n"+c.getBirthdate());
				}
				
				
			}
		};
	}
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("Step1")
				.<Customer,Customer>chunk(10)
				.reader(itemReader())
				.writer(itemWriter()).build();
	}
	
	@Bean
	public Job dbReaderJob() {
		return jobBuilderFactory.get("Flat file reader job").start(step1()).build();
	}
}

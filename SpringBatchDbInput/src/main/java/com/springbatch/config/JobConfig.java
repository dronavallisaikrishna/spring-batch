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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfig {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	public DataSource dataSource;
	
	 @Autowired
	  private EntityManagerFactory entityManagerFactory;
	
	 /**
	  * Simple JDBC item reader
	  * @return
	  */
 
//	@Bean
//	public JdbcCursorItemReader<Customer> cursorItemReader(){
//		JdbcCursorItemReader<Customer> reader= new JdbcCursorItemReader<>();
//		reader.setSql("select id,firstName,lastName,birthdate from customer order by lastName,firstName");
//		reader.setDataSource(dataSource);
//		reader.setRowMapper(new CustomerRowMaper());
//		return reader;
//	}
	/**
	 * 
	 * JPA Item reader
	 * @return
	 */
	@Bean
	public JpaPagingItemReader<Customer> jpaReader(){
		JpaPagingItemReader<Customer> itemReader= new JpaPagingItemReader<>();
		itemReader.setEntityManagerFactory(entityManagerFactory);
		JpaQueryProviderImpl<Customer> jpaQueryProvider = new JpaQueryProviderImpl<>();
		jpaQueryProvider.setQuery("Customer.findAll"); //Customer.findAll
		itemReader.setQueryProvider(jpaQueryProvider);
		itemReader.setPageSize(100);
		System.out.println("customer item is:-"+itemReader);
		return itemReader;
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
				.reader(jpaReader())
				.writer(itemWriter()).build();
	}
	
	@Bean
	public Job dbReaderJob() {
		return jobBuilderFactory.get("basedata job").start(step1()).build();
	}
}

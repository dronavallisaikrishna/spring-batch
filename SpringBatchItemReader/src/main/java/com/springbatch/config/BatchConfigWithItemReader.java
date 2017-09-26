package com.springbatch.config ;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchConfigWithItemReader {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	
//	@Bean
//	public ItemReader<String> reader(){
//		 
//	}
	
	@Bean
	public StatelessItemReader itemReader() {
		List<String> data=new ArrayList<>();
		data.add("one");
		data.add("two");
		data.add("three");
		return new StatelessItemReader(data);
	}
	
	@Bean
	public ItemWriter<String> itemWriter(){
		ItemWriter<String> itemWriter= new ItemWriter<String>() {
			
			@Override
			public void write(List<? extends String> data) throws Exception {
				for(String item :data) {
					System.out.println("the current item is:-"+item);
				}
				
			}
		};
		return itemWriter;
	}
	
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
				.<String,String>chunk(2)
				.reader(itemReader())
				.writer(itemWriter())
				.build();
	}
	
	@Bean
	public Job itemReaderJob() {
		return jobBuilderFactory.get("item reader job")
				.start(step1())
				.build();
	}
	
	
}

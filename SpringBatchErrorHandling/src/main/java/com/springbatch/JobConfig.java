package com.springbatch;

import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfig {

	
	@Autowired
	private JobBuilderFactory builderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	
	@Bean
	@StepScope
	public Tasklet restartTasklet() {
		
		return new Tasklet() {
			
			@Override
			public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
				Map<String, Object> stepExecutionContext=chunkContext.getStepContext().getStepExecutionContext();
				System.out.println("step execution context is:-"+stepExecutionContext);
				if(stepExecutionContext.containsKey("ran")) {
					System.out.println("The Job is succesfully ran");
					return RepeatStatus.FINISHED;
				}
				else {
					System.out.println("i dont think the application ran....");
					chunkContext.getStepContext().getStepExecution().getExecutionContext().put("ran", true);
					throw new RuntimeException("Not this time");
				}
			}
		};
	}
	
	
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("Step1")
				.tasklet(restartTasklet())
				.build();
	}
	
	@Bean
	public Step step2() {
		return stepBuilderFactory.get("Step2")
				.tasklet(restartTasklet())
				.build();
	}
	
	
	@Bean
	public Job job() {
		
	return builderFactory.get("job restart")
			.start(step1())
			.next(step2())
			.build();
	}
}

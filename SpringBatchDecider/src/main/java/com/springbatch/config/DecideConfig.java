package com.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DecideConfig {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step basicStep() {
		return stepBuilderFactory.get("basic step")
				.tasklet(new Tasklet() {
					@Override
					public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
						// TODO Auto-generated method stub
						System.out.println("This is step1 from my flow");
						return RepeatStatus.FINISHED;
					}
				}).build();
	}
	
	@Bean
	public Step evenStep() {
		return stepBuilderFactory.get("even step")
				.tasklet(new Tasklet() {
					@Override
					public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
						// TODO Auto-generated method stub
						System.out.println("This is even from my flow");
						return RepeatStatus.FINISHED;
					}
				}).build();
	}
	
	@Bean
	public Step oddStep() {
		return stepBuilderFactory.get("odd step")
				.tasklet(new Tasklet() {
					@Override
					public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
						// TODO Auto-generated method stub
						System.out.println("This is odd step from my flow");
						return RepeatStatus.FINISHED;
					}
				}).build();
	}
	
	/**
	 * this bean decides which has to be executed
	 * 
	 */
	@Bean
	public JobExecutionDecider decider() {
		return new Decider();
	}
	
	@Bean
	public Job myOwnFLow() {
		return jobBuilderFactory.get("spring batch along with decider to decide which step to execute").start(basicStep())
				.next(decider())
				.from(decider()).on("EVEN").to(evenStep())
				.from(decider()).on("ODD").to(oddStep())
				.from(oddStep()).on("*").to(decider()).end().build();
	}
}

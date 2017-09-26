package com.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AttachCustomFlowAsLastStep {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step customStep() {
		return stepBuilderFactory.get("custom step")
				.tasklet(new Tasklet() {
					@Override
					public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
						// TODO Auto-generated method stub
						System.out.println("This is custom step from the Flow");
						return RepeatStatus.FINISHED;
					}
				}).build();
	}
	
	@Bean
	public Job jobWithFLowAndCustomStepAtStart(Flow flow) {
		return jobBuilderFactory.get("job with flow and custom step in first")
				.start(customStep()).on("COMPLETED").to(flow).end().build();
	}
}

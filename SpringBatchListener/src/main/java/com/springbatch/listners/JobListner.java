package com.springbatch.listners;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class JobListner implements JobExecutionListener {

	@Override
	public void afterJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub
		String name=jobExecution.getJobInstance().getJobName();
		System.out.println("job name after processing the job is:-"+name);

	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub
		String name=jobExecution.getJobInstance().getJobName();
		System.out.println("job name before processing the job is:-"+name);

	}

}

package com.springbatch.config;

import javax.batch.operations.JobOperator;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobLauncherController {

	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private JobOperator jobOperator;
	
	@Autowired
	
	private Job job;
	
	@RequestMapping(value="/start",method=RequestMethod.POST)
	@ResponseStatus(org.springframework.http.HttpStatus.ACCEPTED)
	public void launch(@RequestParam("name") String name) throws Exception{
		JobParameters jobParameters= new JobParametersBuilder().addString("name", name).toJobParameters();
		this.jobLauncher.run(job, jobParameters);
		
	}
}

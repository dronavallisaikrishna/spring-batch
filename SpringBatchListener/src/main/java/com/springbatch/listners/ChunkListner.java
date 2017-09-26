package com.springbatch.listners;

import javax.batch.api.chunk.listener.ChunkListener;

import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ChunkListner {
	
	@Autowired
	private ChunkContext chunkContext;
	
	@BeforeChunk
	public void beforeChunk() {
		
		System.out.println("before chunking the data");
	}
	
	@AfterChunk
	public void afterChunk() {
		System.out.println("after chunking the data");
	}
	
	
}

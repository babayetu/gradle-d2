package com.battle.ch3;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import org.apache.log4j.Logger;

public class ForkJoinDemoTask extends RecursiveTask<Long>{
	private static Logger logger = Logger.getLogger(ForkJoinDemoTask.class);
	private static final int Threshold = 10000;
	private long start;
	private long end;
	
	

	public ForkJoinDemoTask(long start, long end) {
		this.start = start;
		this.end = end;
	}



	@Override
	protected Long compute() {
		long sum=0;
		boolean canComputer = (end - start) < Threshold;
		
		if (canComputer) {
			for (long i=start; i<=end; i++) {
				sum +=i;
			}
		} else {
			//分成100个小任务
			long step = (end - start) / 100;
			ArrayList<ForkJoinDemoTask> arrayList = new ArrayList<ForkJoinDemoTask>();
			
			long curPos = start;
			
			for (int i = 0; i < 100; i++) {
				long curEnd = curPos + step;
				if (curEnd >= end) {
					curEnd = end;
				}
				ForkJoinDemoTask subTask = new ForkJoinDemoTask(curPos,curEnd);
				arrayList.add(subTask);
				curPos = curEnd + 1;
				subTask.fork();
			}
			
			for (ForkJoinDemoTask t :arrayList) {
				sum += t.join();
			}
		}
		
		return sum;
	}
	
	public static void main(String[] args) {
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		ForkJoinDemoTask countTask = new ForkJoinDemoTask(0,50000L);
		ForkJoinTask<Long> result = forkJoinPool.submit(countTask);
		try {
			long sumResult = result.get();
			logger.info("The final result is: " + sumResult);
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

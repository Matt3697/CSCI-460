package priorityInversion;

import java.io.PrintWriter;

/*
* Author: Matthew Sagen
* Date:   11/27/18
* Programming Assignment 3: Priority Inversion
*/
public class Job {
	public int jobId, arrivalTime, priorityLevel, runTime;
	public PrintWriter writer;
	public boolean running;
	
	public Job(int jobId, int arrivalTime, int priorityLevel,  PrintWriter writer) {
		this.arrivalTime = arrivalTime;
		this.priorityLevel = priorityLevel;
		this.jobId = jobId;
		this.writer = writer;
		if(priorityLevel == 1 || priorityLevel == 3) {
			this.runTime = 3;
		}
		else {
			this.runTime = 10;
		}
	}
	
	public int[] changeArray(int[] sharedBuffer) {
		//convert the contents of the sharedBuffer to either 1's or 3's
		for(int i = 0; i < sharedBuffer.length; i++) {
			sharedBuffer[i] = priorityLevel;
		}
		
		return sharedBuffer;
	}
	
	
	public void doJob(int[] sharedBuffer, int time){
		if(priorityLevel == 1 || priorityLevel == 3) {
			sharedBuffer = changeArray(sharedBuffer);
			System.out.print(priorityLevel);
		}
		
		else {
			System.out.print("N");
		}
		
		
	}
	public void setRunning(boolean flag) {
		running = flag;
	}
	public boolean isRunning() {
		return running;
	}
	public int getJobId() {
		return jobId;
	}
	public int getPriorityLevel() {
		return priorityLevel;
	}
	public int getArrivalTime() {
		return arrivalTime;
	}
	public int getRunTime() {
		return runTime;
	}
	public void closeWriter() {
		writer.close();
	}

	public boolean canPreempt(Job prev) {
		if(priorityLevel > prev.getPriorityLevel() && (priorityLevel != 1 && prev.getPriorityLevel() != 3)) {//higher number = higher priority && 1 can't preempt 3
			return true;
		}
		else {
			return false;
		}
	}
}

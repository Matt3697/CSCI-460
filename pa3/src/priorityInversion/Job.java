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
			System.out.print("time " + time + ", T" + priorityLevel + ":");
			writer.print("time " + time + ", T" + priorityLevel + ":");
			for(int i = 0; i < sharedBuffer.length; i++) {
				System.out.print(sharedBuffer[i]);
				writer.print(sharedBuffer[i]);
			}
			System.out.print(":T" + priorityLevel + "\n");
			writer.print(":T" + priorityLevel + "\n");
		}
		
		else {
			System.out.print("time " + time + ", T" + priorityLevel);
			writer.print("time " + time + ", T" + priorityLevel);
			for(int i = 0; i < runTime; i++) {
				System.out.print("N");
				writer.print("N");
			}
			System.out.print("T" + priorityLevel + "\n");
			writer.print("T" + priorityLevel + "\n");
		}
		
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
	public void doEnd() {
		writer.println("Job " + jobId + " is ending.");
	}
}

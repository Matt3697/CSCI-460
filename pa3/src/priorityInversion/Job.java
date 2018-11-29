package priorityInversion;

import java.io.PrintWriter;

/*
* Author: Matthew Sagen
* Date:   11/27/18
* Programming Assignment 3: Priority Inversion
*/
public class Job {
	public int jobId, arrivalTime, priorityLevel, runTime, tj;
	public PrintWriter writer;
	public boolean running, complete, waiting;
	
	public Job(int jobId, int arrivalTime, int tj) {
		this.arrivalTime = arrivalTime;
		this.jobId = jobId;
		this.tj = tj;
		if(tj == 1) {
			this.priorityLevel = 3;
		}
		else if(tj == 2) {
			this.priorityLevel = 2;
		}
		else if(tj == 3) {
			this.priorityLevel = 1;
		}
		if(tj == 1 || tj == 3) {
			this.runTime = 3;
		}
		else if (tj == 2){
			this.runTime = 10;
		}
	}
	
	public int[] changeArray(int[] sharedBuffer) {
		//convert the contents of the sharedBuffer to either 1's or 3's
		for(int i = 0; i < sharedBuffer.length; i++) {
			sharedBuffer[i] = tj;
		}
		return sharedBuffer;
	}
	
	
	public void doJob(int[] sharedBuffer, int time, PrintWriter writer){
		if(priorityLevel == 1 || priorityLevel == 3) {
			sharedBuffer = changeArray(sharedBuffer);
			System.out.print(tj);
			writer.print(tj);
		}
		
		else {
			writer.print("N");
			System.out.print("N");
		}
	}
    public void finishJob(PrintWriter writer) {
    		if(tj == 1) {
    			System.out.print("T\u2081\n");
    			writer.print("T\u2081\n");
    		}
    		else if(tj == 2) {
    			System.out.print("T\u2082\n");
    			writer.print("T\u2082\n");
    		}
    		else if(tj == 3) {
    			System.out.print("T\u2083\n");
    			writer.print("T\u2083\n");
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
	public int getTj() {
		return tj;
	}
	public void setComplete() {
		complete = true;
	}
	public boolean isComplete() {
		return complete;
	}
	public void setWaiting() {
		waiting = true;
	}
	public boolean isWaiting() {
		return waiting;
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

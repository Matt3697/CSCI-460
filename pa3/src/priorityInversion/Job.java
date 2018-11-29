package priorityInversion;

import java.io.PrintWriter;

/*
* Author: Matthew Sagen
* Date:   11/27/18
* Programming Assignment 3: Priority Inversion
* This class is used by Sagen_Matthew.java to create Job objects.
*/
public class Job {
	public int arrivalTime, priorityLevel, runTime, tj;
	public PrintWriter writer;
	public boolean running, complete, waiting;
	
	public Job(int arrivalTime, int tj) {//initialize a Job with an Id, arrivalTime, and tj (representing priority)
		this.arrivalTime = arrivalTime;
		this.tj = tj;
		if(tj == 1) {//P(T1) = 3
			this.priorityLevel = 3;
		}
		else if(tj == 2) {//P(T2) = 2
			this.priorityLevel = 2;
		}
		else if(tj == 3) {//P(T3) = 3
			this.priorityLevel = 1;
		}
		if(tj == 1 || tj == 3) { //The runtime for T1 and T2 jobs is 3ms.
			this.runTime = 3;
		}
		else if (tj == 2){//The runtime for T2 jobs is at most 10 ms.
			this.runTime = 10;
		}
	}
	
	public int[] changeArray(int[] sharedBuffer) {
		//convert the contents of the sharedBuffer to either 1's or 3's. This is only used by T1 and T3 jobs.
		for(int i = 0; i < sharedBuffer.length; i++) {
			sharedBuffer[i] = tj;
		}
		return sharedBuffer;
	}
	
	
	public void doJob(int[] sharedBuffer, int time, PrintWriter writer){//this method defines specific work for a specific job.
		if(priorityLevel == 1 || priorityLevel == 3) {//if the job is  of priority 1 or 3, print its corresponding Tj
			sharedBuffer = changeArray(sharedBuffer); //change the shared buffer to the appropriate values.
			System.out.print(tj);
			writer.print(tj);
		}
		
		else {//if the job is a T2 job, print an N
			writer.print("N");
			System.out.print("N");
		}
	}
    public void finishJob(PrintWriter writer) {//Helper method to finish the output of a job once it is completed or preempted.
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
	public void setRunning(boolean flag) {//set's the job to true or false
		running = flag;
	}
	public boolean isRunning() {//returns whether or not the job is currently running.
		return running;
	}
	
	public int getPriorityLevel() {//returns priority level of the job
		return priorityLevel;
	}
	public int getArrivalTime() { //returns arrivalTime for a job
		return arrivalTime;
	}
	public int getRunTime() { //returns runtime of a job.
		return runTime;
	}
	public int getTj() {//returns the Tj value of a job.
		return tj;
	}
	public void setComplete() {//set a job to complete
		complete = true;
	}
	public boolean isComplete() {//return whether or not a job is complete.
		return complete;
	}
	public void setWaiting() {//set a job to waiting.
		waiting = true;
	}
	public boolean isWaiting() {//return whether or not a job is currently waiting.
		return waiting;
	}
	public boolean canPreempt(Job prev) {//returns whether or not prev can be preempted.
		//higher number = higher priority && 1 can't preempt 3
		//if this jobs priority level is greater than the job in question, and this job is not of priority 1 and the job in quesiton not priority 3.
		//then the job in questions can be preempted. Otherwise not.
		if(priorityLevel > prev.getPriorityLevel() && (priorityLevel != 1 && prev.getPriorityLevel() != 3)) {
			return true;
		}
		else {
			return false;
		}
	}
}

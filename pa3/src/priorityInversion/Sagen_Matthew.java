package priorityInversion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;


/*
 * Author: Matthew Sagen
 * Date:   11/27/18
 * Programming Assignment 3: Priority Inversion
 * Program takes no arguments from standard input. Data is read into memory from src/priorityInversion/input.txt
 * To run this program, simply run this file as a java application.
 * The output will be located in src/priorityInversion/Sagen_Matthew.txt
 * To change how long the program runs, change the variable, upperTimeBound, to desired time in ms.
 */
public class Sagen_Matthew {
	

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException{
		
		ArrayList<Job> jobs = get_inputFileJobArray();
		ArrayList<Job> jobQueue = new ArrayList<Job>();
		ArrayList<Job> waitQueue = new ArrayList<Job>();
		int[] sharedBuffer = {0,0,0}; // T1 shares a small buffer of length 3 with T3, with an initial value of < 0, 0, 0 > at time 0.
		int time = 0; 				 // variable to simulate system time
		int upperTimeBound = 50;
		for(Job job: jobs) {//add each job into a queue
			jobQueue.add(job); 
		}
		
		/* Arrival time | priority level
		 * 1 3 --> run from time 1 to 4 and print T3:333:T3
		 * 3 2 --> run from time 3 to time 13 and print T2:NNNNNNNNNN:T2
		 * 6 3 --> run from time 6 to time 9 and print T3:333:T3
		 * 8 1 --> run from time 8 to time 11 and print T1:111:T1
		 * 10 2 --> run from time 10 to time 20 print T2:NNNNNNNNNN:T2
		 * |--|--|--|--|--|--|--|--|--|--|--|--|--|--|--|--|--|--|--|--|--|--|--|--|--|
		 * 0  1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25
		 *    j1-------|                             <--FCFS
		 *          j2----------------------------|  <--should preempt j1, j1 has priority 3, j2 has priority 2
		 *                   j3-------|              <--should wait for j2 to finish
		 *                         j4-------|        <--should not preempt j3, even though it has higher priority because of shared buffer
		 *                               j5----------------------------|
		 */
		
		System.out.println("Starting jobs...");
		Job current = jobQueue.get(0);
		int jobTimeCounter = 0;
		
        for(int i = 0; i < upperTimeBound; i++) {//this loop simulates system time, from 0 to 50ms or upperTimeBound.
        		//if the current time=the arrival time of a job, try to perform that job.
        		
        		for(Job job : jobQueue) {
        			if(i > jobQueue.get(0).getArrivalTime() && job.getArrivalTime() == i && job.canPreempt(current)) {
        				current.setRunning(false);
        				current.finishJob();
			    	    current = job;  		
        			}
        			else if(i == job.getArrivalTime() && !job.canPreempt(current)) {//if the job can't preempt, put it in a queue to run later.
        				waitQueue.add(job);
        				job.setWaiting();
        				//System.out.println(job.getArrivalTime() + " " + current.isWaiting() + !current.isRunning() + !current.isComplete());
        			}
        		}
        		if(waitQueue.size() > 0 && !current.isRunning()) {
        			current = waitQueue.get(0);
        			waitQueue.remove(0);
        		}
        		if((i == current.getArrivalTime() || current.isWaiting()) && !current.isRunning() && !current.isComplete()) {//if the job is not running, print the current time, and the job description
					if(current.getTj() == 1) {
						System.out.print("time " + time + ", T\u2081"); //\u2081 is to subscript 3
					}
					else if(current.getTj() == 2) {
						System.out.print("time " + time + ", T\u2082");
					}
					else if(current.getTj() == 3) {
						System.out.print("time " + time + ", T\u2083");
					}
					current.setRunning(true);
				}
        		if(current.isRunning()) {       
            			if(current.getTj() == 1) { 
            				if(jobTimeCounter < jobQueue.get(0).getRunTime() - 1) {//if it is T1
            					current.doJob(sharedBuffer, time);
            				}
            				else {
            					current.doJob(sharedBuffer, time);
            					System.out.print("T\u2081\n");
            					current.setRunning(false);
            					current.setComplete();
            					jobTimeCounter = -1;	
            				}
            				jobTimeCounter++;
            			}
            			else if(current.getTj() == 2) {//if it is T2
            				
            				if(jobTimeCounter < current.getRunTime() - 1) {
            					current.doJob(sharedBuffer, time);
            				}
            				else {
            					current.doJob(sharedBuffer, time);
            					System.out.print("T\u2082\n");
            					current.setRunning(false);
            					current.setComplete();
            					jobTimeCounter = -1;
            				}
            				jobTimeCounter++;
            			}
            			else if(current.getTj() == 3){//T3
            				if(jobTimeCounter < current.getRunTime() - 1) {
            					current.doJob(sharedBuffer, time);        					
            				}
            				else {
            					current.doJob(sharedBuffer, time);
            					System.out.print("T\u2083\n");
            					current.setRunning(false);
            					current.setComplete();
            					jobTimeCounter = -1;
            				}
            				jobTimeCounter++;
            			}			
                }
    			time++;
        }
        
        for (int i = 0; i < jobs.size(); i++) {//close the writer. probably change this
			if(i == jobs.size() - 1) {
				jobs.get(i).closeWriter();
			}	
        } 
	}
	
	public static ArrayList<Job> get_inputFileJobArray() throws FileNotFoundException, UnsupportedEncodingException{ //helper method creates jobs from input.txt
		Scanner scanner = null;
		ArrayList<Integer> input = new ArrayList<Integer>();	//holds data from input.txt in the form of Job# | Arrival_Time | Processing_Time
		ArrayList<Job> jobs = new ArrayList<Job>();			//holds individual jobs from input.
		PrintWriter writer = new PrintWriter("src/priorityInversion/Sagen_Matthew.txt", "UTF-8");
		//Opens file location
        try{
            scanner = new Scanner(new File("src/priorityInversion/input.txt"));
        }
        //throws error if the file can't be located.
        catch(Exception e){
            System.out.println("File not found");
            System.exit(0);
        }
        //add data to input list.
        while(scanner.hasNextInt()){
            input.add(scanner.nextInt()); 
        }
        
        int x = 0;
        int i = 0;
        while(x < input.size()) {	//create new jobs using input and add them to jobs list.
        		Job newJob = new Job(i, input.get(x), input.get(x + 1), writer);
        		jobs.add(newJob);
        		x += 2;
        		i++;
        }
        return jobs;
	}
}

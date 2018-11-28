package priorityInversion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * Author: Matthew Sagen
 * Date:   11/27/18
 * Programming Assignment 3: Priority Inversion
 * Program takes no arguments from standard input. Data is read into memory from src/priorityInversion/input.txt
 * To run this program, simply run this file as a java application.
 * The output will be located in src/priorityInversion/Sagen_Matthew.txt
 */
public class Sagen_Matthew {
	

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		
		ArrayList<Job> jobs = get_inputFileJobArray();
		ArrayList<Job> jobQueue = new ArrayList<Job>();
		int[] sharedBuffer = {0,0,0}; // T1 shares a small buffer of length 3 with T3, with an initial value of < 0, 0, 0 > at time 0.
		int time = 0; 				 // variable to simulate system time
		
		for(Job job: jobs) {//start all of the job threads, and add each job into a queue
	    		try {
	    			jobQueue.add(job);
	        }
	        catch(Exception e) { 
	            System.out.println ("Exception is caught"); 
	        } 
		}
		System.out.println("Starting jobs...");
        for(int i = 0; i < 50; i++) {//this loop simulates system time, from 0 to 50ms.
        		if(jobQueue.size() > 0 && i == jobQueue.get(0).getArrivalTime()) {//if the current time = the arrival time of a job, perform that job.
        		         //get the job that is supposed to run at this time slice, and make it do its job.
            			/*
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
            			if(jobQueue.get(0).getPriorityLevel() == 1) {//level 1 has highest priority 
            				
            			}
            			else if(jobQueue.get(0).getPriorityLevel() == 2) {
            				
            			}
            			else {//priority level is 3
            				
            			}
            			jobQueue.get(0).doJob(sharedBuffer, time);
            			time += jobQueue.get(0).getRunTime();
            			jobQueue.remove(0);//remove the first job, and shift the remaining jobs to the left by 1.
                }
        		else {
        			time++;
        		}
        }
        
        for (int i = 0; i < jobs.size(); i++) {//loop through the list of jobs and end their threads.
        		System.out.println("Job " + jobs.get(i).getJobId() + " is ending.");
        		jobs.get(i).doEnd();
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

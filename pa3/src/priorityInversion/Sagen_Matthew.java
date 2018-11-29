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
 * The output will be located in src/priorityInversion/Sagen_Matthew.txt, program also outputs to standard out.
 * To change how long the program runs, change the variable, upperTimeBound, to desired time in ms.
 */
public class Sagen_Matthew {
	

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException{
		
		ArrayList<Job> jobs = get_inputFileJobArray();
		ArrayList<Job> jobQueue = new ArrayList<Job>();
		ArrayList<Job> waitQueue = new ArrayList<Job>();
		int[] sharedBuffer = {0,0,0}; // T1 shares a small buffer of length 3 with T3, with an initial value of < 0, 0, 0 > at time 0.
		int time = 0; 				 // variable to simulate system time
		int upperTimeBound = 50;		 // arbitrary upperTimeBound, ideally at least 10ms higher than highest arrival time for jobs.
		for(Job job: jobs) {			 // add each job into a queue
			jobQueue.add(job); 
		}
		PrintWriter writer = new PrintWriter("src/priorityInversion/Sagen_Matthew.txt", "UTF-8"); //this is used to print to output file
		
		System.out.println("Starting jobs...");
		writer.println("Starting jobs...");
		Job current = jobQueue.get(0);
		int jobTimeCounter = 0;
		
        for(int i = 0; i < upperTimeBound; i++) {//this loop simulates system time, from 0 to 50ms or upperTimeBound.
        		//loop through list of jobs. if the current time equals the arrival time of a job, try to perform that job.
        		for(Job job : jobQueue) {
        			if(i > jobQueue.get(0).getArrivalTime() && job.getArrivalTime() == i && job.canPreempt(current)) {//if the job can preempt the current job
        				current.setRunning(false);
        				if(!current.isComplete()) {//cut off the current job
        					current.finishJob(writer);
        				}
			    	    current = job;  //set the new current job.
        			}
        			else if(i == job.getArrivalTime() && !job.canPreempt(current)) {//if the job can't preempt, put it in a queue to run later as FCFS.
        				waitQueue.add(job);
        				job.setWaiting();
        			}
        		}
        		if(waitQueue.size() > 0 && !current.isRunning()) {
        			//if there are no jobs running, but there is a job in the waitQueue, run the first job in the queue.
        			current = waitQueue.get(0);
        			waitQueue.remove(0);
        		}
        		if((i == current.getArrivalTime() || current.isWaiting()) && !current.isRunning() && !current.isComplete()) {
        			//if the job is not running, print the current time, and the job description, then set the job to running is true.	
        			if(current.getTj() == 1) {//if the job is T1
						System.out.print("time " + time + ", T\u2081"); //\u2081 is to subscript 3
						writer.print("time " + time + ", T\u2081");
					}
					else if(current.getTj() == 2) {//if the job is a T2 job
						System.out.print("time " + time + ", T\u2082");
						writer.print("time " + time + ", T\u2082");
					}
					else if(current.getTj() == 3) {//if the job is a T3 job
						System.out.print("time " + time + ", T\u2083");
						writer.print("time " + time + ", T\u2083");
					}
					current.setRunning(true);
				}
        		if(current.isRunning()) { 						  //if the current job is running, we can have it do its specific job
    				if(jobTimeCounter < current.getRunTime() - 1) {//if it is running within its alloted run time, do its job.
    					current.doJob(sharedBuffer, time, writer);
    					jobTimeCounter++;
    				}
    				else {			//if it is outside of it's run time, end the job. Here we also print the last iteration for the job.
    					current.doJob(sharedBuffer, time, writer);
    					current.finishJob(writer);
    					current.setRunning(false);
    					current.setComplete();
    					jobTimeCounter = 0;	//and reset jobTimeCounter to 0
    				}
        		}
    			time++;
        }
        System.out.println("Finished.");
        writer.println("Finished.");
        writer.close();
	}
	
	public static ArrayList<Job> get_inputFileJobArray() throws FileNotFoundException, UnsupportedEncodingException{ //helper method creates jobs from input.txt
		Scanner scanner = null;
		ArrayList<Integer> input = new ArrayList<Integer>();	//holds data from input.txt in the form of Job# | Arrival_Time | Processing_Time
		ArrayList<Job> jobs = new ArrayList<Job>();			//holds individual jobs from input.
		
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
        		Job newJob = new Job(i, input.get(x), input.get(x + 1));
        		jobs.add(newJob);
        		x += 2;
        		i++;
        }
        return jobs;
	}
}

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
 */
public class Sagen_Matthew {
	

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		Lock queueLock = new ReentrantLock();
		ArrayList<Job> jobs = get_inputFileJobArray();
		ArrayList<Job> jobQueue = new ArrayList<Job>();
		int[] sharedBuffer = {0,0,0}; // T1 shares a small buffer of length 3 with T3, with an initial value of < 0, 0, 0 > at time 0.
		int time = 0; 				 // variable to simulate system time
		
		for(Job job: jobs) {
	    		try {
	    			job.start();
	    			jobQueue.add(job);
	    			Thread.sleep(10);
	        }
	        catch(Exception e) { 
	            System.out.println ("Exception is caught"); 
	        } 
		}
		
        for(int i = time; i < 50; i++) {
        		if(jobQueue.size() > 0 && i == jobQueue.get(0).getArrivalTime()) {
        			queueLock.lock();
            		try {
            			jobQueue.get(0).doJob(sharedBuffer, time);
                }
                catch(Exception e) { 
                    System.out.println ("Exception is caught"); 
                } 
            		queueLock.unlock();
        			jobQueue.remove(0);
        		}
        		time++;
        }
       
       
        for (int i = 0; i < jobs.size(); i++) {
            try {
            		System.out.println("Job " + jobs.get(i).getJobId() + " is ending.");
				jobs.get(i).join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } 
		PrintWriter writer = new PrintWriter("Sagen_Matthew.txt", "UTF-8");
		
		writer.close();
	}
	
	public static ArrayList<Job> get_inputFileJobArray(){ //helper method creates jobs from input.txt
		Scanner scanner = null;
		ArrayList<Integer> input = new ArrayList<Integer>();	//holds data from input.txt in the form of Job# | Arrival_Time | Processing_Time
		ArrayList<Job> jobs = new ArrayList<Job>();			//holds individual jobs from input.
		
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
        		Job newJob = new Job(i, input.get(x), input.get(x + 1));
        		jobs.add(newJob);
        		x += 2;
        		i++;
        }
        return jobs;
	}
}

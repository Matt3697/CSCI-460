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
 */
public class Sagen_Matthew {
	
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		ArrayList<Job> jobs = get_inputFileJobArray();
		int[] sharedBuffer = {0,0,0}; //. T1 shares a small buffer of length 3 with T3, with an initial value of < 0, 0, 0 > at time 0.
 
        for (int i=0; i<jobs.size(); i++) { 
            jobs.get(i).start(); 
        } 
		
        for (int i=0; i<jobs.size(); i++) { 
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

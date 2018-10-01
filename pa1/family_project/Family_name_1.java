package family_project;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Family_name_1 {	
	static ArrayList<Integer> tats = new ArrayList<Integer>();
	public static void main(String[] args) throws IOException {
	    
		Scanner scanner = null;
		ArrayList<Integer> input = new ArrayList<Integer>();	//holds data from input.txt in the form of Job# | Arrival_Time | Processing_Time
		ArrayList<Job> jobs = new ArrayList<Job>();			//holds individual jobs from input.
		
		//Opens file location
        try{
            scanner = new Scanner(new File("family_project/input.txt"));
        }
        //throws error if the file can't be located.
        catch(Exception e){
            System.out.println("File not found.");
        }
        //add data to input list.
        while(scanner.hasNextInt()){
            input.add(scanner.nextInt()); 
        }
        
        int x = 0;
        while(x < input.size()) {	//create new jobs using input and add them to jobs list.
        		Job newJob = new Job(input.get(x), input.get(x + 1), input.get(x + 2));
        		jobs.add(newJob);
        		x += 3;
        }
		//Write a circular procedure to run the job sequence in an on-line fashion. Once a job "i" arrives you need to,
		//according to the order it arrives, put it on the processor (j+1)%k and run it immediately. Here j is the processor where job 
		// i- 1 was run, and initially j= 0, i.e the first job is run on processor 0. You can assume that it takes 1ms to put each job
		//at any processor to run. When the job sequence is finished, measure the overall turn-around time. I.e the time duration 
		//between the arrival of the first job and the finish of the last job.
		//Test your program on randomly generated job sequences of 100 which arrive every 1ms.
		
        //circularProcedure(jobs); 		//process jobs from input.txt
        for(int i = 0; i < 100; i++) {
			circularProcedure(randomJobs()); //process 100 randomly generated jobs
		}
		int min = get_min();
		int max = get_max();
		double average = get_average();
		double standard_deviation = get_standard_deviation(average);
		
        printResults(min, max, average, standard_deviation);
		
	}
	public static ArrayList<Job> randomJobs() {	//helper method to create 100 random jobs.
		ArrayList<Job> randomJobs_array = new ArrayList<Job>();
		for(int i = 1; i <= 100; i++) {
			int random = (int) (Math.random() * 499) + 1; //random number between 1 and 500
			Job newJob = new Job(i, i, random);
    			randomJobs_array.add(newJob);
		}
		return randomJobs_array;
	}
	
	public static void circularProcedure(ArrayList<Job> jobs) {
		ArrayList<Processor> processors = new ArrayList<Processor>(); //holds individual processors.
		int std_no = 8788;			//last four of student id.
		int k = (std_no%3) + 2;		//number of processors to emulate.
		
		for(int i = 0; i < k; i++) {	//create k processors.
			Processor processor = new Processor(i);
			processors.add(processor);
		}
		
		int system_time = 0;	//Variable to keep track of system time in milliseconds.
		 	int i = 0;   //used to add jobs based on arrival time
			int j = 0;   //used to add jobs to processor "j"
			int y = 0;   //used to get jobs
			int processor_num = 0;
			int tat = 0; //turn around time
			
			while(y < jobs.size()){
				//System.out.println("System Time at iteration " + i + ":" + system_time);
				if(jobs.get(y).get_arrival_time() == i) { //at a jobs arrival time	
					//System.out.println("Job# " + jobs.get(y).getJobNum() + " with processing time: " +jobs.get(y).get_processing_time());
					if(j == 0) {									   //put the first job on processor 0
						processors.get(j).onLoad_job(jobs.get(y));
					}
					else {										   //otherwise put jobs on processor (j+1)%k
						processor_num = (j + 1) % k;
						processors.get(processor_num).onLoad_job(jobs.get(y));
					}
					//System.out.println("added job " + jobs.get(y).getJobNum() + " to processor " + processors.get(processor_num).get_id());
					system_time += jobs.get(y).get_processing_time(); //add processing time to total system time.
					system_time++; //assume that it takes 1ms to put each job at any processor. i.e add 1ms
					j++;
					y++;
				}
				i++;
			}
			tat = system_time - jobs.get(0).get_arrival_time();
			tats.add(tat);
			System.out.println("Total System Time = " + system_time + "ms");
			System.out.println("Overall turnaround time: " + tat +"ms");
	}
	
	public static int get_min() { //compute the smallest value in the tats array.
		int min = tats.get(0);
		for(int i = 1; i < tats.size() - 1; i++) {
			if(tats.get(i) < min) {
				min = tats.get(i);
			}
		}
		return min;
	}
	public static int get_max() { //compute the largest value in the tats array
		int max = tats.get(0);
		for(int i = 1; i < tats.size() - 1; i++) {
			if(tats.get(i) > max) {
				max = tats.get(i);
			}
		}
		return max;
	}
	public static double get_average() { //compute the average of all values in the tats array
		double average = 0;
		for(int i = 0; i < tats.size(); i++) {
			average += tats.get(i);
		}
		average = average / tats.size();
		return average;
		
	}
	public static double get_standard_deviation(double average) { //compute the standard deviation of the values in the tats array.
		double standard_deviation = 0;
		double newAverage = 0;
		double[] temp = new double[tats.size()];
		for(int i = 0; i < tats.size(); i++) {
			temp[i] = (tats.get(i) - average)*(tats.get(i) - average);
		}
		for(int i = 0; i < temp.length; i++) {
			newAverage += temp[i];
		}
		newAverage = newAverage / temp.length;
		standard_deviation = Math.sqrt(newAverage);
		return standard_deviation;
	}
	public static void printResults(int min, int max, double average, double standard_deviation) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("Family_name1.txt", "UTF-8");
		writer.println("Minimum Turn-around Time: " + min);
		writer.println("Max Turn-around Time: " + max);
		writer.println("Average Turn-around Time: "+ average);
		writer.println("Standard Deviation of Turn-around Time: " + standard_deviation);
		writer.close();
	}
}
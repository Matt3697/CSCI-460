package family_project;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Family_name_1 {
	
	public static void main(String[] args) throws IOException {
	    
		Scanner scanner = null;
		ArrayList<Integer> input = new ArrayList<Integer>();	//holds data from input.txt
		int std_no = 8788;									//last four of student id.
		int k = (std_no%3) + 2;								//number of processors to emulate.
		for(int i = 0; i < k; i++) {							//create the k processors.
			Processor processor = new Processor(i);		}
		int j = 0;
		
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
        /*int x = 0;
        while(x < input.size()) {
        		System.out.println(input.get(x));
        		x++;
        }*/
		//Write a circular procedure to run the job sequence in an on-line fashion. Once a job "i" arrives you need to,
		//according to the order it arrives, put it on the processor (j+1)%k and run it immediately. Here j is the processor where job 
		// i- 1 was run, and initially j= 0, i.e the first job is run on processor 0. You can assume that it takes 1ms to put each job
		//at any processor to run. When the job sequence is finished, measure the overall turn-around time. I.e the time duration 
		//between the arrival of the first job and the finish of the last job.
		//Test your program on randomly generated job sequences of 100 which arrive every 1ms.
		
		
		PrintWriter writer = new PrintWriter("Family_name1.txt", "UTF-8");
		writer.println("The first line");
		writer.println("The second li");
		writer.close();
		
	}
}

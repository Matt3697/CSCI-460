package priorityInversion;
/*
* Author: Matthew Sagen
* Date:   11/27/18
* Programming Assignment 3: Priority Inversion
*/
public class Job extends Thread {
	int jobId, arrivalTime, j;
	
	public Job(int jobId, int arrivalTime, int j) {
		this.arrivalTime = arrivalTime;
		this.j = j;
		this.jobId = jobId;
	}
	
	public int[] changeArray(int[] sharedBuffer) {
		//convert the contents of the sharedBuffer to either 1's or 3's
		for(int i = 0; i < sharedBuffer.length; i++) {
			sharedBuffer[i] = j;
		}
		
		return sharedBuffer;
	}
	
	
	public void run() { 
        try{ 
            // Displaying the thread that is running 
            System.out.println ("Job " + jobId + " is running"); 
        } 
        catch (Exception e) { 
            System.out.println ("Exception is caught"); 
        } 
    } 
	
	public int getJobId() {
		return jobId;
	}
}

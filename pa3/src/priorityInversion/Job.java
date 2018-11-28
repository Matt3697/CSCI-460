package priorityInversion;

/*
* Author: Matthew Sagen
* Date:   11/27/18
* Programming Assignment 3: Priority Inversion
*/
public class Job extends Thread {
	int jobId, arrivalTime, priorityLevel, runTime;
	
	public Job(int jobId, int arrivalTime, int priorityLevel) {
		this.arrivalTime = arrivalTime;
		this.priorityLevel = priorityLevel;
		this.jobId = jobId;
		if(priorityLevel == 1 || priorityLevel == 3) {
			this.runTime = 3;
		}
		else {
			this.runTime = 10;
		}
	}
	
	public int[] changeArray(int[] sharedBuffer) {
		//convert the contents of the sharedBuffer to either 1's or 3's
		for(int i = 0; i < sharedBuffer.length; i++) {
			sharedBuffer[i] = priorityLevel;
		}
		
		return sharedBuffer;
	}
	
	
	public void run() { 
        try{ 
            // Displaying the thread that is running 
            System.out.println ("Job " + jobId + " is waiting."); 
            
        } 
        catch (Exception e) { 
            System.out.println ("Exception is caught"); 
        } 
    } 
	
	public void doJob(int[] sharedBuffer, int time) throws InterruptedException {
		if(priorityLevel == 1 || priorityLevel == 3) {
			try {
				sharedBuffer = changeArray(sharedBuffer);
				System.out.print("time " + time + ", T" + priorityLevel + ":");
				for(int i = 0; i < sharedBuffer.length; i++) {
					System.out.print(sharedBuffer[i]);
				}
				System.out.print(":T" + priorityLevel + "\n");
			} 
			finally{
				Thread.sleep(runTime * 100);
		     }
		}
		else {
			System.out.print("time " + time + ", T" + priorityLevel);
			for(int i = 0; i < runTime; i++) {
				System.out.print("N");
				Thread.sleep(runTime * 100);
			}
			System.out.print("T" + priorityLevel + "\n");
		}
		
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
}

package family_project;

import java.util.ArrayList;

public class Processor {
	public int processor_id;
	ArrayList<String> jobs = new ArrayList<String>();
	
	public Processor(int k) {			//constructor for class, Processor.
		processor_id = k;
	}
	public int get_id() {				//returns the unique id for the processor.
		return processor_id;
	}
	public void add_job(String job) {	//adds a job to the list of jobs on the processor.
		jobs.add(job);
	}
	
	public void onLoad_job(Job job) {
		
	}
}

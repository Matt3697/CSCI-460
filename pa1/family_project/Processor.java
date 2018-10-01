package family_project;

import java.util.ArrayList;

public class Processor {
	public int processor_id;
	ArrayList<Job> jobs = new ArrayList<Job>();
	
	public Processor(int k) {			//constructor for class, Processor.
		processor_id = k;
	}
	public int get_id() {				//returns the unique id for the processor.
		return processor_id;
	}
	
	public void onLoad_job(Job job) {    //adds job to list of jobs.
		jobs.add(job);
	}
}

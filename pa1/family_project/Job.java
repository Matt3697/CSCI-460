package family_project;

public class Job {
	public int job_num;
	public int arrival_time;
	public int processing_time;
	
	public Job(int job_num, int arrival_time, int processing_time) {
		this.job_num = job_num;
		this.arrival_time = arrival_time;
		this.processing_time = processing_time;
	}
	
	public int getJobNum() {
		return job_num;
	}
	public int get_arrival_time() {
		return arrival_time;
	}
	public int get_processing_time() {
		return processing_time;
	}
}

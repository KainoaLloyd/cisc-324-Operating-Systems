package lab6;

public class UserJob extends Thread{

		int job;
		int bound;
		int CPUtime;
		int IOtime;
		private DiskDrive DD;
		private CPUmonitor monitor;
		static int numtracks = 1024;
	
	public UserJob(int i, int b, CPUmonitor CPUmon, DiskDrive dd){
		job = i;
		bound = b; // 0 is I/O bound/ 1 is CPU bound
		DD = dd;
		monitor = CPUmon;
	}
	
	public void run(){

		int CPUtime = 100;

		
		for (int i = 0; i<10; i++){
					
			//request a  CPU
			int C;
			C = monitor.startCPUuse(job);

			System.out.println("User Job "+ job +" is starting to use CPU " + C);
			
			///determines sleep time based on if it is CPUbouind or I/O bound
			///
			if (bound == 1){ // if CPU bound
				CPUtime = (int)(Math.random()* 1000+1);
				
			}else{ //if IO bound
				CPUtime = (int)(Math.random()*200+1);
	
			}
			System.out.println("User Job "+ job +" is starting CPU burst of length " +CPUtime);
			try {
				sleep( CPUtime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			//release the allocated CPU
			monitor.endCPUuse(C);
			System.out.println("User Job "+ job +" is finished using CPU " + C);
				
			// a disk has  1024 tracks so it will request a track number within this range
			int track = (int)(Math.random()*numtracks+1);
			System.out.println("User Job "+ job +" requesting to read disk track " + track);
			
			DiskDrive.useTheDisk(track);
			System.out.println("User Job "+ job +" is finished reading disk track " + track);
			
	
			

		}
		
	}

}
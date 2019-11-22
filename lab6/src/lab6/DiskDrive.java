package lab6;


//This handles a simulation of Disk Drive Scheduling
//User Jobs must process one at a time when several of them try to perform disk I/O because there is only one diskdrive

public class DiskDrive {

	static int track;
	public DiskDrive(){
		//track starts at 0
		track = 0;
	}
	
	public synchronized static void useTheDisk(int t){

		System.out.println("Current disk head is at track " + track);
	
		
		int time = Math.abs (track-t)+1;
		System.out.println("Disk head will move " + (time-1) + " in "+ time + " Iseconds");
		track = t;
		//simulate delay for a read operation
		try {
			Thread.sleep(time);
		} catch (Exception e) {}
		
		
	}
}

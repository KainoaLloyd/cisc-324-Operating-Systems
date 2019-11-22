package lab6;

/* this program simulates a computer system using monitors to synchronize the activities of UserJob threads
 * THe simulated Computer system will use 20 UserJobs 3 CPUs and 1 disk drive
 * The 3 main components of the simulation will be UserJobs(UserJobs.java, CPUscheduling(CPUmonitor.java) and Disk Scheduling(DiskDrive.java
 * 
 * UserJobs:
 * 		Request a CPU
 * 		Use CPU for a while ( CPU bound job or I/O bound job)
 * 		Release the allocated CPU
 * 		Perform Disk I/O, the disk drive has 1024 tracks
 * CPUmonitor:
 * 		
 * 		startCPUuse:
 * 			Attempt tp start a CPU burst by:
 * 			check if there are CPUs available (counter)
 * 			if so assign user job to one of the ones available
 * 			Notify waiting jobs thatUserJob hAS FINISHED IT'S cpu BURST
 * 
 * 		endCPUuse:
 * 			release allocated CPU
 * 			notify jobs of release
 * DiskDrive:
 * 		Simulate th efact that disk access takes variable time, depending on how much the disk head has to move:
 * 		time taekn = 1ms per track  that the disj head has to move + 1ms to access the data
 *
 */



public class MainMethod {
public static void main (String argv[]) {
	System.out.println("The simulation of the computer system is starting");
	CPUmonitor monitor = new CPUmonitor();
	DiskDrive disk = new DiskDrive();
	//section 0.2
	UserJob U;
	//creates 20 User jobs half being CPU bound half being I/Onbound
    for (int i=0; i<20; i++) {
        U = new UserJob(i, i%2, monitor,  disk);
   
        U.start();
      }
    



System.out.println("This is main speaking");
}  // end of "main"
}  // end of "MainMethod"
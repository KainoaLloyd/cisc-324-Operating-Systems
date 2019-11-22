package lab6;

/*This class handles the CPU's and their scheduling
 * 
 * 
 */
import java.util.concurrent.Semaphore;

public class CPUmonitor {


	static boolean[] CPUs = new boolean[0];
	static int numCPUs = 3;		//Total number of CPUs
	static int available = 3;  // number of available CPUs
	
	//When CPUmonitor is created it calls init
	public CPUmonitor() {
		init(numCPUs);		
	}
	
	// If no CPUs exist, initialize static array of free(ie =0) CPUs to 0
	private void init(int numCPUs) {
		if (CPUs.length == 0) {
			CPUs = new boolean[numCPUs];
		}
	}
	
	

	/*This method starts the Use of a CPU
	 * 
	 * 
	 */
	public synchronized  int startCPUuse(int processID){
		
		//The test is to make sure CPUs are available if available == 0 no CPUs are available
		System.out.println(available);
		while(available == 0)try { wait(); } catch(Exception e) {};
		available--;
	
		//Optimization
		int len = CPUs.length;
		
		//Go through the Array of CPUs if any if  CPU[i] == false
		//that means a CPU is available for use so it is set == true to indicate it is in use
		//notifies jobs that it is done selecting a CPU so that other UserJobs and select a CPU to use
		//then returns the CPU number
		for (int i = 0; i<len; i ++){
			if (CPUs[i]== false){
				CPUs[i] = true;
				notifyAll();
				return i;
			}
		}
		return -1;
		
	}
	
	//Ends the use of a CPU 
	//C is the CPU being ended
	public synchronized  void endCPUuse(int C){
		//CPUs[C] is not in use anymore
		if (C >= 0) {
			CPUs[C] = false;
		} else {
			// this covers the return -1 
			CPUs[0] = false;
		}
		//there is one more available CPU
		available++;
		System.out.println("done" +available);
		//notify others that a UserJob has stopped use of a CPU
		try { notifyAll(); } catch(Exception e) {};
	}
}

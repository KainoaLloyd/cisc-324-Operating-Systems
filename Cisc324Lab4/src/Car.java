// This file defines class "Car".

// This code uses
//      class Synch, which defines the semaphores and variables
//                   needed for synchronizing the cars.

public class Car extends Thread {
  int myName;  // The variable myName stores the name of this thread.
               // It is initialized in the constructor.



  // This is the constructor for class Car.  It has an integer parameter,
  // which is the name that is given to this thread.
  public Car(int name) {
    // copy the parameter value to local variable "MyName"
    myName = name;

    // Call threadStart to let the timeSim scheduler know that another
    // thread is starting.  timeSim needs to know how many threads there
    // are, so that it can accurately judge when all threads have finished
    // their current computation, so that simulated time can be advanced.
    Synch.timeSim.threadStart();
  }  // end of the constructor for class "Car"



  public void run () {
    for (int I=1;  I<= 4; I++) {

      // Simulate driving around Barriefield.
      System.out.println("At time " + Synch.timeSim.curTime() + " Car "
          + myName + " is driving around Barriefield.");
      Synch.timeSim.doSleep(1, 500);

      // Now cross the causeway westbound, into Kingston.  This might
      // involve some waiting (if the westbound light is red).
      System.out.println("At time " + Synch.timeSim.curTime() + " Car "
          + myName + " wants to cross westbound.");
      
			try {
				Synch.mutex.acquire();
			} catch (Exception e){}
			
			//if westbound light isn't green
			if (Synch.light != 2){
				Synch.westboundCount++;
				Synch.mutex.release();
				//car can't continue until Traffic light turns green and releases westbound cars
					try {
						Synch.westboundCars.acquire();
					} catch (Exception e){}
					try {
						Synch.mutex.acquire();
					} catch (Exception e){}
				Synch.westboundCount--;
			}
			Synch.mutex.release();
			
      // *** Put synchronization code here, to make cars wait if the westbound
      // *** light is red.
      // You can choose how detailed to make your simulation:
	  // (1) In a less-detailed simulation, all waiting cars start crossing
	  //     instantly, as soon as the light turns green.
	  // or
	  // (2) In a more-detailed simulation, there is some reaction time, so when
	  //     the light turns green the first car starts crossing.  Then after a
	  //     short pause (simulated, for example, by "sleep(1)"), the second car
	  //     starts crossing, and so on.  This is more realistic than simulation (1):
	  //     when you are waiting in a long line of cars at a traffic light, you see the
	  //     light turn green up there, but it takes a while before all the cars ahead
	  //     of you get going, so you have to keep waiting even though the light is green.
	  //     In some cases, you don't even make it across on this green; you have to 
	  //     wait through another red cycle for the next green.
	  // Full marks will be awarded for either of these levels of detail in the simulation.


      // Now we have permission to cross the causeway.  Crossing is simulated
      // by a sleep.  The sleep time is chosen to be relatively long (compared
      // to the sleep times for driving around and getting donuts), so that
      // it frequently happens that several cars are on the bridge.
      System.out.println("At time " + Synch.timeSim.curTime() + " Car "
          + myName + " is starting to cross westbound.");
      Synch.timeSim.doSleep(100);

      // Simulate driving to Tim Hortons, buying donuts, eating them, and
      // driving back to the causeway.
      System.out.println("At time " + Synch.timeSim.curTime() + " Car "
          + myName + " is getting donuts at Tim Horton's.");
      Synch.timeSim.doSleep(1, 500);

      // Now cross the causeway eastbound, back into Barriefield.
      System.out.println("At time " + Synch.timeSim.curTime() + " Car "
          + myName + " wants to cross eastbound.");

      // *** Put synchronization code here, to make cars wait if the eastbound
      
		try {
			Synch.mutex.acquire();
		} catch (Exception e){}
		
		//if eastbound light isn't green otherwise car can continue
		if (Synch.light != 1){
			Synch.eastboundCount++;
			Synch.mutex.release();
			//car can't continue until Traffic light turns green and releases westbound cars
				try {
					Synch.eastboundCars.acquire();
				} catch (Exception e){}
				try {
					Synch.mutex.acquire();
				} catch (Exception e){}
			Synch.eastboundCount--;
		}
		Synch.mutex.release();
      // *** light is red..

      System.out.println("At time " + Synch.timeSim.curTime() + " Car "
          + myName + " is starting to cross eastbound.");
      Synch.timeSim.doSleep(100);

    } // end of "for" loop
    System.out.println("At time " + Synch.timeSim.curTime() + " Car "
        + myName + " has finished and disappears.  Poof!");
    Synch.done ++;
    if (Synch.done == 8) Synch.traffic = false;
    Synch.timeSim.threadEnd();  // Let timeSim know that this thread
                                // has ended.
  }  // end of "run" method
}  // end of class "Car"

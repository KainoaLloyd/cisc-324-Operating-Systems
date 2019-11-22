// This file defines class "Synch".  This class contains all the semaphores
// and variables needed to coordinate the car simulation.

public class Synch {

    public static TimeSim timeSim; // this class provides an accurate "sleep"
                                   // function.


   
    
// *** Declare your variables and semaphores here.  You might want to have two semaphores, one for use
// *** by waiting eastbound cars, and the other for use by waiting westbound cars.
// *** You might also want to have counters for the number of cars waiting in each direction.  You
// *** also need some variable to represent whether the light is green westbound, green eastbound, or
// *** red in both directions.
// *** If you use counters, you need to have a mutex semaphore to protect access to the counters.
//
// Which variables/semaphores you need depends on how you decide to code your solution.
    
    public static Semaphore mutex;   // example of a semaphore declaration; the initial value is given in MainMethod.java
    public static Semaphore eastboundCars;
    public static Semaphore westboundCars;
    public static int eastboundCount;
    public static int westboundCount;
    public static int light = 0; //0 is red 1 is east is green 2 is west is green;
    public static boolean traffic;
    public static int done = 0;


    public static int debug;  // set this to 1 or 2 to get extra output for debugging TimeSim.java

}
// This file defines class "Reader".

// This code uses
//      class Semaphore, from the java.util.concurrent package in Java 5.0 which defines the behaviour of a 
//                           semaphore, including acquire and release operations.
//      class Synch, which defines the semaphores and variables 
//                   needed for synchronizing the readers and writers.
//      class RandomSleep, which defines the doSleep method.


public class Reader extends Thread {
  int myName;  // The variable myName stores the name of this thread.
               // It is initialized in the constructor for class Reader.

  RandomSleep rSleep;  // rSleep can hold an instance of class RandomSleep.



  // This is the constructor for class Reader.  It has an integer parameter,
  // which is the name that is given to this thread.
  public Reader(int name) {
    myName = name;  // copy the parameter value to local variable "MyName"
    rSleep = new RandomSleep();  // Create an instance of RandomSleep.
  }  // end of the constructor for class "Reader"



  public void run () {
    for (int I = 0;  I < 5; I++) {
      System.out.println("Reader " + myName + " wants to read.  "
                         + "Beforehand, numReadersWaiting is "  + Synch.numReadersWaiting);

      // Do acquire on the "mutex" semaphore, to get exclusive access to the
      // variable "Synch.readcount". 
      try{
      	Synch.mutex.acquire();
      }
      catch(Exception e){}
      // If a writer is active, the first reader waits for "wrt" while still 
      // holding on to  "mutex".  Other readers, who arrive later, will get
      //  held up waiting for "mutex".
      if(Synch.mode ==2 || Synch.numWritersWaiting>0 ){
    	  Synch.numReadersWaiting++;
    	  Synch.mutex.release();
    	  try{
    		  Synch.readerSem.acquire();
    	  }
    	  catch(Exception e){}
          try{
            	Synch.mutex.acquire();
            }
            catch(Exception e){}
          Synch.numReadersWaiting--;
          Synch.numReadersActive++;
          System.out.println("Reader " + myName + " is now reading. "
        		  + "numReadersActive is " + Synch.numReadersActive); 

      }
      	Synch.mode = 1; //reading
    	  Synch.numReadersActive++;
    	    System.out.println("Reader " + myName + " is now reading.  "
                    + "numReadersWaiting is " + Synch.numReadersWaiting);
    	  Synch.mutex.release();
    	  rSleep.doSleep(1, 200);  
    	  
    	  //endread



      try{
      	Synch.mutex.acquire();
      }
      catch(Exception e){}
      Synch.numReadersActive--;
      System.out.println("Reader " + myName + " is finished reading.  " 
                         + "numReaders decremented to " + Synch.numReadersActive);
      
      if (Synch.numReadersActive==0){
    	  if (Synch.numWritersWaiting >0){
    		  Synch.writerSem.release();
    		  Synch.mode = 2;//writing
    	  }else
    	  {
    		  	Synch.mode = 0;//idle
    	  }
      }	  
      Synch.mutex.release();


      // Simulate "doing something else".
      rSleep.doSleep(1, 1000);
    } // end of "for" loop
  }  // end of "run" method
}  // end of class "Reader"

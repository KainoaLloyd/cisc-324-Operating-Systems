// This file defines class "writer".

// This code uses
//      class Semaphore, from the java.util.concurrent package in Java 5.0 which defines the behaviour of a 
//                           semaphore, including acquire and release operations.
//      class Synch, which defines the semaphores and variables
//                   needed for synchronizing the readers and writers.
//      class RandomSleep, which defines the doSleep method.


public class Writer extends Thread {
  int myName;  // The variable myName stores the name of this thread.
               // It is initialized in the constructor for class Reader.

  RandomSleep rSleep;  // rSleep can hold an instance of class RandomSleep.



  // This is the constructor for class Writer.  It has an integer parameter,
  // which is the name that is given to this thread.
  public Writer(int name) {
    myName = name;  // copy the parameter value to local variable "MyName"
    rSleep = new RandomSleep();   // Create and instance of RandomSleep.
  }  // end of the constructor for class "Writer"



  public void run () {
    for (int I = 0;  I < 5; I++) {

      // Get permission to write
        // Get permission to write
        System.out.println("Writer " + myName + " wants to write" );
  	 try{
        	Synch.mutex.acquire();
        }
        catch(Exception e){}
  	  if ( Synch.mode !=0){
  		Synch.numWritersWaiting++;	
  		Synch.mutex.release();
  		try{
  			Synch.writerSem.acquire();
  		}
  		catch(Exception e){}
  		try{
  			Synch.mutex.acquire();
  		}
  		  catch(Exception e){}
  		Synch.numWritersWaiting--;
  	  }/*else{
  	   	  try{
  				  Synch.writerSem.acquire(); // if mode is idle we acquire writerSem in order to make sure its the only writer writing
  			  }
  			  catch (Exception e){}
  	  }*/
  	        // Simulate the time taken by writing.
        
       Synch.mode = 2;  
  	  //Synch.numWritersActive++;
  	System.out.println("Writer " + myName + " is now writing");
  	  Synch.mutex.release();
  		
  	  rSleep.doSleep(1, 200);
  	  }

 
      

//endwrite
    try{ 
    	Synch.mutex.acquire();
    }catch(Exception e){}
    Synch.numWritersActive--; 
   System.out.println("Writer " + myName + " is finished writing");
   
    
    if (Synch.numReadersWaiting > 0){
	    for (int j = 1; j <= Synch.numReadersWaiting; j++){
	        System.out.println("Writer " + myName + " is finished writing");
	
	    	Synch.readerSem.release();
	    }
	    Synch.mode = 1; //reading
    }else if(Synch.numWritersWaiting > 0){
    	Synch.writerSem.release();
   
    }else{
    	Synch.mode = 0;
    }
    Synch.mutex.release();
    rSleep.doSleep(1, 1000);
 }  
  }


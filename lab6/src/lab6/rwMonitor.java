package lab6;
//This file defines class "rwMonitor", a monitor for the reader/writer
//problem.  It defines methods startRead, endRead, startWrite, endWrite.
//
//The synchronization (readers have priority) is the same as was used
//in lab 3.  Where lab 3 used semaphores, a monitor is used here.
//
//startRead, endRead, startWrite, and endWrite are declared to be 
//"synchronized" methods.  The "synchronized" keyword tells the Java 
//compiler to provide mutually exclusive access.  At any given time, at most
//one thread can be executing any of the code inside these synchronized
//methods.  For example, if thread R1 is executing startRead, then any
//other threads that call startRead, endRead, startWrite, or endWrite
//have to wait.  The compiler generates code to do this, both to make the
//calling threads wait, and to arrange that when R1 finishes execution of
//StartRead, one of the waiting threads is allowed to continue executing.

public class rwMonitor {
private int readcount;   // A count of the number of readers who are
                        // currently reading.
private int writecount;  // A count of the number of writers who are
                        // currently writing (0 or 1).

// This is the constructor for class rwMonitor.
public rwMonitor() {
 readcount = 0;
 writecount = 0;
}  // end of the constructor for class "rwMonitor"


//               ---- method startRead ----
// If there are any writers active (tested by "writecount>0") then wait
// until another thread notifies us.
//
// Once no writers are active, the reader can go ahead. Record 
// this by incrementing readcount.
public synchronized void startRead () {
 while (writecount > 0) {
    try {  wait(); } catch (Exception e) {};  // wait for "notify()"
 }  // end of the while loop
 readcount++;
}  // end of "startRead" method



//               ---- method endRead ----
// The endRead method decrements readcount.
// If readcount is now zero, then notify all the waiting threads.
// All of the waiting threads will be writers; any readers that showed
// up would not wait in "startRead".  All of the waiting writers are
// awakened, and they all compete to get the lock for the monitor.  The
// first writer to get in will set writecount to 1, and will start writing.
// The other writers will find that writecount still is greater than 0, and
// they will wait() again.
public synchronized void endRead () {
 readcount--;
 if (readcount == 0) {
     notifyAll(); 
 }  // if readcount = 0
}  // end of "endRead" method



//               ---- method startWrite ----
// The startWrite method waits until no readers or writers are active.
// Then the writer can go ahead.  Record this by incrementing writecount.
public synchronized void startWrite () {
 while (readcount > 0 || writecount > 0) {
    try {  wait(); } catch (Exception e) {};  // wait for notify()
 }  // end of the while loop
 writecount++;
}  // end of "startWrite" method



//               ---- method endWrite ----
// The endWrite method decrements writecount and notifies all waiting
// processes. 
//
// The notifyAll wakes up all waiting threads (both readers and
// writers).  All the readers will start, or one of the writers will start.
// The other threads will wait() again.
//
// As an example, suppose that 3  reader and 2 writer threads are waiting.
// Each of these threads is part-way through executing a synchronized
// routine (startRead, endRead, startWrite, endWrite) and is stuck at
// a "wait" statement, where it has "stepped out" of the monitor.  (In other
// words, it no longer holds the lock for the monitor.)
// The notifyAll() wakes up all 5 of the waiting threads, in some 
// arbitrary order.  The threads execute one at a time (stepping
// back in to the monitor, to execute more of the synchronized
// routine that they called.)  If it happens that a reader thread 
// gets the monitor lock first, it finds that writecount == 0 so it sets
// readcount to 1 and starts reading.  The writers that are awakened notice
// that readcount>0,  so they again execute wait().  The other readers 
// notice that writecount is still zero, so they start.  Now we have three 
// readers active, readcount = 3, writecount = 0, and two writers still 
// waiting in the startWrite method.
public synchronized void endWrite () {
 writecount--;
 notifyAll();  // notify all processes that are waiting.  This will
               // start all waiting readers, or one waiting writer.
}  // end of "endWrite" method

}  // end of class "rwMonitor"

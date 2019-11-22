// This file defines class "Synch".  This class contains all the semaphores
// and variables needed to coordinate the instances of the Reader and Writer
// classes.  

import java.util.concurrent.*;

public class Synch {

  public static Semaphore mutex;
  public static Semaphore readerSem;
  public static Semaphore writerSem;
  public static int mode = 0; // 0 = idle, 1 = read, 2 = write

  public static int numReadersWaiting = 0;
  public static int numWritersWaiting = 0;
  public static int numReadersActive = 0;
  public static int numWritersActive = 0;
}  // end of class "Synch"


public class TrafficLight extends Thread{
	
	
	public TrafficLight(){
		Synch.timeSim.threadStart();
	}
	
	 public void run () {
		 
		 while (Synch.traffic){
			 
			try {
				Synch.mutex.acquire();
			} catch (Exception e){}
			System.out.println("Eastbound light is green.");
			//light is green for eastbound traffic
			Synch.light = 1;
			for (int i = 0; i <Synch.eastboundCount; i++){
				Synch.eastboundCars.release();
			}
			Synch.mutex.release();
			Synch.timeSim.doSleep(200);
			System.out.println("Both lights are red.");
			//both lights are red
			Synch.light= 0;
			Synch.timeSim.doSleep(100);
			
			try {
				Synch.mutex.acquire();
			} catch (Exception e){}
			System.out.println("Westbound light is green.");
			//light is green for westbound traffic
			Synch.light = 2;
			for (int i = 0; i <Synch.westboundCount; i++){
				Synch.westboundCars.release();
			}	
			
			Synch.mutex.release();
			Synch.timeSim.doSleep(200);
			System.out.println("Both lights are red.");
			//light is red
			Synch.light= 0;
			Synch.timeSim.doSleep(100);
			
	 	}
		 Synch.timeSim.threadEnd();
	 }

}

package com.okanalan;
//https://ufukuzun.files.wordpress.com/2015/05/java-da-multithreading.pdf

class PrintDemo {
	   public void printCount() {
	      try {
	         for(int i = 0; i < 10; i++) {
	            System.out.println("Counter   ---   "  + i );
	         }
	      } catch (Exception e) {
	         System.out.println("Thread  interrupted.");
	      }
	   }
	}

	class ThreadDemo extends Thread {
	   private Thread t;
	   private String threadName;
	   PrintDemo  PD;

	   ThreadDemo( String name,  PrintDemo pd) {
	      threadName = name;
	      PD = pd;
	   }
	   
	   public void run() {
	      synchronized(PD) {
	         PD.printCount();
	      }
	      System.out.println("Thread " +  threadName + " exiting.");
	   }

	   public void start () {
	      System.out.println("Starting " +  threadName );
	      if (t == null) {
	         t = new Thread (this, threadName);
	         t.start ();
	      }
	   }
	}

	public class SynchronizedSample2 {

	   public static void main(String args[]) {
	      PrintDemo PD = new PrintDemo();

	      ThreadDemo T1 = new ThreadDemo( "Thread - 1 ", PD );
	      ThreadDemo T2 = new ThreadDemo( "Thread - 2 ", PD );

	      T1.start();
	      T2.start();

	      // wait for threads to end
	      try {
	         T1.join();
	         T2.join();
	      } catch ( Exception e) {
	         System.out.println("Interrupted");
	      }
	   }
	}





/*import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SynchronizedSample2 {
	
	int ct=0;
	
	private Object lock1 = new Object();
	private Object lock2 = new Object();

	private List<Integer> list1 = new ArrayList<>();
	private List<Integer> list2 = new ArrayList<>();
	
	public static void main(String[] args) {
		
		SynchronizedSample2 tipt = new SynchronizedSample2();
		
		long startTime = System.currentTimeMillis();
		tipt.work();
		System.out.println("passing time: "+(System.currentTimeMillis()-startTime));
		
	}
	
	private void work(){
		Thread t1 = new Thread( new Runnable() {
			@Override
			public void run() {
				for(int i = 0; i<1000;i++) {
					process();
				}
			}
		});
		
		Thread t2 = new Thread( new Runnable() {
			@Override
			public void run() {
				for(int i = 0; i<100;i++) {
					process(); 
				}
			}
		});
		
		t1.start();
		t2.start();
		
	      try {
	         t1.join();
	         t2.join();
	      } catch ( Exception e) {
	         System.out.println("Interrupted");
	      }
	}
	
	private void process() {
		for(int i = 0; i<=100;i++) {
			addNewIntegerToList1();
			addNewIntegerToList2();
			System.out.println(i);
		}
	}
	
	private void addNewIntegerToList1() {
		synchronized(lock1) {
			try {
				Thread.sleep(1);
			}catch(InterruptedException e1){
				
			}
			//System.out.println(ct++);
			list1.add(2);
		}
	}
	private void addNewIntegerToList2() {
		synchronized(lock2) {
			try {
				Thread.sleep(1);
			}catch(InterruptedException e2){
				
			}
			//System.out.println(ct++ + "okan");
			list2.add(1);
		}
	}
	
}
*/
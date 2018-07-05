package com.okanalan;

import java.io.*;


public class SynchronizedSample1 {
	
	private int count=0;
	
	public static void main(String[] args) {
		
		SynchronizedSample1 tipt = new SynchronizedSample1(); 
		tipt.doCount();
		
	}
	
	public synchronized void increase() {
		count++;
	}
	
	private void doCount() {
		Thread t1 = new Thread( new Runnable() {
			@Override
			public void run() {
				for(int i = 0; i<1000;i++) {
					increase();
				}
			}
		});
		
		Thread t2 = new Thread( new Runnable() {
			@Override
			public void run() {
				for(int i = 0; i<1000;i++) {
					increase(); 
				}
			}
		});
		
		t1.start();
		t2.start();
		
	      // wait for threads to end
	      try {
	         t1.join();
	         t2.join();
	      } catch ( Exception e) {
	         System.out.println("Interrupted");
	      }
		
		System.out.println(count);
		
	}
	
}

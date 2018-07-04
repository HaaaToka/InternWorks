package com.okanalan;

import java.io.*;


public class TrySomethingHereBoyz {
	
	public static void main(String[] args) {
		
		Thread tr = new Thread( ()->System.out.println("okan") );
		
		tr.start();
	}
	
}

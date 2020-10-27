package com.process;

public class Test {

	public static void main(String[] args) throws InterruptedException {
		int data1 = 0;
		for(int i= 1; i<=10;i++) {
			data1+=i;
			System.out.println("data1: "+data1);
			Thread.sleep(300);
		}
		
		int data2 = 0;
		for(int i= 1; i<=10;i++) {
			data2+=i;
			System.out.println("data2: "+data2);
			Thread.sleep(300);
		}
	}

}

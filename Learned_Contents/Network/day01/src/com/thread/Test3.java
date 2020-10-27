package com.thread;

import java.util.Scanner;

class Thread1 extends Thread{
	
	boolean flag;

	public Thread1() {
	}
	
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	@Override
	public void run() {
		for(int i=0; i<=100; i++) {
			System.out.println("Start");
			while(true) {
				if(flag==false) {
					System.out.println("Stop...");
					break;
				}
				//System.out.println("Connecting...");
				try {
					Thread.sleep(500);
					
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			
			System.out.println("End");
		}
	}
	
}

public class Test3 {

	public static void main(String[] args) throws InterruptedException {
		Thread1 r = new Thread1();
		Thread t1 = null;
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.println("Input cmd");
			String cmd = sc.nextLine();
			if(cmd.equals("start")) {
				t1 = new Thread(r);
				t1.start();
			}else if(cmd.equals("Stop")) {
				r.setFlag(false);
			}else {
				break;
			}
		}
		sc.close();

//		//Thread를 제어하는 방법
//		Thread1 t1 = new Thread1();
//		t1.start();
//		Thread.sleep(10000);
//		t1.setPriority(10);
		
	}

}

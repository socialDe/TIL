package com.sync;

public class Dth extends Thread{

	
	Account acc;
	public Dth(Account acc) {
		this.acc = acc;
	}
	
	@Override
	public void run() {
		while(acc.getBalance() >= 0) {
			try {
				Thread.sleep(90);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			int money = (int)(Math.random()*9+1)*100;
			acc.deposit(money);
			System.out.println(acc);
		}
	}
}

package com.sync;

public class Wth extends Thread{
	
	
	Account acc;
	
	public Wth(Account acc) {
		this.acc = acc;
	}
	
	@Override
	public void run() {
		while(acc.getBalance() >= 0) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			int money = (int)(Math.random()*9+1)*100;
			acc.withdraw(money);
			System.out.println(acc);
		}
	}

}

package com.tcpip2;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import com.msg.Msg;

public class Client {

   int port;
   String address;
   Socket socket;
   Sender sender;
   
   public Client() {
      // TODO Auto-generated constructor stub
   }
   
   public Client(String address, int port) {
      this.address = address;
      this.port = port;
   }
   
   public void connect() throws Exception {
      try {
         socket = new Socket(address, port);
         System.out.println("Connected ..");
      } catch (Exception e) {
         while(true) {
            Thread.sleep(2000);
            try {
               socket = new Socket(address, port);
               System.out.println("Connected ..");
               break;
            } catch (IOException e1) {
               System.out.println("Re-Try ..");
            }
         }
      }
      sender = new Sender();
   }
   
   class Sender implements Runnable{
     
      ObjectOutputStream dos;
      Msg mo;
      
      public void setMo(Msg mo) {
    	  this.mo = mo;
      }
      
      public Sender() {
         
         try {
            dos = new ObjectOutputStream(socket.getOutputStream());
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
      
      @Override
      public void run() {
         if(dos != null) {
            try {
               dos.writeObject(mo);
            } catch (IOException e) {
               System.out.println("서버와의 연결이 정상적이지 않습니다.");
               System.exit(0);
            }
         }
      }
   }
   
   public void request() throws Exception {
      Scanner sc = new Scanner(System.in);
      try {
    	 Msg mo = null;
         while(true) {
            System.out.println("[Input Msg:]");
            String msg = sc.nextLine();
            // 
            mo = new Msg("192.168.0.73","[Jaeuk]", msg.trim());
            //
            sender.setMo(mo);
            new Thread(sender).start();
            Thread.sleep(800);
            
            if(mo.getMsg().equals("q")) {
               System.out.println("Exit Client");
               break;
            }
         }
      } catch (Exception e) {
         
      } finally {
         sc.close();
         if(socket != null) {
            socket.close();
         }
      }
   }
   
   public static void main(String[] args) {
      Client client = new Client("192.168.0.73", 7777);
      try {
         client.connect();
         client.request();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

}
package com.jhs.main;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.PriorityQueue;

public class PriorityQueueServer{
	
	private Socket socket;
	private static PriorityQueue <String> queue1;
	private static final int port = 17780;
	
	private static boolean outputThreadStarted;

	public PriorityQueueServer(Socket socket){	
		this.socket = socket;
	}
	
	public static void main(String[] args) throws IOException{
        @SuppressWarnings("resource")
		ServerSocket serverSocket = new ServerSocket(port);
        outputThreadStarted = false;
        
		queue1 = new PriorityQueue <String>();

        while(true){
            //READ
            Socket ssocket = serverSocket.accept();
            System.out.println("Client has connected: "+ssocket.getRemoteSocketAddress().toString());
        	new PriorityQueueServer(ssocket).run();
        }
	}
	
	public void run(){	
		try{
			NewInputHandler nih = new NewInputHandler(socket);
			NewOutputHandler noh = new NewOutputHandler(socket);
			
			Thread inputThread = new Thread(nih);
			Thread outputThread = new Thread(noh);
			
			inputThread.run();
			
			if(!inputThread.isAlive()){
				String message = nih.getMessage();
	            queue1.add(message);
	            System.out.println(message+":ADDED");

	            if(!outputThreadStarted){
					outputThreadStarted = true;
					outputThread.run();
	            }
	            else{
				    if(queue1.size() >= 2){
			            String returnMessage = queue1.poll()+"_"+queue1.poll();
			        	System.out.println("MATCH FOUND! Between "+returnMessage.split("_")[0]+" & "+returnMessage.split("_")[1]);
			        	noh.setMessage(returnMessage);
			        	outputThread.run();
						outputThreadStarted = false;
			    	}
			    	else{
			    	    System.out.println(queue1.size());
			    	}
	            }
			}
	    }
	    catch (Exception e){
	        e.printStackTrace();
	    }
	}
}
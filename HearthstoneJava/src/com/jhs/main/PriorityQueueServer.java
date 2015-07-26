package com.jhs.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.PriorityQueue;

public class PriorityQueueServer implements Runnable{
	
	private Socket socket;
	private static PriorityQueue <String> queue1;

	public PriorityQueueServer(Socket socket){	
		this.socket = socket;
	}
	
	public static void main(String[] args) throws IOException{
        int port = 25002;
        @SuppressWarnings("resource")
		ServerSocket serverSocket = new ServerSocket(port);
        
		queue1 = new PriorityQueue <String>();

        while(true){
            //READ
            Socket ssocket = serverSocket.accept();
            System.out.println("Client has connected: "+ssocket.getRemoteSocketAddress().toString());
        	new Thread(new PriorityQueueServer(ssocket)).start();
        }
	}
	
	public void run(){	
		try{
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String message = br.readLine();
            //System.out.println("Message received from client is "+message);
            
            queue1.add(message);
            System.out.println(message+":ADDED");
            
            if(queue1.size() >= 2){
	            //POST
	            OutputStream os = socket.getOutputStream();
	            OutputStreamWriter osw = new OutputStreamWriter(os);
	            BufferedWriter bw = new BufferedWriter(osw);
	            
	            String returnMessage = queue1.poll()+"_"+queue1.poll();
            	System.out.println("MATCH FOUND! Between "+returnMessage.split("_")[0]+" & "+returnMessage.split("_")[1]);
	            		
	            bw.write(returnMessage);
	            //System.out.println("Message sent to the client is "+returnMessage);
	            bw.flush();
            }
	    }
	    catch (SocketException e){
	    	System.out.println("The connection did reset, shutting down server");
	        e.printStackTrace();
	    }
	    catch (Exception e){
	        e.printStackTrace();
	    }
	}
}

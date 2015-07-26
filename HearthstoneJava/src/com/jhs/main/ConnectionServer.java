package com.jhs.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class ConnectionServer implements Runnable{

	private Socket socket;
	
	
	public ConnectionServer(Socket socket){
		this.socket = socket;
	}
	
	public static void main(String[] args) throws IOException{
        int port = 25000;
        @SuppressWarnings("resource")
		ServerSocket serverSocket = new ServerSocket(port);

        while(true){
            //READ
            Socket ssocket = serverSocket.accept();
            System.out.println("Client has connected: "+ssocket.getRemoteSocketAddress().toString());
        	new Thread(new ConnectionServer(ssocket)).start();
        }
	}
	
	public void run(){
	    try{
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String message = br.readLine();
            System.out.println("Message received from client is "+message);
            
            switch (message) {
            	case "deck_0_queueUp": queueUp(socket.getRemoteSocketAddress().toString()); break;
            	default: System.out.println("NAH"); break;
            }
        }
	  
	    catch (SocketException e){
	    	e.printStackTrace();
	    	System.out.println("The connection did reset, shutting down server");
	    }
	    catch (Exception e){
	        e.printStackTrace();
	    }
	}
	
	public void queueUp(String s) throws IOException{
		String host = "localhost";
        int port = 25002;
        InetAddress address = InetAddress.getByName(host);
        socket = new Socket(address, port);
        
        //POST
        OutputStream os = socket.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);
        
        String sendMessage = s.substring(1) + "\n";
        bw.write(sendMessage);
        System.out.println("SENT MESSAGE: "+sendMessage);
        bw.flush();
        
        //GET
        InputStream is = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String message = br.readLine();
        System.out.println("RECEIVED MESSAGE: " +message);
	}
}
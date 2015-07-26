package com.jhs.main;

import java.io.*;
import java.net.*;
 
public class NewClient {
	private final int port = 17775;
	private Socket socket;
	
	public static void main(String[] args) {
		try {
			new NewClient().startClient();
		} catch (Exception e) {
			System.out.println("Something failed: " + e.getMessage());
			e.printStackTrace();
		}
	}
 
	public void startClient() throws IOException {
		try {
			InetAddress host = InetAddress.getLocalHost();
			socket = new Socket(host.getHostName(), port);
			
            while(true){
            	writeToServerStream();
            	System.out.println(readFromServerStream());
            }
		} catch (UnknownHostException e) {
			System.exit(1);
		} catch (IOException e) {
			System.exit(1);
		} 
	}
	
	public String readFromServerStream(){
		NewInputHandler nihPQ = new NewInputHandler(socket);
		Thread inputThreadPQ = new Thread(nihPQ);
		
		inputThreadPQ.run();
		
		return nihPQ.getMessage();
	}
	
	public void writeToServerStream(){
		NewOutputHandler noh = new NewOutputHandler(socket);
		Thread outputThread = new Thread(noh);
		
		//noh.enableCustomMessage(true);
		noh.setMessage("queueUp");
		
		outputThread.run();
	}
	
	/*public String readFromServerStream() {
		BufferedReader in;
		
		String message;
		
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			message = in.readLine();
			return message;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
			return null;
		}
	}*/
	
	/*public void writeToServerStream(){
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out;
        
        String message;
        
		try {
	        //message = stdIn.readLine();
			message = "queueUp";
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		
	        out.write(message + "\n");
	        out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
	}*/
}
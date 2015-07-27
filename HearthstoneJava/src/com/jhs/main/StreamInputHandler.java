package com.jhs.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class StreamInputHandler implements Runnable{

	@SuppressWarnings("unused")
	private Socket socket;
	
	private InputStream is;
	private InputStreamReader isr;
	private BufferedReader br;
	
	private String message;
	
	public StreamInputHandler(Socket socket){
		this.socket = socket;
		
		try {
			is = socket.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run(){
		try {
			message = br.readLine();
			System.out.println("The message received is: "+message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getMessage(){
		return message;
	}
}

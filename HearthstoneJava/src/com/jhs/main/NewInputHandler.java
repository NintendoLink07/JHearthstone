package com.jhs.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class NewInputHandler implements Runnable{
	
	private Socket socket;
	private String message;
	
	public NewInputHandler(Socket socket){
		this.socket = socket;
	}
	
	public void run(){
        InputStream is;
		try {
			is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            message = br.readLine();
            System.out.println("Message received from the client is "+message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getMessage(){
		return message;
	}
}
package com.jhs.main;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class StreamOutputHandler implements Runnable {

	@SuppressWarnings("unused")
	private Socket socket;
	
	private OutputStream os;
	private OutputStreamWriter osw;
	private BufferedWriter bw;
	
	private String message;
	
	public StreamOutputHandler(Socket socket){
		this.socket = socket;
		
		try {
			os = socket.getOutputStream();
            osw = new OutputStreamWriter(os);
            bw = new BufferedWriter(osw);           
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run(){
        try {
        	if(message==null){
        	}
        	else{
	        	System.out.println("The message to be sent is: "+message);
				bw.write(message+"\n");
		        bw.flush();
        	}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setMessage(String s){
		this.message = s;
	}
	
	@Deprecated
	public void sendMessage(String s){
		this.message = s;
		this.run();
	}
}

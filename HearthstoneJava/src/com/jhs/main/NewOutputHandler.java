package com.jhs.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class NewOutputHandler implements Runnable{
	
	private Socket socket;
	private String message;
	private boolean customMessage;
	
	public NewOutputHandler(Socket socket){
		this.socket = socket;
		this.message = null;
		this.customMessage = false;
	}
	
	public void run(){
        OutputStream os;
        
		try {
			os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);

    		if(customMessage){
    			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        		message = stdIn.readLine();
    		}
    		
            if(message==null){}
            else{
	            bw.write(message + "\n");
	            System.out.println("Message sent to the client is "+message);
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
	
	public void enableCustomMessage(boolean b){
		this.customMessage = b;
	}
}
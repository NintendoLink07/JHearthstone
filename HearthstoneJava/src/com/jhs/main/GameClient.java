package com.jhs.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class GameClient{

	private static Socket socket;
	
    static String host = "localhost";
    static int port = 25000;
	
	public static void main(String args[])
	{
	    Scanner input = new Scanner(System.in);
	    
	    while(true) {
	        try {
	            InetAddress address = InetAddress.getByName(host);
	            socket = new Socket(address, port);
	            
	            //POST
	            OutputStream os = socket.getOutputStream();
	            OutputStreamWriter osw = new OutputStreamWriter(os);
	            BufferedWriter bw = new BufferedWriter(osw);
	            
	            String command=input.next();
	            String sendMessage = command + "\n";
	            bw.write(sendMessage);
	            bw.flush();
	            System.out.println("Message sent to the server : "+sendMessage);
	
	            //GET
	            InputStream is = socket.getInputStream();
	            InputStreamReader isr = new InputStreamReader(is);
	            BufferedReader br = new BufferedReader(isr);
	            String message = br.readLine();
	            System.out.println("Message received from the server : " +message);
	        }
	        catch (ConnectException e) { //When the connection is refused upon connecting to the server
	            //promt the user here
	            System.out.println("Connection refused");
	            break; //to quit the infinite loop
	        } 
	        catch (IOException e) { //when connection drops, server closed, loses connection
	           //promt the user here
	           System.out.println("Disconnected from server, probably offline");
	           break; //to quit the infinite loop
	        }
	    }
	    input.close();
	}
}
package com.jhs.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class LobbyClient implements Runnable{

	private final int lobbyServerPort;
	
	private Socket socket;
	
	private BufferedReader keyboardInput;
	
	private StreamInputHandler sih;
	private StreamOutputHandler soh;
	
	public LobbyClient(int port){
		lobbyServerPort = port;
		
		keyboardInput = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public void run(){
		while(true){
			
			try {
				InetAddress host = InetAddress.getLocalHost();	
				socket = new Socket(host.getHostName(), lobbyServerPort);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			sih = new StreamInputHandler(socket);
			soh = new StreamOutputHandler(socket);
			
			System.out.print("Command: ");
			
			String readLine;
			
			try {
				readLine = keyboardInput.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				readLine = null;
			}
			
			if(readLine!=null){
				if(readLine.equalsIgnoreCase("login")){
					soh.setMessage(readLine+"_user_pass");
					soh.run();

					sih.run();
				}
			}
		}
	}
	
	public static void main (String[]args){
		new LobbyClient(15000).run();
	}
}

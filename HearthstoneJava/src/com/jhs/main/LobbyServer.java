package com.jhs.main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class LobbyServer extends Server {
	
	private final int loginServerPort;

	protected StreamInputHandler sihLogin;  //InputHandler for incoming stream-packets to the login server
	protected StreamOutputHandler sohLogin; //OutputHandler for outgoing stream-packets to the login server
	
	public LobbyServer(int port){
		super(port);
		loginServerPort = 15001;
	}
	
	@Override
	public void run(){
		try {
			serverSocket = new ServerSocket(getServerPort());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(true){
			
			try {
				Socket localSocket = serverSocket.accept();
				
				sih = new StreamInputHandler(localSocket);
				soh = new StreamOutputHandler(localSocket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				sih = null;
				soh = null;
			}
			
			sih.run();
			//sohClient.run();
			
			processMessage(sih.getMessage());
		}
	}
	
	private void processMessage(String message){
		if(message.contains("login")){
			try {
				Socket socket = new Socket(InetAddress.getLocalHost().getHostName(), loginServerPort);
				
				sohLogin = new StreamOutputHandler(socket);
				sihLogin = new StreamInputHandler(socket);
				
				sohLogin.setMessage(message);
				sohLogin.run();
				
				sihLogin.run();
				
				System.out.println("|"+sihLogin.getMessage()+"|");
				
				if(sihLogin.getMessage().equals("granted")){
					soh.setMessage("Logged in");
				}
				else{
					soh.setMessage("Not logged in!");
				}
				
				soh.run();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	
					
		}
	}
	
	public static void main (String[]args){
		new LobbyServer(15000).run();
	}
}

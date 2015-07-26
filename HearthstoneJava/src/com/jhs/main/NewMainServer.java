package com.jhs.main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/*
 * 
 * -LÖSUNG FÜR INPUTHANDLER FINDEN
 * ANSONSTEN INPUTHANDLER NEUSCHREIBEN
 * -MIT SPIELLOGIK BEGINNEN
 * 
 */

public class NewMainServer implements Runnable {

	final static int portNumber = 17775; //this: port number
	final static int portNumberPQ = 17780; //Priority-Queue-Server: port number
	 
	public static void main(String[] args) 
	{
		try {
			new NewMainServer().run();
		} catch (Exception e) {
			e.printStackTrace();
		}
 
	}
 
	public void run(){
		ServerSocket serverSocket = null;
		boolean listening = true;
 
		try {
			serverSocket = new ServerSocket(portNumber);
			
		} catch (IOException e) {
			System.err.println("Could not listen on port: " + portNumber);
			System.exit(-1);
		}
 
		while (listening) {
			handleClientRequests(serverSocket);
		}
 
		try {
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void handleClientRequests(ServerSocket serverSocket){
		try {
			new ClientRequestHandler(serverSocket.accept()).run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public class ClientRequestHandler implements Runnable{
		Socket clientSocket, socketPQ;
		
		public ClientRequestHandler(Socket socket){
			clientSocket = socket;
			try {
				clientSocket.setReuseAddress(true);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void run(){
			String ipClient = clientSocket.getInetAddress().getHostAddress().toString();
			System.out.println("Client connected to socket: " + ipClient);
			
			String read = readFromStream(clientSocket);
			
			//System.out.println("FROM CLIENT: \""+read+"\"");
			
			if(read.equalsIgnoreCase("queueUp")){
				try {
					socketPQ = new Socket(InetAddress.getLocalHost().getHostName(), portNumberPQ);
					
					writeToStream(ipClient, socketPQ);
					readFromStream(socketPQ);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				String write = "You're queued up!";
				writeToStream(write, clientSocket);
				
				
			}
		}
		
		public String readFromStream(Socket socket) {
			NewInputHandler nihPQ = new NewInputHandler(socket);
			Thread inputThreadPQ = new Thread(nihPQ);
			inputThreadPQ.setDaemon(true);
			
			inputThreadPQ.run();
			
			return nihPQ.getMessage();
		}
		
		public void writeToStream(String sendMessage, Socket socket){
			NewOutputHandler noh = new NewOutputHandler(socket);
			Thread outputThread = new Thread(noh);
			outputThread.setDaemon(true);
			
			noh.setMessage(sendMessage);
			
			outputThread.run();
		}
	}
}

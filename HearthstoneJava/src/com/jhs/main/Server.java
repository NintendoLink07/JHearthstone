package com.jhs.main;

import java.net.ServerSocket;

public class Server implements Runnable{

	private final int serverPort; //Port of this server
	
	protected ServerSocket serverSocket;
	
	protected StreamInputHandler sih;  //InputHandler for incoming stream-packets to a client
	protected StreamOutputHandler soh; //OutputHandler for outgoing stream-packets to a client
	
	public Server(int port){
		serverPort = port;
		setServerSocket(null);
	}
	
	public void run(){	
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public int getServerPort() {
		return serverPort;
	}

	public StreamInputHandler getStreamInputHandler() {
		return sih;
	}

	public void setStreamInputHandler(StreamInputHandler sihClient) {
		this.sih = sihClient;
	}

	public StreamOutputHandler getStreamOutputHandler() {
		return soh;
	}

	public void setStreamOutputHandler(StreamOutputHandler sohClient) {
		this.soh = sohClient;
	}
}

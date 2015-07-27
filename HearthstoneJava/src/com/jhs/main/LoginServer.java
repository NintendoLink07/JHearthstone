package com.jhs.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginServer extends Server{
	
	private Connection con;
	
	public LoginServer(int port){
		super(port);
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
			//soh.run();
			
			connectToDatabase();
			executeLoginVerification(sih.getMessage());
		}
	}
	
	private void connectToDatabase(){
		
		con = null;
		
		try {
			Class.forName( "com.mysql.jdbc.Driver" );
			
			con = DriverManager.getConnection(
			    "jdbc:mysql://localhost:3306/jhearthstone",
			    "loginServer0",
			    "pass1234");
			
			con.setReadOnly(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void executeLoginVerification(String message){
		String[] loginData = message.split("_");
		
		Statement st = null;
		ResultSet rs = null;
		
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT id,name,password FROM accounts WHERE name='"+loginData[1]+"' AND password='"+loginData[2]+"'");
			
			if(rs.next()){
				/*rs.beforeFirst();
				while (rs.next()) {
		            int id = rs.getInt("id");
		            String name = rs.getString("name");
		            String password = rs.getString("password");
		            System.out.println(id + "\t" + name +
		                               "\t" + password);
		        }*/
				
				soh.setMessage("granted");
			}
			else{
				soh.setMessage("denied");
			}
			
			soh.run();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main (String[]args){
		new LoginServer(15001).run();
	}
}

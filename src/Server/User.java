package Server;

import java.net.Socket;
import java.util.Date;

public class User {
	/*
	 * Every connection to the server is a "user" and have their own ServerThread for comunication.
	 * The user-objects prints out when it gets created and when it dies for the admin to keep track.
	 */
	private ServerThread thread = null;
	int id = (int) new Date().getTime();
	private Socket openForConnections;
	
	User(Socket socket)
	{
		openForConnections = socket;
		thread = new ServerThread(this, openForConnections);
		thread.start();
		System.out.println("Client Connected");
	}
	
	public int getID()
	{
		return id;
	}
	
	public void start()
	{
		
	}
}

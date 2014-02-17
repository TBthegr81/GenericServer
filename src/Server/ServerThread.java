package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread{
	/*
	 * This is the thread every user have, it handles sending either objects or strings between server and user,
	 * Right now it only prints out the data the user sends but the idea is that it checks the data and returns
	 * an appropriate answer, like if the user wrote the wrong password or if the message to someone got sent.
	 */
	private final int STRINGS = 1;
	private final int OBJECTS = 2;
	private int mode = 1;
	private ObjectOutputStream outStream;
	private ObjectInputStream inStream;
	private Socket socket;
	private String line = null;
	private BufferedReader in = null;
	
	ServerThread(User user, Socket socket)
	{
		this.socket = socket;
		/*
		 * Based on the mode-variable it either reads strings or objects based on what the server needs to do
		 */
		if(mode == STRINGS)
		{
			try {
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			} catch (IOException e) {
				Lib.error("Cant get InputStream!",e.toString());
			}
		}
		else if(mode == OBJECTS)
		{
			// Opens stream top out socket for sending and receiving
			try {
				outStream = new ObjectOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				Lib.error("Cant open ObjectOutputStream", e.getLocalizedMessage());
			}
			try {
				inStream =  new ObjectInputStream(socket.getInputStream());
			} catch (IOException e) {
				Lib.error("Cant open ObjectInputStream", e.getLocalizedMessage());
			}
		}
	}
	
	public void run()
	{
		while((line = readLine()) != null)
		{
			Lib.printAndLog(line);
		}
	}
	
	public String readLine()
	{
		try {
			line = in.readLine();
		} catch (IOException e) {
			Lib.error("Cant read line from InputStream.",e.toString());
		}
		return line;
	}
}

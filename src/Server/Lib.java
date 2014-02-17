package Server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

/*
 * Contains lots of functions for the server
 */
public class Lib {
	private static ServerSocket server = null;
	private static ArrayList<User> users = new ArrayList<User>();
	private static ArrayList<String[]> settings = new ArrayList<String[]>();
	
	public static void loadSettings()
	{
		/*
		 * Tries to load settings from file, if file does not exist it creates one with default settings
		 */
		ArrayList<String> settingsfile = null;
		String servername = "Server";
		int port = 10000;
		boolean notFail = true;
		while(notFail)
		{
			try {
				settingsfile = readFromFile("./settings.cfg");
				notFail = false;
			} catch (FileNotFoundException e) {
				createFile("settings.cfg");
				writeToFile("settings.cfg","Servername: Server");
				writeToFile("settings.cfg", "Port: 10000");
			}
		}
		/*
		 * Chops up the pure-text settingsfile so that the variables can be used.
		 * Only cut at the first whitespace to allow whitespaces in MOT and Servername and such
		 */
		for(String line : settingsfile)
		{
			settings.add(line.split("\\s+",2));
		}
		//servername = settings.get(0)[1];
		//port = Integer.parseInt(settings.get(1)[1]);
		Settings.setSettings(settings.get(0)[1], Integer.parseInt(settings.get(1)[1]));

		Lib.printAndLog("Servername: " + servername + "\nPort: "+port);
	}
	
	public static ArrayList<String> readFromFile(String fileURL) throws FileNotFoundException
	{
		/*
		 * Method to read text from any file specified, used by the LoadSettings-method.
		 * Returns an array with every row in the textfile.
		 */
		InputStream inputstream = null;
		BufferedReader buffreader = null;
		ArrayList<String> file = new ArrayList<String>();
		String line = null;
		
		print("Reading from " + fileURL);
		Path path = Paths.get(fileURL);
		inputstream = new FileInputStream(path.toString());
		buffreader = new BufferedReader(new InputStreamReader(inputstream, Charset.forName("UTF-8")));
		
		/*
		 * As long as there is lines in the file keep reading
		 */
		try {
			while((line = buffreader.readLine()) != null)
			{
				file.add(line);
			}
		} catch (IOException e) {
			error("Could not read File.",e.toString());
		}
		return file;
	}
	
	public static void writeToFile(String file, String text)
	{
		/*
		 * Method to write to any file specified. Primarly used by the Log-method.
		 */
		FileWriter writer = null;
		
			try {
				writer = new FileWriter(file, true);
			} catch (IOException e) {
				error("Could not create filewriter.",e.toString());
			}
			
			/*
			 * Writes whatever was specified when calling the method and also a lineSeperator.
			 */
			try {
				writer.write(text + System.lineSeparator());
			} catch (IOException e) {
				error("Could not write to file", e.toString());
			}
			
			try {
				writer.close();
			} catch (IOException e) {
				error("Could not close file.", e.toString());
			}
			
	}
	
	public static void createFile(String fileName)
	{
		/*
		 * Creates file
		 */
		new File("./", fileName);
	}
	
	public static void print(String text)
	{
		/*
		 * Prints out text to console, can be changed to print to GUI if needed
		 */
		System.out.println(text);
	}
	
	public static void log(String text)
	{
		/*
		 * Gets the time/date and prints that and the text specified to error.log
		 */
		Date date = new Date();
		//print(date.toString() + " " + text);
		writeToFile("error.log", date.toString() + " " + text);
	}
	
	public static void printAndLog(String text)
	{
		/*
		 * This method both logs and prints a text
		 */
		print(text);
		log(text);
	}
	
	public static void error(String text, String errorMessage)
	{
		/*
		 * Displays an error via the System.err method in the console
		 */
		String message =  text + " Errormessage: " + errorMessage;
		System.err.print(message);
		log(message);
	}
	
	public static void startServer()
	{
		/*
		 * Notifies the user that the server has started and at what port, then creates a new server-object
		 */
		printAndLog("Starting Server at port " + settings.get(1)[1]);
		try {
			server = new ServerSocket(Settings.getPort());
		} catch (IOException e) {
			error("Could not create ServerSocket.", e.toString());
		}
	}
	
	public static void stopServer()
	{
		/*
		 * Shuts the server down
		 */
		try {
			server.close();
		} catch (IOException e) {
			error("Could not close ServerSocket",e.toString());
		}
	}
	
	public static void runServer()
	{
		/*
		 * This starts the server-object when it have been created.
		 * When someone connects to the server a User-object is born in the createUser-method
		 */
		boolean running = true;
		
		while(running)
		{
			System.out.println("Listening...");
			try {
				createUser(server.accept());
				System.out.println("User conected!");
			} catch (IOException e) {
				error("Could not create new user",e.toString());
			}
		}
	}
	
	public static void createUser(Socket socket)
	{
		/*
		 * Creates an user-object and starts it up.
		 */
		User user = new User(socket);
		users.add(user);
		user.start();
	}
}

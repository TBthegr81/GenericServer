package Server;

import java.util.ArrayList;

public class Settings {
	/*
	 * Settings-object that other methods in the package can get info from, like what port is in use and such.
	 */
	private static String Servername = null;
	private static int Port = 0;
	
	public static void setSettings(String inservername, int inport)
	{
		Servername = inservername;
		Port = inport;
	}
	
	public ArrayList<String> getSettings()
	{
		ArrayList<String> stuff = new ArrayList<String>();
		stuff.add(Servername);
		stuff.add(Port+"");
		
		return stuff;
	}
	
	public static int getPort()
	{
		return Port;
	}
	
	public static void setPort(int newPort)
	{
		Port = newPort;
	}
	
	public static String getServerName()
	{
		return Servername;
	}
	
	public static void setServerName(String newServername)
	{
		Servername = newServername;
	}
}

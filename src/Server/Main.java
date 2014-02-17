package Server;
/*
 * Generic-Server by Torbjörn "TB" Hagenstam
 * Multi-purpose Server, a base to build a service that acctually do something.
 * Have basic stuff like multithreads, errorlogging, reading settings from file implemented
 * but needs actuall server-code to use theese functions.
 * 
 * I made this after compleeting a group-project in school where i was responsible for the server-side
 * Wanted to make something more clean and better thought out and to train myself in back-end programing.
 * 
 * 2013
 */

public class Main {
	
	public static void main(String[] args) {
		/*
		 * Self-explanatory
		 */
		Lib.loadSettings();
		Lib.startServer();
		Lib.runServer();
		Lib.stopServer();
	}

}

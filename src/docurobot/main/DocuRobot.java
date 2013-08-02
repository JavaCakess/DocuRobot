package docurobot.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;


public class DocuRobot {

	public static String dataPath = "DATA/", spacesPath = "DATA/SPACES/", helpPath = "HELP/";
	
	static BufferedReader reader = null;
	static BufferedWriter writer = null;
	static Socket socket;
	static String server, channel;
	static Scanner s = new Scanner(System.in);
	static String nick = "DocuRobot", login = "DocuRobot 8 * : DocuRobot: Store your files in me.";
	public static void main(String[] args) {
		server = input("Enter server.");
		channel = input("Enter channel (include hashtag (#) please.)");

		try {
			/*
			 * Connect the socket to the server, port 6667.
			 */
			socket = new Socket(server, 6667);
			/*
			 * Initialize our BufferedReader and BufferedWriter.
			 */
			reader = new BufferedReader(
					new InputStreamReader(socket.getInputStream())
					);
			writer = new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream())
					);

			/*
			 * Next, we give the server our nick. 
			 */
			send("NICK " + nick + "\r\n");
			send("USER " + login + "\r\n");

			// Read lines from the server until it tells us we have connected.
			String line = null;

			while ((line = reader.readLine( )) != null) {
				if (line.startsWith("PING ")) {
					writer.write("PONG " + line.substring(5) + "\r\n");
					writer.flush();
				}
				if (line.indexOf("004") >= 0) {
					// We are now logged in.
					print("Logged in.");
					break;
				}else if (line.indexOf("433") >= 0) {
					print("Nickname is already in use.");
					return;
				}

			}

			/*
			 * Finally, we join the channel!
			 */
			send("JOIN " + channel + "\r\n");
		} catch (Exception e) {
			print("Error during initialization.");
			e.printStackTrace();
			System.exit(-1);
		}
		String line = "";
		try{
			while ((line = reader.readLine( )) != null) {

				if (line.startsWith("PING ")) {
					// We must respond to PINGs to avoid being disconnected.

					writer.write("PONG " + line.substring(5) + "\r\n");
					writer.flush( );
				}
				if (line.contains(":!dr")) {
					try {
						String cmd = line.substring(line.indexOf(":!dr") + 5);
						String user = line.substring(1, line.indexOf("!"));

						CommandHandler.handleCommand(line.substring(line.indexOf("PRIVMSG ") + "PRIVMSG ".length(), line.indexOf(" :")), line, user, cmd);
					}catch(Exception e){

					}
				}
				print("[" + time() + "]  <<  " + line);
			}
		}catch(Exception ioe) {

		}
	}

	public static String input() {
		return s.nextLine();
	}

	public static String input(String str) {
		print(str);
		return s.nextLine();
	}

	public static void print(String str) {
		System.out.println(str);

	}

	public static void send(String data) {
		try {
			writer.write(data);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String time() {
		Date d = new Date();
		int h = d.getHours();
		int m = d.getMinutes();
		int s = d.getSeconds();
		
		String hours = "" + h, minutes = "" + m, secs = "" + s;
		if (h < 10) {
			hours = "0" + h;
		}
		if (m < 10) {
			minutes = "0" + m;
		}
		if (s < 10) {
			secs = "0" + s;
		}
		return (hours + ":" + minutes + ":" + secs);
	}
	
	public static void send(String channel, String msg) {
		send("PRIVMSG " + channel + " :" + msg + "\r\n");
		print("[" + time() + "]  >>  [" + channel + "] " + msg);
	}
	public static void pm(String user, String msg) {
		send("PRIVMSG " + user + " :" + msg + "\r\n");
		print("[" + time() + "]  >>  [" + user + "] " + msg);
	}
}

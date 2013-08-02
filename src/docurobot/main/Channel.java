package docurobot.main;

import java.util.ArrayList;

public class Channel {

	String name;
	ArrayList<String> users = new ArrayList<String>();
	
	public Channel(String name) {
		this.name = name;
	}
	
	public void add(String user) {
		users.add(user);
	}
	
	public void remove(String user) {
		if (users.contains(user)) {
			users.remove(user);
		}
	}
}

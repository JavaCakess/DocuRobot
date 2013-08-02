package docurobot.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import docurobot.tools.IOTools;

public class Command {

	public static void help(String string) {
		/*
		 * Load the file into an arraylist.
		 */
		File help = new File(DocuRobot.helpPath + "help_find.txt");
		ArrayList<String> helpFile = IOTools.readFile(help);
		ArrayList<String> suggestedCommands = new ArrayList<String>();
		/*
		 * Try and find that command.
		 */
		for (String v : helpFile) {
			String[] args = v.split(" ");
			if (args[0].equalsIgnoreCase(string)) {
				/*
				 * We found it, so we send it to them and return.
				 */
				CommandHandler.send(v);
				return;
			}
			if (args[0].contains(string) || string.contains(args[0])) {
				suggestedCommands.add(args[0]);
			}
		}
		/*
		 * Otherwise we tell them we couldn't find the command...
		 * ... And list some suggested ones. (If there are any.)
		 */
		String suggested = "Command not found.";
		if (suggestedCommands.isEmpty()) {
			/*
			 * There are no suggested commands, so we return.
			 */
			CommandHandler.send(suggested);
			return;
		}
		/*
		 * Otherwise we continue.
		 * First append "Did you mean " to the string.
		 */
		suggested = suggested.concat(" Did you mean ");
		
		/*
		 * ... Then concatenate the suggested commands.
		 */
		for (int i = 0; i < suggestedCommands.size(); i++) {
			if (i == helpFile.size() - 1) {
				suggested = suggested.concat(suggestedCommands.get(i) + "?");
			} else {
				suggested = suggested.concat(suggestedCommands.get(i) + ", ");
			}
		}
		CommandHandler.send(suggested);
	}

	public static void help() {
		CommandHandler.send("Help: https://dl-web.dropbox.com/get/DocuRobot/help.html?w=AAB_kEEbDKmHzJ4aJVg-Tmug9OJDCH-MZW6RBxOD-pbwRg");
	}

	public static void join(String string) {
		if (!string.startsWith("#")) {
			CommandHandler.send("Channel must start with a hashtag! (#)");
			return;
		}
		/*
		 * Pretty self-explanatory. Joins a channel. 
		 */
		DocuRobot.send("JOIN " + string + "\r\n");
		DocuRobot.send(string, "Hi, I'm DocuRobot, vividMario52's bot. I was sent by " + CommandHandler._user + ".");
	}
	
	/**
	 * Create a new space.
	 * @param name : Name of the space.
	 * @param p0p : Whether it should be private or public.
	 */
	public static void crSpace(String name, String pOp) {
		File newSpace = new File(DocuRobot.spacesPath + name);
		File spaces_info = new File(DocuRobot.dataPath + "spaces_info.txt");
		/*
		 * We want to make sure it DOESN'T exist before creating it.
		 * We don't want to overwrite any data!
		 */
		if (newSpace.exists()) {
			CommandHandler.send("That space already exists!!");
			return;
		}
		
		/*
		 * Otherwise we'll go ahead and create it.
		 */
		try {
			newSpace.createNewFile();
		} catch (IOException e) {
			CommandHandler.send("Error creating space.");
			return;
		}
		
		
	}
}

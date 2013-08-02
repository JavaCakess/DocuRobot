package docurobot.main;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;

import docurobot.tools.DocuTools;
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
			if (i == suggestedCommands.size() - 1) {
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

		if (pOp != null) {
			/*
			 * Is the parameter pOp valid?
			 */
			if (!pOp.equalsIgnoreCase("public") && !pOp.equalsIgnoreCase("private")) {
				/*
				 * It's not, so we tell them it's not and return.
				 */
				CommandHandler.send("The space must be private or public.");
				return;
			}
		} else {
			pOp = "private";
		}

		/*
		 * Otherwise we'll go ahead and create it.
		 */
		newSpace.mkdir();

		/*
		 * Write to spaces_info.txt about the new space we just created.
		 */
		ArrayList<String> spaceInfo = IOTools.readFile(spaces_info);
		spaceInfo.add(name + " " + CommandHandler._user + " " + pOp);
		IOTools.writeToFile(spaces_info, spaceInfo);

		CommandHandler.send("Space " + name + " successfully created. Access level: " + pOp);
	}

	public static void space_info(String string) {
		File space = new File(DocuRobot.spacesPath + string);
		/*
		 * Of course, if the space doesn't exist, we
		 * tell the user and return.
		 */
		if (!space.exists()) {
			CommandHandler.send("That space doesn't exist!");
			return;
		}
		
		/*
		 * == Info  Variables ==
		 * 
		 * Get the kilobytes, owner, and access level of a space.
		 */
		double B = IOTools.folderSize(space);
		int div = 1024;
		String str2 = "kB";
		if (B < 1024) {
			str2 = "B";
			div = 1;
		} else if (B < 1048576) {
			str2 = "kB";
			div = 1024;
		} else {
			str2 = "mB";
			div = 1048576;
		}
		String owner = DocuTools.getOwnerOfSpace(string);
		String access_level = DocuTools.getAccLevelOfSpace(string);
		
		CommandHandler.send("Owner: " + owner + ", Size: " + IOTools.roundDouble(B / div, 4) + str2 + ", Access Level: " + access_level);
	}
}

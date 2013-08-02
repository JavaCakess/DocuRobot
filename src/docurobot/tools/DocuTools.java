package docurobot.tools;

import java.io.File;
import java.util.ArrayList;

import docurobot.main.DocuRobot;

public class DocuTools {
	
	public static String getOwnerOfSpace(String space) {
		File f = new File(DocuRobot.dataPath + "spaces_info.txt");
		ArrayList<String> spaces_info = IOTools.readFile(f);
		for (String s : spaces_info) {
			String[] args = s.split(" ");
			if (args[0].equals(space)) {
				return args[1];
			}
		}
		return "";
	}

	public static String getAccLevelOfSpace(String space) {
		File f = new File(DocuRobot.dataPath + "spaces_info.txt");
		ArrayList<String> spaces_info = IOTools.readFile(f);
		for (String s : spaces_info) {
			String[] args = s.split(" ");
			if (args[0].equals(space)) {
				return args[2];
			}
		}
		return "";
	}
}

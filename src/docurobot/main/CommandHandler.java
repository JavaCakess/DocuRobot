package docurobot.main;


public class CommandHandler {

	public static String _line, _user, _channel;
	public static String[] _args;
	public static void handleCommand(String channel, String line, String user, String cmd) {
		_line = line; _user = user; _channel = channel;
		String[] args = cmd.toLowerCase().split(" ");
		_args = args;
		String command = args[0];
		switch (command) {
		case "help":
			/*
			 * If there aren't any parameters, we will just show them the help file link.
			 */
			if (NEP(1)) {
				Command.help();
				return;
			}
			/*
			 * Or we will search for the command they want help on. 
			 */
			Command.help(args[1]);
			break;
		case "join":
			if (NEP(1)) {
				return;
			}
			Command.join(args[1]);
			break;
		}
	}
	
	public static void send(String msg) {
		
		if (_line.toLowerCase().contains("privmsg docurobot :")) {
			DocuRobot.pm(_user, msg);
		} else {
			DocuRobot.send(_channel, msg);
		}
	}
	
	public static boolean NEP(int am) {
		if (_args.length - 1 < am) {
			return true;
		}
		return false;
	}
}

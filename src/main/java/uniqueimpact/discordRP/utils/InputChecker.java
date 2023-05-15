package uniqueimpact.discordRP.utils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputChecker {
	
	public static boolean validName(String input) {
		return (input.length() >= 1 && input.length() <= 32);
	}
	
	public static boolean validDisplayName(String input) {
		return (input.length() >= 1 && input.length() <= 32);
	}
	
	public static boolean validDiscordID(String input) {
		if (input.length() >= 18 && input.length() <= 20) {
			Pattern pattern = Pattern.compile("^[0-9]*$");
			Matcher matcher = pattern.matcher(input);
			return matcher.find();
		}
		return false;
	}
	
	public static boolean validDescription(String input) {
		return (input.length() <= 1500);
	}
	
	public static boolean validKey(String input) {
		return (input.length() <= 32);
	}

}

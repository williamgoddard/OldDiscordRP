package uniqueimpact.discordRP.utils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputChecker {
	
	public static boolean validName(String input) {
		if (input.length() >= 1 && input.length() <= 40) {
			Pattern pattern = Pattern.compile("^[a-zA-Z0-9_-]*$");
			Matcher matcher = pattern.matcher(input);
			return matcher.find();
		}
		return false;
	}
	
	public static boolean validDisplayName(String input) {
		if (input.length() >= 1 && input.length() <= 32) {
			Pattern pattern = Pattern.compile("^[ a-zA-Z0-9_-]*$");
			Matcher matcher = pattern.matcher(input);
			return matcher.find();
		}
		return false;
	}
	
	public static boolean validDiscordID(String input) {
		if (input.length() >= 18 && input.length() <= 19) {
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
		if (input.length() <= 40) {
			Pattern pattern = Pattern.compile("^[a-zA-Z1-9_-]*$");
			Matcher matcher = pattern.matcher(input);
			return matcher.find();
		}
		return false;
	}

}

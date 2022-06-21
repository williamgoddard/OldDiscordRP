package uniqueimpact.discordRP.discord;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class DiscordRunner {
	
	private static final Scanner SCANNER = new Scanner(System.in);
	
	public static void runDiscordBot() {
		String botToken;
		try {
			File myFile = new File("token.txt");
			Scanner myReader = new Scanner(myFile);
			botToken = myReader.nextLine();
			myReader.close();
		} catch (FileNotFoundException | NoSuchElementException e) {
			System.out.println("Please enter bot token:");
			botToken = SCANNER.nextLine();
			File myFile = new File("token.txt");
			try {
				myFile.createNewFile();
				FileWriter myWriter = new FileWriter("token.txt");
				myWriter.write(botToken);
				myWriter.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	    }
		try {
			JDA jda = JDABuilder.createDefault(botToken)
					.enableIntents(GatewayIntent.GUILD_MEMBERS)
					.setMemberCachePolicy(MemberCachePolicy.ALL)
					.setChunkingFilter(ChunkingFilter.ALL)
					.build();
			jda.addEventListener(new MessageEvent());
		} catch (LoginException e) {}
	}

}

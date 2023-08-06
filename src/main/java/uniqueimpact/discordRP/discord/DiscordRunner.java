package uniqueimpact.discordRP.discord;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import uniqueimpact.discordRP.discord.listeners.InteractionListener;
import uniqueimpact.discordRP.discord.listeners.MessageListener;
import uniqueimpact.discordRP.discord.listeners.ReadyListener;
import uniqueimpact.discordRP.discord.utils.CommandSetup;

public class DiscordRunner {
	
	private static final Scanner SCANNER = new Scanner(System.in);
	
	public static void runDiscordBot() throws InterruptedException {

		// Get the Bot Token
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

		// Create the builder
		JDABuilder builder = JDABuilder.createDefault(botToken)
				.enableIntents(GatewayIntent.MESSAGE_CONTENT);

		// Add event listeners to the builder
		builder.addEventListeners(
				new MessageListener(),
				new ReadyListener(),
				new InteractionListener());

		// Create and start the bot
		JDA bot = builder.build();
		bot.awaitReady();

		// Set up the commands
		CommandSetup.addCommands(bot);

	}

}

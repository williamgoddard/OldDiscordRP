package uniqueimpact.discordRP.discord;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import uniqueimpact.discordRP.things.Player;
import uniqueimpact.discordRP.things.Roleplay;
import uniqueimpact.discordRP.utils.InvalidInputException;

public class MessageEvent extends ListenerAdapter {
	
	private Roleplay roleplay = Roleplay.getInstance();

	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		
		if (!event.getAuthor().isBot()) {
			String messageSent = event.getMessage().getContentRaw();
			if (!(messageSent.isEmpty() || messageSent == null)) {
				if (messageSent.charAt(0) == '!') {
					CommandRunner.runCommand(messageSent, event);
				} else {
					try {
						Player player = roleplay.findPlayerByChannel(event.getChannel().getId());
						System.out.println(messageSent);
						String formattedMessage = "";
						for (int i = 0; i < messageSent.length(); i++) {
							char character = messageSent.charAt(i);
							switch (character) {
							case '\\':
								formattedMessage += "\\\\";
								break;
							case '\"':
								formattedMessage += "\\\"";
								break;
							case '\n':
								formattedMessage += "\n";
								break;
							default: formattedMessage += character;
							}
						}
						WebhookManager.sendOthers(formattedMessage, player);
					} catch (InvalidInputException e) {}
				}
			}
		}
		
	}
	
}

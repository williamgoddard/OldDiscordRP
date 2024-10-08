package uniqueimpact.discordRP.discord.listeners;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;
import uniqueimpact.discordRP.discord.utils.WebhookManager;
import uniqueimpact.discordRP.things.Chara;
import uniqueimpact.discordRP.things.Roleplay;
import uniqueimpact.discordRP.utils.InvalidInputException;

public class MessageListener implements EventListener {
	
	private Roleplay roleplay = Roleplay.getInstance();

	@Override
	public void onEvent(@NotNull GenericEvent event) {

		if (!(event instanceof MessageReceivedEvent)) {
			return;
		}

		MessageReceivedEvent messageEvent = (MessageReceivedEvent) event;
		if (messageEvent.getAuthor().isBot()) {
			return;
		}

		String message = messageEvent.getMessage().getContentRaw();
		if (message.isEmpty() || message == null || message.charAt(0) == '!') {
			return;
		}

		Chara player;
		try {
			player = roleplay.findCharacterByChannel(messageEvent.getChannel().getId());
		} catch (InvalidInputException e) {
			return;
		}

		String formattedMessage = "";
		for (int i = 0; i < message.length(); i++) {
			char character = message.charAt(i);
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

		if (formattedMessage.length() < 2000) {
			WebhookManager.sendOthers(formattedMessage, player);
		} else {
			WebhookManager.sendOthers(formattedMessage.substring(0, 2000), player);
			WebhookManager.sendOthers(formattedMessage.substring(2000), player);
		}

	}
}

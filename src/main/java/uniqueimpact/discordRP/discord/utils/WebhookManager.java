package uniqueimpact.discordRP.discord.utils;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import net.dv8tion.jda.api.entities.Webhook;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import uniqueimpact.discordRP.things.Player;
import uniqueimpact.discordRP.things.Room;

import java.util.List;

public class WebhookManager {

	private static final String BOT_ID = "870388601861591090";

	public static String createOrGetWebhook(TextChannel channel) {
		List<Webhook> webhooks = channel.retrieveWebhooks().complete();
		for (Webhook webhook : webhooks) {
			if (webhook.getOwner().getId().equals(BOT_ID)) {
				return webhook.getUrl();
			}
		}
		Webhook webhook = channel.createWebhook("Polyanna").complete();
		return webhook.getUrl();
	}

	public static void deleteWebhook(TextChannel channel) {
		List<Webhook> webhooks = channel.retrieveWebhooks().complete();
		for (Webhook webhook : webhooks) {
			if (webhook.getOwner().getId().equals(BOT_ID)) {
				channel.deleteWebhookById(webhook.getId()).complete();
			}
		}
	}
	
	public static void sendSelf(String message, Player player) {
		
		String webhook = player.getWebhook();
		String name = player.getDisplayName();
		String picture = player.getPicture();
		
		WebhookClient client = WebhookClient.withUrl(webhook);
		
		WebhookMessageBuilder builder = new WebhookMessageBuilder();
		builder.setUsername(name);
		builder.setAvatarUrl(picture);
		builder.setContent(message);
		
		client.send(builder.build());
		client.close();
		
	}
	
	public static void sendAll(String message, Player player) {
		
		Room room = player.getRoom();
		String name = player.getDisplayName();
		String picture = player.getPicture();
		
		WebhookMessageBuilder builder = new WebhookMessageBuilder();
		builder.setUsername(name);
		builder.setAvatarUrl(picture);
		builder.setContent(message);
		
		for (Player toPlayer : room.getPlayers()) {
			
			String webhook = toPlayer.getWebhook();
			
			WebhookClient client = WebhookClient.withUrl(webhook);
			
			client.send(builder.build());
			client.close();
			
		}
	}
	
	public static void sendOthers(String message, Player player) {
		
		Room room = player.getRoom();
		String name = player.getDisplayName();
		String picture = player.getPicture();
		
		WebhookMessageBuilder builder = new WebhookMessageBuilder();
		builder.setUsername(name);
		builder.setAvatarUrl(picture);
		builder.setContent(message);
		
		for (Player toPlayer : room.getPlayers()) {
			
			if (player != toPlayer) {
			
				String webhook = toPlayer.getWebhook();
				
				WebhookClient client = WebhookClient.withUrl(webhook);
				
				client.send(builder.build());
				client.close();
				
			}
			
		}

	}

}

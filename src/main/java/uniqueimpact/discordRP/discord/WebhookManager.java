package uniqueimpact.discordRP.discord;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import uniqueimpact.discordRP.things.Player;
import uniqueimpact.discordRP.things.Room;

public class WebhookManager {
	
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

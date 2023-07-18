package uniqueimpact.discordRP.discord.utils;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import net.dv8tion.jda.api.entities.Webhook;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import uniqueimpact.discordRP.things.Chara;
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

	public static void send(String message, Chara fromChara, Chara toChara) {

		String webhook = toChara.getWebhook();
		String name = fromChara.getDisplayName();
		String picture = fromChara.getPicture();

		WebhookClient client = WebhookClient.withUrl(webhook);

		WebhookMessageBuilder builder = new WebhookMessageBuilder();
		builder.setUsername(name);
		builder.setAvatarUrl(picture);
		builder.setContent(message);

		client.send(builder.build());
		client.close();

	}
	
	public static void sendSelf(String message, Chara player) {
		
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
	
	public static void sendAll(String message, Chara chara) {
		
		Room room = chara.getRoom();
		String name = chara.getDisplayName();
		String picture = chara.getPicture();
		
		WebhookMessageBuilder builder = new WebhookMessageBuilder();
		builder.setUsername(name);
		builder.setAvatarUrl(picture);
		builder.setContent(message);
		
		for (Chara toPlayer : room.getCharacters()) {
			
			String webhook = toPlayer.getWebhook();
			
			WebhookClient client = WebhookClient.withUrl(webhook);
			
			client.send(builder.build());
			client.close();
			
		}
	}
	
	public static void sendOthers(String message, Chara chara) {
		
		Room room = chara.getRoom();
		String name = chara.getDisplayName();
		String picture = chara.getPicture();
		
		WebhookMessageBuilder builder = new WebhookMessageBuilder();
		builder.setUsername(name);
		builder.setAvatarUrl(picture);
		builder.setContent(message);
		
		for (Chara toChara : room.getCharacters()) {
			
			if (chara != toChara) {
			
				String webhook = toChara.getWebhook();
				
				WebhookClient client = WebhookClient.withUrl(webhook);
				
				client.send(builder.build());
				client.close();
				
			}
			
		}

	}

}

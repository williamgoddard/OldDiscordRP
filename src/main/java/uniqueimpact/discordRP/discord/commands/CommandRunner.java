package uniqueimpact.discordRP.discord.commands;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.dv8tion.jda.api.entities.Role;
import uniqueimpact.discordRP.discord.utils.DiscordOutputGenerator;
import uniqueimpact.discordRP.discord.utils.WebhookManager;
import uniqueimpact.discordRP.things.Door;
import uniqueimpact.discordRP.things.Inventory;
import uniqueimpact.discordRP.things.Item;
import uniqueimpact.discordRP.things.Player;
import uniqueimpact.discordRP.things.Roleplay;
import uniqueimpact.discordRP.things.Room;
import uniqueimpact.discordRP.utils.InvalidInputException;

@Deprecated
public class CommandRunner {
	
	private static Random random = new Random();
	private static Roleplay roleplay = Roleplay.getInstance();
	
	private static void commandTime(TextChannel channel) {
		LocalTime localTime = LocalTime.now(ZoneId.of("UTC-3"));
		int hour = localTime.getHour();
		int minute = localTime.getMinute();
		String time = ((hour < 10) ? "0" : "") + hour + ":" + ((minute < 10) ? "0" : "") + minute;
		channel.sendMessage("The time is currently `" + time + "`").queue();
	}
	
	private static void commandSetDesc(String[] command, TextChannel channel) {
		try {
			String channelId = channel.getId();
			Player player = roleplay.findPlayerByChannel(channelId);
			if (command.length >= 2) {
				String desc = command[1];
				for (int i = 2; i < command.length; i++) {
					desc += " " + command[i];
				}
				player.setDescription(desc);
				channel.sendMessage("Your description has been set successfully.").queue();
			} else {
				player.setDescription("");
				channel.sendMessage("Your description has been cleared successfully.").queue();
			}
		} catch (InvalidInputException e) {
			channel.sendMessage(e.getMessage()).queue();
		}
	}
	
	private static void commandSetPicture(String[] command, TextChannel channel) {
		try {
			String channelId = channel.getId();
			Player player = roleplay.findPlayerByChannel(channelId);
			if (command.length >= 2) {
				player.setPicture(command[1]);
				channel.sendMessage("Your picture has been set successfully.").queue();
			} else {
				player.setPicture("");
				channel.sendMessage("Your picture has been cleared successfully.").queue();
			}
		}  catch (InvalidInputException e) {
			channel.sendMessage(e.getMessage()).queue();
		}
	}

	private static void commandLockDoor(String[] command, TextChannel channel) {
		try {
			if (command.length >= 2) {
				String channelId = channel.getId();
				Player player = roleplay.findPlayerByChannel(channelId);
				Inventory inv = player.getInv();
				List<Item> items = inv.getItems();
				Room room = player.getRoom();
				String doorString = command[1];
				if (command.length >= 3) {
					doorString += "#" + command[2];
				}
				Door door = roleplay.findSpecificRoomDoor(room, doorString, false, false);
				for (int i = 0; i < items.size(); i++) {
					Item item = items.get(i);
					if (!item.getKey().equals("") && item.getKey().equals(door.getLock())) {
						door.setLocked(true);
						WebhookManager.sendSelf("*I lock the door to the " + command[1] + " with the " + item.getName() + ".*", player);
						WebhookManager.sendOthers("*" + player.getDisplayName() + " locks the door to the " + command[1] + " with their " + item.getName() + ".*", player);
						return;
					}
				}
				WebhookManager.sendSelf("*I don't have anything that can lock the door to the " + command[1] + ".*", player);
			} else {
				channel.sendMessage("Invalid command format: The correct format is `!lockdoor <door> [num]`").queue();
			}
		} catch (InvalidInputException e) {
			channel.sendMessage(e.getMessage()).queue();
		}
	}
	
	private static void commandUnlockDoor(String[] command, TextChannel channel) {
		try {
			if (command.length >= 2) {
				String channelId = channel.getId();
				Player player = roleplay.findPlayerByChannel(channelId);
				Inventory inv = player.getInv();
				List<Item> items = inv.getItems();
				Room room = player.getRoom();
				String doorString = command[1];
				if (command.length >= 3) {
					doorString += "#" + command[2];
				}
				Door door = roleplay.findSpecificRoomDoor(room, doorString, true, false);
				for (int i = 0; i < items.size(); i++) {
					Item item = items.get(i);
					if (!item.getKey().equals("") && item.getKey().equals(door.getLock())) {
						door.setLocked(false);
						WebhookManager.sendSelf("*I unlock the door to the " + command[1] + " with the " + item.getName() + ".*", player);
						WebhookManager.sendOthers("*" + player.getDisplayName() + " unlocks the door to the " + command[1] + " with their " + item.getName() + ".*", player);
						return;
					}
				}
				WebhookManager.sendSelf("*I don't have anything that can unlock the door to the " + command[1] + ".*", player);
			} else {
				channel.sendMessage("Invalid command format: The correct format is `!unlockdoor <door> [num]`").queue();
			}
		} catch (InvalidInputException e) {
			channel.sendMessage(e.getMessage()).queue();
		}
	}
	
	private static void commandDragAll(String[] command, TextChannel channel, boolean isHost) {
		if (isHost) {
			if (command.length >= 2) {
				try {
					List<Player> players = roleplay.getPlayers();
					Room toRoom = roleplay.findRoom(command[1]);
					for (Player player : players) {
						Room fromRoom = player.getRoom();
						toRoom.getPlayers().add(player);
						fromRoom.getPlayers().remove(player);
						player.setRoom(toRoom);
					}
					channel.sendMessage("All players were moved successfully.").queue();
				} catch (InvalidInputException e) {
					channel.sendMessage(e.getMessage()).queue();
				}
			} else {
				channel.sendMessage("Invalid command format: The correct format is `!drag <player> <room>`").queue();
			}
		} else {
			channel.sendMessage("You must have the Host role to use this command.").queue();
		}
	}

}

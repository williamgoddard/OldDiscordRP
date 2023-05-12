package uniqueimpact.discordRP.discord.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uniqueimpact.discordRP.things.Door;
import uniqueimpact.discordRP.things.Item;
import uniqueimpact.discordRP.things.Player;
import uniqueimpact.discordRP.things.Room;
import uniqueimpact.discordRP.utils.InvalidInputException;

public class DiscordOutputGenerator {
	
	public static String convertItem(Item item) {
		return "Item Name: `" + item.getName() + "`, " +
				"Weight: `" + item.getWeight() + "`, " +
				"Takeable: `" +item.isTakeable() + "`, " +
				"Wearable: `" + item.isWearable() + "`, " +
				"Infinite: `" +item.isInfinite() + "`\n" +
				item.getDescription();
	}
	
	public static String convertItemAdmin(Item item) {
		return "Item Name: `" + item.getName() + "`, " +
				"Weight: `" + item.getWeight() + "`, " +
				"Takeable: `" +item.isTakeable() + "`, " +
				"Wearable: `" + item.isWearable() + "`, " +
				"Infinite: `" +item.isInfinite() + "`, " +
				"Key: `" + ((item.getKey().equals("")) ? "none" : item.getKey()) + "`\n" +
				item.getDescription();
	}
	
	public static String convertItemList(List<Item> items, int charLimit) {
		Map<String, Integer> itemCounts = new HashMap<>();
		for (Item item : items) {
			itemCounts.put(item.getName(), itemCounts.getOrDefault(item.getName(), 0) + 1);
		}
		String output = "";
		for (String itemName : itemCounts.keySet()) {
			String addToOutput = "`" + itemName;
			if (itemCounts.get(itemName) > 1) {
				addToOutput += " (" + itemCounts.get(itemName) + ")";
			}
			addToOutput += "` ";
			if (output.length() + addToOutput.length() > charLimit) {
				output += "(and more) ";
				return output;
			} else {
				output += addToOutput;
			}
		}
		return output;
	}
	
	public static String convertPlayer(Player player) {
		return "Name: `" + player.getDisplayName() + "`\n" +
				player.getDescription();
	}
	
	public static String convertPlayerAdmin(Player player) {
		return "Name: `" + player.getDisplayName() + "`, " +
				"Hidden: `" + player.isHidden() + "`, " +
				"Max Items: `" + player.getInv().getCapacity() + "`, " +
				"Max Clothes: `" + player.getClothes().getCapacity() + "`, " +
				"Channel Id: `" + player.getChannel() + "`\n" +
				player.getDescription();
	}
	
	public static String convertPlayerList(List<Player> players, int charLimit) {
		Map<String, Integer> playerCounts = new HashMap<>();
		for (Player player : players) {
			playerCounts.put(player.getName(), playerCounts.getOrDefault(player.getName(), 0) + 1);
		}
		String output = "";
		for (String playerName : playerCounts.keySet()) {
			String addToOutput = "`" + playerName;
			if (playerCounts.get(playerName) > 1) {
				addToOutput += " (" + playerCounts.get(playerName) + ")";
			}
			addToOutput += "` ";
			if (output.length() + addToOutput.length() > charLimit) {
				output += "(and more) ";
				return output;
			} else {
				output += addToOutput;
			}
		}
		return output;
	}
	
	public static String convertDoorAdmin(Door door) {
		return "Room 1: `" + door.getRoom1() + "`, " +
				"Room 2: `" + door.getRoom2() + "`, " +
				"Hidden: `" + door.isHidden() + "`, " +
				"Locked: `" + door.isLocked() + "`, " +
				"Lock: `" + ((door.getLock().equals("")) ? "none" : door.getLock()) + "`";
	}
	
	public static String convertDoorList(List<Door> doors, Room currentRoom, int charLimit) throws InvalidInputException {
		String output = "";
		for (Door door : doors) {
			String addToOutput = "`";
			if (currentRoom == door.getRoom1()) {
				addToOutput += door.getRoom2().getName();
			} else {
				addToOutput += door.getRoom1().getName();
			}
			addToOutput += "` ";
			if (output.length() + addToOutput.length() > charLimit) {
				output += "(and more) ";
				return output;
			} else {
				output += addToOutput;
			}
		}
		return output;
	}
	
	public static String convertRoomList(List<Room> rooms, int charLimit) {
		Map<String, Integer> roomCounts = new HashMap<>();
		for (Room room : rooms) {
			roomCounts.put(room.getName(), roomCounts.getOrDefault(room.getName(), 0) + 1);
		}
		String output = "";
		for (String roomName : roomCounts.keySet()) {
			String addToOutput = "`" + roomName;
			if (roomCounts.get(roomName) > 1) {
				addToOutput += " (" + roomCounts.get(roomName) + ")";
			}
			addToOutput += "` ";
			if (output.length() + addToOutput.length() > charLimit) {
				output += "(and more) ";
				return output;
			} else {
				output += addToOutput;
			}
		}
		return output;
	}
	
	public static String convertRoomAdmin(Room room) {
		return "Name: `" + room.getName() + "`, " +
				"Capacity: `" + room.getInv().getCapacity() + "`\n" +
				room.getDescription();
	}
	
}

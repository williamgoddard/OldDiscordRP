package uniqueimpact.discordRP.discord.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uniqueimpact.discordRP.things.Door;
import uniqueimpact.discordRP.things.Item;
import uniqueimpact.discordRP.things.Chara;
import uniqueimpact.discordRP.things.Room;

public class DiscordOutputGenerator {
	
	public static String convertItem(Item item) {
		return "Item Name: `" + item.getName() + "`, " +
				"Weight: `" + item.getWeight() + "`, " +
				"Quantity: `" + item.getQuantity() + "`, " +
				"Takeable: `" +item.isTakeable() + "`, " +
				"Wearable: `" + item.isWearable() + "`, " +
				"Infinite: `" +item.isInfinite() + "`\n" +
				item.getDescription();
	}
	
	public static String convertItemAdmin(Item item) {
		return "Item Name: `" + item.getName() + "`, " +
				"Weight: `" + item.getWeight() + "`, " +
				"Quantity: `" + item.getQuantity() + "`, " +
				"Takeable: `" +item.isTakeable() + "`, " +
				"Wearable: `" + item.isWearable() + "`, " +
				"Infinite: `" +item.isInfinite() + "`, " +
				"Keyword: `" + (item.getKeyword() == null ? "none" : item.getKeyword()) + "`\n" +
				item.getDescription();
	}
	
	public static String convertItemList(List<Item> items, int charLimit) {
		if (items.size() == 0) {
			return "None";
		}

		String output = "";

		for (Item item : items) {
			String addToOutput = "`" + item.getName();
			if (item.isInfinite()) {
				addToOutput += " (∞)";
			} else if (item.getQuantity() > 1) {
				addToOutput += " (" + item.getQuantity() + ")";
			}
			addToOutput += "` ";
			if (output.length() + addToOutput.length() > charLimit) {
				output += "(and more)";
				return output;
			} else {
				output += addToOutput;
			}
		}

		return output;

	}
	
	public static String convertPlayer(Chara player) {
		return "Name: `" + player.getDisplayName() + "`\n" +
				player.getDescription();
	}
	
	public static String convertPlayerAdmin(Chara player) {
		return "Name: `" + player.getDisplayName() + "`, " +
				"Hidden: `" + player.isHidden() + "`, " +
				"Frozen: `" + player.isFrozen() + "`, " +
				"NPC: `" + player.isNpc() + "`, " +
				"Max Items: `" + player.getInv().getCapacity() + "`, " +
				"Max Clothes: `" + player.getClothes().getCapacity() + "`, " +
				"Channel Id: `" + player.getChannel() + "`\n" +
				player.getDescription();
	}
	
	public static String convertCharaList(List<Chara> charas, int charLimit) {

		if (charas.size() == 0) {
			return "None";
		}

		String output = "";

		for (Chara chara : charas) {
			String addToOutput = "`" + chara.getName() + "` ";
			if (output.length() + addToOutput.length() > charLimit) {
				output += "(and more)";
				return output;
			} else {
				output += addToOutput;
			}
		}

		return output;

	}
	
	public static String convertDoorAdmin(Door door) {
		return "Room 1: `" + door.getRoom1().getName() + "`, " +
				"Room 2: `" + door.getRoom2().getName() + "`, " +
				"Hidden: `" + door.isHidden() + "`, " +
				"Locked: `" + door.isLocked() + "`, " +
				"Keyword: `" + (door.getKeyword() == null ? "" : door.getKeyword()) + "`";
	}
	
	public static String convertDoorList(List<Door> doors, Room currentRoom, int charLimit) {
		if (doors.size() == 0) {
			return "None";
		}
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

		if (rooms.size() == 0) {
			return "None";
		}

		String output = "";

		for (Room room : rooms) {
			String addToOutput = "`" + room.getName() + "` ";
			if (output.length() + addToOutput.length() > charLimit) {
				output += "(and more)";
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

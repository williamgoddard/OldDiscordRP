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
	
	public static void runCommand(String command) {
		
		boolean isHost = false;
		for (Role role : event.getMember().getRoles()) {
			if (role.getName().toLowerCase().equals("host")) {
				isHost = true;
			}
		}
		
		TextChannel channel = event.getChannel();
		String[] commandSplit = command.substring(1).split(" ");
		
		switch (commandSplit[0]) {
		
		// Information Commands
		case "help":
			commandHelp(commandSplit, channel);
			break;
		case "time":
			commandTime(channel);
			break;
			
		// Character Customisation Commands
		case "setdesc":
			commandSetDesc(commandSplit, channel);
			break;
		case "setpicture":
			commandSetPicture(commandSplit, channel);
			break;
			
		// Inspection Commands
		case "lookroom": case "room": case "desc":
			commandDesc(channel);
			break;			
		case "lookclothes": case "clothes": case "c":
			commandLookClothes(commandSplit, channel);
			event.getMessage().delete().queue();
			break;
		case "lookinv": case "inv": case "i":
			commandLookInv(commandSplit, channel);
			event.getMessage().delete().queue();
			break;
		case "lookitem": case "items": case "item": case "look": case "l":
			commandLookItem(commandSplit, channel);
			event.getMessage().delete().queue();
			break;
		case "lookplayer": case "players": case "player": case "p":
			commandLookPlayer(commandSplit, channel);
			event.getMessage().delete().queue();
			break;
		
		// Item Movement Commands
		case "drop": case "d":
			commandDrop(commandSplit, channel);
			event.getMessage().delete().queue();
			break;
		case "take": case "t":
			commandTake(commandSplit, channel);
			event.getMessage().delete().queue();
			break;
		case "undress": case "u":
			commandUndress(commandSplit, channel);
			event.getMessage().delete().queue();
			break;
		case "wear": case "w":
			commandWear(commandSplit, channel);
			event.getMessage().delete().queue();
			break;
		case "undressdrop": case "ud":
			commandUndressDrop(commandSplit, channel);
			event.getMessage().delete().queue();
			break;
		case "takewear": case "tw":
			commandTakeWear(commandSplit, channel);
			event.getMessage().delete().queue();
			break;
			
		// Movement Commands
		case "goto": case "move": case "go": case "doors":
			event.getMessage().delete().queue();
			commandGoto(commandSplit, channel);
			break;
		case "lockdoor": case "lock":
			commandLockDoor(commandSplit, channel);
			event.getMessage().delete().queue();
			break;
		case "unlockdoor": case "unlock":
			commandUnlockDoor(commandSplit, channel);
			event.getMessage().delete().queue();
			break;
		
		// Admin Information Commands
		case "adminhelp":
			commandAdminHelp(channel, isHost);
			break;
			
		// Admin Item Commands
		case "additem":
			commandAddItem(commandSplit, channel, isHost);
			break;
		case "copyitem":
			commandCopyItem(commandSplit, channel, isHost);
			break;
		case "delitem":
			commandDelItem(commandSplit, channel, isHost);
			break;
		case "edititem":
			commandEditItem(commandSplit, channel, isHost);
			break;
		case "listitems":
			commandListItems(commandSplit, channel, isHost);
			break;
		case "moveitem":
			commandMoveItem(commandSplit, channel, isHost);
			break;
		case "seeitem":
			commandSeeItem(commandSplit, channel, isHost);
			break;
		
		// Admin Player Commands
		case "addplayer":
			commandAddPlayer(commandSplit, channel, isHost);
			break;
		case "delplayer":
			commandDelPlayer(commandSplit, channel, isHost);
			break;
		case "editplayer":
			commandEditPlayer(commandSplit, channel, isHost);
			break;
		case "listplayers":
			commandListPlayers(commandSplit, channel, isHost);
			break;
		case "seeplayer":
			commandSeePlayer(commandSplit, channel, isHost);
			break;
		
		// Admin Room Commands
		case "addroom":
			commandAddRoom(commandSplit, channel, isHost);
			break;
		case "delroom":
			commandDelRoom(commandSplit, channel, isHost);
			break;
		case "editroom":
			commandEditRoom(commandSplit, channel, isHost);
			break;
		case "listrooms":
			commandListRooms(channel, isHost);
			break;
		case "seeroom":
			commandSeeRoom(commandSplit, channel, isHost);
			break;
		
		// Admin Door Commands
		case "adddoor":
			commandAddDoor(commandSplit, channel, isHost);
			break;
		case "deldoor":
			commandDelDoor(commandSplit, channel, isHost);
			break;
		case "editdoor":
			commandEditDoor(commandSplit, channel, isHost);
			break;
		case "listdoors":
			commandListDoors(commandSplit, channel, isHost);
			break;
		case "seedoor":
			commandSeeDoor(commandSplit, channel, isHost);
			break;
			
		// Admin Drag Commands
		case "drag":
			commandDrag(commandSplit, channel, isHost);
			break;
		case "dragall":
			commandDragAll(commandSplit, channel, isHost);
			break;
		
		// Secret Commands
		case "secret":
			channel.sendMessage("yep you did it well done you found a secret command").queue();
			break;
		case "unique":
			channel.sendMessage("Please subscribe! https://www.youtube.com/user/TheUniqueImpact").queue();
			break;
		case "traitor":
			channel.sendMessage("The real traitor is the friends we make along the way...").queue();
			break;
		case "mastermind":
			channel.sendMessage("Psst... if you run this command over and over it'll eventually tell you who the mastermind is!").queue();
			break;
		case "nibbles":
			channel.sendMessage("https://media.discordapp.net/attachments/226490521520963584/800151579386183720/Nibbles.png").queue();
			break;
		case "spoilers":
			channel.sendMessage("https://m.media-amazon.com/images/I/51xdQEDj1tL._AC_SL1001_.jpg").queue();
			break;
		case "refrigerator":
			channel.sendMessage("how the heck did you find this one?").queue();
			break;
		case "joke":
			channel.sendMessage("knock knock").queue();
			break;
		case "whosthere": case "whoisthere":
			channel.sendMessage("doctor").queue();
			break;
		case "doctorwho":
			channel.sendMessage("The fact that Russell T Davies is returning to Doctor Who to kick out (the frankly useless) Chris Chibnall fills me with great joy and satisfaction. RTD is a very reliable writer, and of course, was the one responsible for the fantastic revival of the show in the first place, so who better to replace Chibbs and hopefully return the show to its former glory?").queue();
			break;
		case "monkey":
			channel.sendMessage("Mmmmm... Monke.").queue();
			break;
		case "arcade":
			channel.sendMessage("\"Maybe we can't all change the world. Maybe it's enough just to do good for the short time that we're here.\" - Arcade Gannon, 2281 CE");
			break;
		case "69":
			channel.sendMessage("Nice").queue();
			break;
			
		// Fun Commands
		case "8ball":
			command8Ball(channel);
			break;
		case "choose": case "random":
			commandChoose(commandSplit, channel);
			break;
		case "excuse":
			commandExcuse(commandSplit, channel);
			break;
		case "roll": case "dice":
			commandRoll(commandSplit, channel);
			break;
			
		default:
			if (isHost) {
				channel.sendMessage("Command not found. Type `!help` for a list of player commands, or `!adminhelp` for a list of admin commands.").queue();
			} else {
				channel.sendMessage("Command not found. Type `!help` for a list of commands.").queue();
			}
		}
		
		roleplay.save();
		
	}

	private static void commandHelp(String[] command, TextChannel channel) {
		if (command.length >= 2) {
			switch (command[1]) {
			
				// Information Commands
				case "help":
					channel.sendMessage("`!help [command]` Lists all of the player commands, or gives details about a specific command.\n"
							+ "`[command]` - The command you want details about. If left blank, all available commands are listed instead.").queue();
					break;
				case "time":
					channel.sendMessage("`!time` Gives the current in-game time.").queue();
					break;
					
				// Character Customisation Commands
				case "setdesc":
					channel.sendMessage("`!setdesc [desc]` Sets your character's description, which other players can see with `!lookplayer`.\n"
							+ "`[desc]` - The new description for your character. If left blank, it will clear your character's description.").queue();
					break;
				case "setpicture":
					channel.sendMessage("`!setpicture [url]` Sets your character's profile picture.\n"
							+ "`[url]` - The URL of your character's new picture. If left blank, it will clear your character's picture.").queue();
					break;
					
				// Inspection Commands
				case "lookroom": case "room": case "desc":
					channel.sendMessage("`!lookroom|room|desc` Gives the description of the room you are currently in.").queue();
					break;			
				case "lookclothes": case "clothes": case "c":
					channel.sendMessage("`!lookclothes|clothes|c [item] [num]` Lists all of the clothes you are wearing, or gives details about a specific clothing item.\n"
							+ "`[item]` - The name of the item you want details about. If left blank, all of your character's clothes are listed.\n"
							+ "`[num]` - The number of the item you want details about, in case there are multiple items with the same name. If left blank, the first item is selected automatically.").queue();
					break;
				case "lookinv": case "inv": case "i":
					channel.sendMessage("`!lookinv|inv|i [item] [num]` Lists all of the items you are holding, or gives details about a specific item.\n"
							+ "`[item]` - The name of the item you want details about. If left blank, all of your character's items are listed.\n"
							+ "`[num]` - The number of the item you want details about, in case there are multiple items with the same name. If left blank, the first item is selected automatically.").queue();
					break;
				case "lookitem": case "items": case "item": case "look": case "l":
					channel.sendMessage("`!lookitem|items|item|look|l [item] [num]` Lists all of the items in the room you are in, or gives details about a specific item.\n"
							+ "`[item]` - The name of the item you want details about. If left blank, all of the room's items are listed.\n"
							+ "`[num]` - The number of the item you want details about, in case there are multiple items with the same name. If left blank, the first item is selected automatically.").queue();
					break;
				case "lookplayer": case "players": case "player": case "p":
					channel.sendMessage("`!looklookplayer|players|player|p [player] [num]` Lists all of the players in the room you are in, or gives details about a specific player.\n"
							+ "`[player]` - The name of the player you want details about. If left blank, all of the players in the room are listed.\n"
							+ "`[num]` - The number of the player you want details about, in case there are multiple players with the same name. If left blank, the first player is selected automatically.").queue();
					break;
				
				// Item Movement Commands
				case "drop": case "d":
					channel.sendMessage("`!drop|d <item> [num]` Drop an item from your inventory into the room.\n"
							+ "`<item>` - The name of the item you want to drop.\n"
							+ "`[num]` - The number of the item you want to drop, in case there are multiple items with the same name. If left blank, the first item is selected automatically.").queue();
					break;
				case "take": case "t":
					channel.sendMessage("`!take|t <item> [num]` Take an item from the room.\n"
							+ "`<item>` - The name of the item you want to take.\n"
							+ "`[num]` - The number of the item you want to take, in case there are multiple items with the same name. If left blank, the first item is selected automatically.").queue();
					break;
				case "undress": case "u":
					channel.sendMessage("`!undress|u <item> [num]` Remove an item of clothing, and put it into your inventory.\n"
							+ "`<item>` - The name of the item you want to undress.\n"
							+ "`[num]` - The number of the item you want to undress, in case there are multiple items with the same name. If left blank, the first item is selected automatically.").queue();
					break;
				case "wear": case "w":
					channel.sendMessage("`!wear|w <item> [num]` Wear an item of clothing from your inventory.\n"
							+ "`<item>` - The name of the item you want to wear.\n"
							+ "`[num]` - The number of the item you want to wear, in case there are multiple items with the same name. If left blank, the first item is selected automatically.").queue();
					break;
				case "undressdrop": case "ud":
					channel.sendMessage("`!undressdrop|ud <item> [num]` Remove an item of clothing, and drop it into the room.\n"
							+ "`<item>` - The name of the item you want to undress and drop.\n"
							+ "`[num]` - The number of the item you want to undress and drop, in case there are multiple items with the same name. If left blank, the first item is selected automatically.").queue();
					break;
				case "takewear": case "tw":
					channel.sendMessage("`!takewear|tw <item> [num]` Take an item from the room and wear it.\n"
							+ "`<item>` - The name of the item you want to take and wear.\n"
							+ "`[num]` - The number of the item you want to take and wear, in case there are multiple items with the same name. If left blank, the first item is selected automatically.").queue();
					break;
					
				// Movement Commands
				case "goto": case "move": case "go": case "doors":
					channel.sendMessage("`!goto|move|doors|go [room] [num]` Move to another room, or list all of the rooms you can currently go to.\n"
							+ "`[room]` - The name of the room you want to go to. If left blank, all of the room's doors are listed.\n"
							+ "`[num]` - The number of the room you want to go to, in case there are multiple rooms with the same name. If left blank, the first room is selected automatically.").queue();
					break;
				case "lockdoor": case "lock":
					channel.sendMessage("`!lockdoor|lock <door> [num]` Lock a door in the room you are currently in. You must have an appropriate key item to do this.\n"
							+ "`<door>` - The name of the door you want to lock. This will be the name of the room on the other side of the door. Use `!doors` for help.\n"
							+ "`[num]` - The number of the door you want to lock, in case there are multiple doors with the same name. If left blank, the first door is selected automatically.").queue();
					break;
				case "unlockdoor": case "unlock":
					channel.sendMessage("`!unlockdoor|unlock <door> [num]` Unlock a door in the room you are currently in. You must have an appropriate key item to do this.\n"
							+ "`<door>` - The name of the door you want to unlock. This will be the name of the room on the other side of the door. Use `!doors` for help.\n"
							+ "`[num]` - The number of the door you want to unlock, in case there are multiple doors with the same name. If left blank, the first door is selected automatically.").queue();
					break;
					
				// Fun Commands
				case "8ball":
					channel.sendMessage("`!8ball [question]` Ask the almighty magic 8 ball a question, and it will bestow unto you the divine truth...\n"
							+ "`[question]` - The question you wish to ask the almighty magic 8 ball.").queue();
					break;
				case "choose": case "random":
					channel.sendMessage("`!choose|random <options>` Make a random selection between 2 or more options.\n"
							+ "`<options>` - At least 2 options, separated by spaces, to choose between.").queue();
					break;
				case "excuse":
					channel.sendMessage("`!excuse [event]` Need a quick excuse for literally anything? Then use this command, and experience perfect results!\n"
							+ "`[event]` - The event for which you need an excuse. Example: \"I couldn't do my homework\"").queue();
					break;
				case "roll": case "dice":
					channel.sendMessage("`!roll|dice [size] [num]` Roll some good old fashioned dice to generate some delightfully random numbers!\n"
							+ "`[size]` - The number of sides on the dice. Maximum 1000.\n"
							+ "`[num]` - The number of dice to roll. Maximum 100.").queue();
					break;
					
				default:
					channel.sendMessage("The command you wish for help with was not found. Type '!help' for a list of commands.").queue();	
			}
					
		} else {
			channel.sendMessage("```!help [command]\r\n"
					+ "!time\r\n"
					+ "!setdesc [desc]\r\n"
					+ "!setpicture [url]\r\n"
					+ "\r\n"
					+ "!lookroom|room|desc\r\n"
					+ "!lookclothes|clothes|c [item] [num]\r\n"
					+ "!lookinv|inv|i [item] [num]\r\n"
					+ "!lookitem|items|item|look|l [item] [num]\r\n"
					+ "!lookplayer|players|player|p [player] [num]\r\n"
					+ "\r\n"
					+ "!drop|d <item> [num]\r\n"
					+ "!take|t <item> [num]\r\n"
					+ "!undress|u <item> [num]\r\n"
					+ "!wear|w <item> [num]\r\n"
					+ "!undressdrop|ud <item> [num]\r\n"
					+ "!takewear|tw <item> [num]\r\n"
					+ "\r\n"
					+ "!goto|move|doors|go [room] [num]\r\n"
					+ "!lockdoor|lock <door> [num]\r\n"
					+ "!unlockdoor|unlock <door> [num]\r\n"
					+ "\r\n"
					+ "!8ball [question]\r\n"
					+ "!choose|random <options>\r\n"
					+ "!roll|dice [size] [num]\r\n"
					+ "!excuse [event]```"
					).queue();
		}
		
	}
	
	private static void commandTime(TextChannel channel) {
		LocalTime localTime = LocalTime.now(ZoneId.of("UTC-3"));
		int hour = localTime.getHour();
		int minute = localTime.getMinute();
		String time = ((hour < 10) ? "0" : "") + hour + ":" + ((minute < 10) ? "0" : "") + minute;
		channel.sendMessage("The time is currently `" + time + "`").queue();
	}
	
	private static void commandDesc(TextChannel channel) {
		try {
			String channelId = channel.getId();
			Player player = roleplay.findPlayerByChannel(channelId);
			Room room = player.getRoom();
			channel.sendMessage("You are in the `" + room.getName() + "`.\n" + room.getDescription()).queue();
		} catch (InvalidInputException e) {
			channel.sendMessage(e.getMessage()).queue();
		}
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
	
	private static void commandLookClothes(String[] command, TextChannel channel) {
		try {
			String channelId = channel.getId();
			Player player = roleplay.findPlayerByChannel(channelId);
			if (command.length == 1) {
				Inventory clothesInv = player.getClothes();
				List<Item> clothes = clothesInv.getItems();
				if (clothes.size() > 0) {
					WebhookManager.sendSelf("*I am currently wearing these clothes:*\n" + DiscordOutputGenerator.convertItemList(clothes, 1800), player);
				} else {
					WebhookManager.sendSelf("*I am currently not wearing much...*", player);
				}
			} else {
				String itemString = command[1];
				if (command.length >= 3) {
					itemString += "#" + command[2];
				}
				Inventory clothesInv = player.getClothes();
				Item item = roleplay.findInvItem(clothesInv, itemString);
				WebhookManager.sendSelf("*I examine my " + item.getName() + "*.\n" + DiscordOutputGenerator.convertItem(item), player);
			}
		} catch (InvalidInputException e) {
			channel.sendMessage(e.getMessage()).queue();
		}
	}
	
	private static void commandLookInv(String[] command, TextChannel channel) {
		try {
			String channelId = channel.getId();
			Player player = roleplay.findPlayerByChannel(channelId);
			if (command.length == 1) {
				Inventory inv = player.getInv();
				List<Item> items = inv.getItems();
				if (items.size() > 0) {
					WebhookManager.sendSelf("*I am currently holding these items:*\n" + DiscordOutputGenerator.convertItemList(items, 1800), player);
				} else {
					WebhookManager.sendSelf("*I am currently not holding anything.*", player);
				}
			} else {
				String itemString = command[1];
				if (command.length >= 3) {
					itemString += "#" + command[2];
				}
				Inventory inv = player.getInv();
				Item item = roleplay.findInvItem(inv, itemString);
				WebhookManager.sendSelf("*I examine the " + item.getName() + "*.\n" + DiscordOutputGenerator.convertItem(item), player);
			}
		} catch (InvalidInputException e) {
			channel.sendMessage(e.getMessage()).queue();
		}
	}
	
	private static void commandLookItem(String[] command, TextChannel channel) {
		try {
			String channelId = channel.getId();
			Player player = roleplay.findPlayerByChannel(channelId);
			Room room = player.getRoom();
			Inventory inventory = room.getInv();
			if (command.length == 1) {
				List<Item> items = inventory.getItems();
				List<Item> tItems = new ArrayList<>();
				List<Item> iItems = new ArrayList<>();
				List<Item> uItems = new ArrayList<>();
				for (Item item : items) {
					if (!item.isTakeable()) {
						uItems.add(item);
					} else if (item.isInfinite()) {
						iItems.add(item);
					} else {
						tItems.add(item);
					}
				}
				String outputString = "*I take a look around...*\n";
				if (tItems.size() > 0) {
					outputString += "*I find these items:*\n" + DiscordOutputGenerator.convertItemList(tItems, 1000) + "\n";
				} else {
					outputString += "*I don't find any items here.*\n";
				}
				if (iItems.size() > 0) {
					outputString += "*I find these infinite items:*\n" + DiscordOutputGenerator.convertItemList(iItems, 400) + "\n";
				}
				if (uItems.size() > 0) {
					outputString += "*I find these objects:*\n" + DiscordOutputGenerator.convertItemList(uItems, 400);
				} else {
					outputString += "*I don't find any objects here.*";
				}
				WebhookManager.sendSelf(outputString, player);
			} else {
				String itemString = command[1];
				if (command.length >= 3) {
					itemString += "#" + command[2];
				}
				Item item = roleplay.findInvItem(inventory, itemString);
				WebhookManager.sendSelf("*I examine the " + item.getName() + "*.\n" + DiscordOutputGenerator.convertItem(item), player);
			}
		} catch (InvalidInputException e) {
			channel.sendMessage(e.getMessage()).queue();
		}
	}
	
	private static void commandLookPlayer(String[] command, TextChannel channel) {
		try {
			String channelId = channel.getId();
			Player player = roleplay.findPlayerByChannel(channelId);
			Room room = player.getRoom();
			if (command.length == 1) {
				List<Player> players = room.getPlayers(false);
				if (players.size() > 1) {
					WebhookManager.sendSelf("*I see these people here:*\n" + DiscordOutputGenerator.convertPlayerList(players, 1800), player);
				} else {
					WebhookManager.sendSelf("*I don't see anyone else here.*", player);
				}
			} else {
				String playerString = command[1];
				if (command.length >= 3) {
					playerString += "#" + command[2];
				}
				Player targetPlayer = roleplay.findRoomPlayer(room, playerString, false);
				WebhookManager.sendSelf("*I look at " + targetPlayer.getDisplayName() + ".*\n" + DiscordOutputGenerator.convertPlayer(targetPlayer), player);
				Inventory targetClothesInv = targetPlayer.getClothes();
				List<Item> targetClothes = targetClothesInv.getItems();
				if (targetClothes.size() > 0) {
					WebhookManager.sendSelf("*" + targetPlayer.getDisplayName() + " is currently wearing these clothes:*\n" + DiscordOutputGenerator.convertItemList(targetClothes, 1800), player);
				} else {
					WebhookManager.sendSelf("*" + targetPlayer.getDisplayName() + " is currently not wearing much...*", player);
				}
			}
		} catch (InvalidInputException e) {
			channel.sendMessage(e.getMessage()).queue();
		}
	}
	
	private static void commandDrop(String[] command, TextChannel channel) {
		try {
			if (command.length >= 2) {
				String channelId = channel.getId();
				Player player = roleplay.findPlayerByChannel(channelId);
				Room room = player.getRoom();
				Inventory fromInv = player.getInv();
				Inventory toInv = room.getInv();
				String itemString = command[1];
				if (command.length >= 3) {
					itemString += "#" + command[2];
				}
				Item item = roleplay.findInvItem(fromInv, itemString);
				if (toInv.getRemainingCapacity() >= item.getWeight()) {
					fromInv.getItems().remove(item);
					toInv.getItems().add(item);
					WebhookManager.sendSelf("*I drop the " + item.getName() + ".*", player);
					WebhookManager.sendOthers("*" + player.getDisplayName() + " drops their " + item.getName() + ".*" , player);
				} else { 
					WebhookManager.sendSelf("*I can't drop the " + item.getName() + " because the room is too full.*", player);
				}
			} else {
				channel.sendMessage("Invalid command format: The correct format is `!drop <item> [num]`").queue();
			}
		} catch (InvalidInputException e) {
			channel.sendMessage(e.getMessage()).queue();
		}
	}
	
	private static void commandTake(String[] command, TextChannel channel) {
		try {
			if (command.length >= 2) {
				String channelId = channel.getId();
				Player player = roleplay.findPlayerByChannel(channelId);
				Room room = player.getRoom();
				Inventory fromInv = room.getInv();
				Inventory toInv = player.getInv();
				String itemString = command[1];
				if (command.length >= 3) {
					itemString += "#" + command[2];
				}
				Item item = roleplay.findInvItem(fromInv, itemString);
				if (item.isTakeable()) {
					if (toInv.getRemainingCapacity() >= item.getWeight()) {
						if (item.isInfinite()) {
							Item newItem = item.getCopy();
							newItem.setInfinite(false);
							toInv.getItems().add(newItem);
							if (item.getName().toUpperCase().startsWith("A") ||
									item.getName().toUpperCase().startsWith("E") ||
									item.getName().toUpperCase().startsWith("I") ||
									item.getName().toUpperCase().startsWith("O") ||
									item.getName().toUpperCase().startsWith("U")) {
								WebhookManager.sendSelf("*I take an " + item.getName() + ".*", player);
								WebhookManager.sendOthers("*" + player.getDisplayName() + " takes an " + item.getName() + ".*" , player);
							} else {
								WebhookManager.sendSelf("*I take a " + item.getName() + ".*", player);
								WebhookManager.sendOthers("*" + player.getDisplayName() + " takes a " + item.getName() + ".*" , player);
							}
						} else {
							fromInv.getItems().remove(item);
							toInv.getItems().add(item);
							WebhookManager.sendSelf("*I take the " + item.getName() + ".*", player);
							WebhookManager.sendOthers("*" + player.getDisplayName() + " takes the " + item.getName() + ".*" , player);
						}
					} else { 
						WebhookManager.sendSelf("*I can't take the " + item.getName() + " because I would be holding too much.*", player);
					}
				} else {
					WebhookManager.sendSelf("*I can't take the " + item.getName() + ".*", player);
				}
			} else {
				channel.sendMessage("Invalid command format: The correct format is `!take <item> [num]`").queue();
			}
		} catch (InvalidInputException e) {
			channel.sendMessage(e.getMessage()).queue();
		}
	}
	
	private static void commandTakeWear(String[] command, TextChannel channel) {
		try {
			if (command.length >= 2) {
				String channelId = channel.getId();
				Player player = roleplay.findPlayerByChannel(channelId);
				Room room = player.getRoom();
				Inventory fromInv = room.getInv();
				Inventory toInv = player.getClothes();
				String itemString = command[1];
				if (command.length >= 3) {
					itemString += "#" + command[2];
				}
				Item item = roleplay.findInvItem(fromInv, itemString);
				if (item.isTakeable()) {
					if (item.isWearable()) {
						if (toInv.getRemainingCapacity() >= item.getWeight()) {
							if (item.isInfinite()) {
								toInv.getItems().add(item.getCopy());
								if (item.getName().toUpperCase().startsWith("A") ||
										item.getName().toUpperCase().startsWith("E") ||
										item.getName().toUpperCase().startsWith("I") ||
										item.getName().toUpperCase().startsWith("O") ||
										item.getName().toUpperCase().startsWith("U")) {
									WebhookManager.sendSelf("*I take and put on an " + item.getName() + ".*", player);
									WebhookManager.sendOthers("*" + player.getDisplayName() + " takes and puts on an " + item.getName() + ".*" , player);
								} else {
									WebhookManager.sendSelf("*I take and put on a " + item.getName() + ".*", player);
									WebhookManager.sendOthers("*" + player.getDisplayName() + " takes and puts on a " + item.getName() + ".*" , player);
								}
							} else {
								fromInv.getItems().remove(item);
								toInv.getItems().add(item);
								WebhookManager.sendSelf("*I take and put on the " + item.getName() + ".*", player);
								WebhookManager.sendOthers("*" + player.getDisplayName() + " takes and puts on the " + item.getName() + ".*" , player);
							}
						} else { 
							WebhookManager.sendSelf("*I can't wear the " + item.getName() + " because I would be wearing too much.*", player);
						}
					} else {
						WebhookManager.sendSelf("*I can't wear the " + item.getName() + ".*", player);
					}
				} else {
					WebhookManager.sendSelf("*I can't take the " + item.getName() + ".*", player);
				}
			} else {
				channel.sendMessage("Invalid command format: The correct format is `!takewear <item> [num]`").queue();
			}
		} catch (InvalidInputException e) {
			channel.sendMessage(e.getMessage()).queue();
		}
	}
	
	private static void commandUndress(String[] command, TextChannel channel) {
		try {
			if (command.length >= 2) {
				String channelId = channel.getId();
				Player player = roleplay.findPlayerByChannel(channelId);
				Inventory fromInv = player.getClothes();
				Inventory toInv = player.getInv();
				String itemString = command[1];
				if (command.length >= 3) {
					itemString += "#" + command[2];
				}
				Item item = roleplay.findInvItem(fromInv, itemString);
				if (toInv.getRemainingCapacity() >= item.getWeight()) {
					fromInv.getItems().remove(item);
					toInv.getItems().add(item);
					WebhookManager.sendSelf("*I take off my " + item.getName() + ".*", player);
					WebhookManager.sendOthers("*" + player.getDisplayName() + " takes off their " + item.getName() + ".*" , player);
				} else { 
					WebhookManager.sendSelf("*I can't take off my " + item.getName() + " because I would be holding too much.*", player);
				}
			} else {
				channel.sendMessage("Invalid command format: The correct format is `!undress <item> [num]`").queue();
			}
		} catch (InvalidInputException e) {
			channel.sendMessage(e.getMessage()).queue();
		}
	}
	
	private static void commandUndressDrop(String[] command, TextChannel channel) {
		try {
			if (command.length >= 2) {
				String channelId = channel.getId();
				Player player = roleplay.findPlayerByChannel(channelId);
				Room room = player.getRoom();
				Inventory fromInv = player.getClothes();
				Inventory toInv = room.getInv();
				String itemString = command[1];
				if (command.length >= 3) {
					itemString += "#" + command[2];
				}
				Item item = roleplay.findInvItem(fromInv, itemString);
				if (toInv.getRemainingCapacity() >= item.getWeight()) {
					fromInv.getItems().remove(item);
					toInv.getItems().add(item);
					WebhookManager.sendSelf("*I take off and drop my " + item.getName() + ".*", player);
					WebhookManager.sendOthers("*" + player.getDisplayName() + " takes off and drops their " + item.getName() + ".*" , player);
				} else { 
					WebhookManager.sendSelf("*I can't take off and drop my " + item.getName() + " because the room is too full.*", player);
				}
			} else {
				channel.sendMessage("Invalid command format: The correct format is `!undressdrop <item> [num]`").queue();
			}
		} catch (InvalidInputException e) {
			channel.sendMessage(e.getMessage()).queue();
		}
	}
	
	private static void commandWear(String[] command, TextChannel channel) {
		try {
			if (command.length >= 2) {
				String channelId = channel.getId();
				Player player = roleplay.findPlayerByChannel(channelId);
				Inventory fromInv = player.getInv();
				Inventory toInv = player.getClothes();
				String itemString = command[1];
				if (command.length >= 3) {
					itemString += "#" + command[2];
				}
				Item item = roleplay.findInvItem(fromInv, itemString);
				if (item.isWearable()) {
					if (toInv.getRemainingCapacity() >= item.getWeight()) {
						fromInv.getItems().remove(item);
						toInv.getItems().add(item);
						WebhookManager.sendSelf("*I put on the " + item.getName() + ".*", player);
						WebhookManager.sendOthers("*" + player.getDisplayName() + " puts on their " + item.getName() + ".*" , player);
					} else { 
						WebhookManager.sendSelf("*I can't put on the " + item.getName() + " because I would be wearing too much.*", player);
					}
				} else {
					WebhookManager.sendSelf("*I can't wear the " + item.getName() + ".*", player);
				}
			} else {
				channel.sendMessage("Invalid command format: The correct format is `!wear <item> [num]`").queue();
			}
		} catch (InvalidInputException e) {
			channel.sendMessage(e.getMessage()).queue();
		}
	}
	
	private static void commandGoto(String[] command, TextChannel channel) {
		try {
			String channelId = channel.getId();
			Player player = roleplay.findPlayerByChannel(channelId);
			Room room = player.getRoom();
			if (command.length == 1) {
				List<Door> uDoors = room.getSpecificDoors(false, false);
				List<Door> lDoors = room.getSpecificDoors(true, false);
				String outputString = "*I look for places I can go.*\n";
				if (uDoors.size() > 0) {
					outputString += "*I can go to these places from here:*\n" + DiscordOutputGenerator.convertDoorList(uDoors, room, 1000) + "\n";
				} else {
					outputString += "*I can't go anywhere from here.*\n";
				}
				if (lDoors.size() > 0) {
					outputString += "*The doors to these places are locked:*\n" + DiscordOutputGenerator.convertDoorList(lDoors, room, 800);
				}
				WebhookManager.sendSelf(outputString, player);
			} else {
				String doorString = command[1];
				if (command.length >= 3) {
					doorString += "#" + command[2];
				}
				Door door = roleplay.findSpecificRoomDoor(room, doorString, false, false);
				Room targetRoom = door.getOtherRoom(room);
				WebhookManager.sendOthers("*" + player.getDisplayName() + " leaves to the " + targetRoom.getName() + ".*", player);
				player.setRoom(targetRoom);
				room.getPlayers().remove(player);
				targetRoom.getPlayers().add(player);
				WebhookManager.sendOthers("*" + player.getDisplayName() + " enters from the " + room.getName() + ".*", player);
				List<Player> players = targetRoom.getPlayers(false);
				if (players.size() > 1) {
					WebhookManager.sendSelf("*I go to the " + targetRoom.getName() + ". I see these people here:*\n" + DiscordOutputGenerator.convertPlayerList(players, 1800), player);
				} else {
					WebhookManager.sendSelf("*I go to the " + targetRoom.getName() + ". I don't see anyone else here.*", player);
				}
			}
		} catch (InvalidInputException e) {
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
	
	private static void commandAdminHelp(TextChannel channel, boolean isHost) {
		if (isHost) {
			channel.sendMessage("```!adminhelp\r\n"
					+ "\r\n"
					+ "!additem <inv> <name> <weight> <takeable> <wearable> <infinite> <key> [desc]\r\n"
					+ "!copyitem <item> [inv]\r\n"
					+ "!delitem <item>\r\n"
					+ "!edititem <item> <parameter> [value]\r\n"
					+ "!listitems <inv>\r\n"
					+ "!moveitem <item> <inv>\r\n"
					+ "!seeitem <item>\r\n"
					+ "\r\n"
					+ "!addplayer <channel> <name> <gender> <height> <weight> <max-items> <max-clothes> <room> <webhook> [desc]\r\n"
					+ "!delplayer <player>\r\n"
					+ "!editplayer <player> <parameter> [value]\r\n"
					+ "!listplayers [room]\r\n"
					+ "!seeplayer <player>\r\n"
					+ "\r\n"
					+ "!addroom <channel> <name> <max-items> [desc]\r\n"
					+ "!delroom <room>\r\n"
					+ "!editroom <room> <parameter> <value>\r\n"
					+ "!listrooms\r\n"
					+ "!seeroom <room>\r\n"
					+ "\r\n"
					+ "!adddoor <room1> <room2> <hidden> <locked> [lock]\r\n"
					+ "!deldoor <door>\r\n"
					+ "!editdoor <door> <paramter> [value]\r\n"
					+ "!listdoors <room>\r\n"
					+ "!seedoor <door>\r\n"
					+ "\r\n"
					+ "!drag <player> <room>\r\n"
					+ "!dragall <room>```").queue();
		} else {
			channel.sendMessage("You must have the Host role to use this command.").queue();
		}
	}
	
	private static void commandAddItem(String[] command, TextChannel channel, boolean isHost) {
		if (isHost) {
			if (command.length >= 8) {
				Inventory inv;
				try {
					inv = roleplay.findInventory(command[1]);
				} catch (InvalidInputException e) {
					channel.sendMessage(e.getMessage()).queue();
					return;
				}
				String name = command[2];
				double weight;
				try {
					weight = Double.parseDouble(command[3]);
				} catch (NumberFormatException e) {
					channel.sendMessage("Item weight must be a number.").queue();
					return;
				}
				if (!(command[4].equals("true") || command[4].equals("false"))) {
					channel.sendMessage("Takeable must be either true or false.").queue();
					return;
				}
				boolean takeable = Boolean.parseBoolean(command[4]);
				if (!(command[5].equals("true") || command[5].equals("false"))) {
					channel.sendMessage("Wearable must be either true or false.").queue();
					return;
				}
				boolean wearable = Boolean.parseBoolean(command[5]);
				if (!(command[6].equals("true") || command[6].equals("false"))) {
					channel.sendMessage("Infinite must be either true or false.").queue();
					return;
				}
				boolean infinite = Boolean.parseBoolean(command[6]);
				String key = (command[7].equals("none")) ? "" : command[7];
				String desc = "";
				if (command.length >= 9) {
					desc = command[8];
					for (int i = 9; i < command.length; i++) {
						desc += " " + command[i];
					}
				}
				Item item;
				try {
					item = new Item(name, desc, weight, takeable, wearable, infinite, key);
					inv.getItems().add(item);
					channel.sendMessage("The item was added successfully.").queue();
				} catch (InvalidInputException e) {
					channel.sendMessage(e.getMessage()).queue();
				}
			} else {
				channel.sendMessage("Invalid command format: The correct format is `!additem <inv> <name> <weight> <takeable> <wearable> <infinite> <key> [desc]`").queue();
			}
		} else {
			channel.sendMessage("You must have the Host role to use this command.").queue();
		}
	}
	
	private static void commandCopyItem(String[] command, TextChannel channel, boolean isHost) {
		if (isHost) {
			if (command.length >= 2) {
				try {
					Item item = roleplay.findItem(command[1]);
					Inventory inv;
					if (command.length >= 3) {
						inv = roleplay.findInventory(command[2]);
					} else {
						inv = roleplay.findInventoryByItemString(command[1]);
					}
					inv.getItems().add(item.getCopy());
					channel.sendMessage("The item was copied successfully.").queue();
				} catch (InvalidInputException e) {
					channel.sendMessage(e.getMessage()).queue();
				}
			} else {
				channel.sendMessage("Invalid command format: The correct format is `!copyitem <item> [inv]`").queue();
			}
		} else {
			channel.sendMessage("You must have the Host role to use this command.").queue();
		}
	}
	
	private static void commandDelItem(String[] command, TextChannel channel, boolean isHost) {
		if (isHost) {
			if (command.length >= 2) {
				try {
					Item item = roleplay.findItem(command[1]);
					Inventory inv = roleplay.findInventoryByItemString(command[1]);
					inv.getItems().remove(item);
					channel.sendMessage("The item was deleted successfully.").queue();
				} catch (InvalidInputException e) {
					channel.sendMessage(e.getMessage()).queue();
				}
			} else {
				channel.sendMessage("Invalid command format: The correct format is `!delitem <item>`").queue();
			}
		} else {
			channel.sendMessage("You must have the Host role to use this command.").queue();
		}
	}
	
	private static void commandEditItem(String[] command, TextChannel channel, boolean isHost) {
		if (isHost) {
			if (command.length >= 3) {
				try {
					Item item = roleplay.findItem(command[1]);
					switch (command[2]) {
					case "name":
						if (command.length >= 4) {
							item.setName(command[3]);
						} else {
							channel.sendMessage("When the parameter is `name`, you must enter a value.").queue();
							return;
						}
						break;
					case "weight":
						if (command.length >= 4) {
							try {
								item.setWeight(Double.parseDouble(command[3]));
							} catch (NumberFormatException e) {
								channel.sendMessage("Item weight must be a number.").queue();
								return;
							}
						} else {
							channel.sendMessage("When the parameter is `weight`, you must enter a value.").queue();
							return;
						}
						break;
					case "takeable":
						if (command.length >= 4) {
							if (!(command[3].equals("true") || command[3].equals("false"))) {
								channel.sendMessage("Takeable must be either true or false.").queue();
								return;
							}
							item.setTakeable(Boolean.parseBoolean(command[3]));
						} else {
							channel.sendMessage("When the parameter is `takeable`, you must enter a value.").queue();
							return;
						}
						break;
					case "wearable":
						if (command.length >= 4) {
							if (!(command[3].equals("true") || command[3].equals("false"))) {
								channel.sendMessage("Wearable must be either true or false.").queue();
								return;
							}
							item.setWearable(Boolean.parseBoolean(command[3]));
						} else {
							channel.sendMessage("When the parameter is `wearable`, you must enter a value.").queue();
							return;
						}
						break;
					case "infinite":
						if (command.length >= 4) {
							if (!(command[3].equals("true") || command[3].equals("false"))) {
								channel.sendMessage("Wearable must be either true or false.").queue();
								return;
							}
							item.setInfinite(Boolean.parseBoolean(command[3]));
						} else {
							channel.sendMessage("When the parameter is `wearable`, you must enter a value.").queue();
							return;
						}
						break;
					case "key":
						String key = "";
						if (command.length >= 4) {
							key = (command[3].equals("none")) ? "" : command[3];
						}
						item.setKey(key);
						break;
					case "desc":
						String desc = "";
						if (command.length >= 4) {
							desc = command[3];
							for (int i = 4; i < command.length; i++) {
								desc += " " + command[i];
							}
						}
						item.setDescription(desc);
						break;
					default:
						channel.sendMessage("Invalid parameter. Valid parameters are: `name` `weight` `takeable` `wearable` `infinite` `key` `desc`.").queue();
						return;
					}
					channel.sendMessage("The item was edited successfully.").queue();
				} catch (InvalidInputException e) {
					channel.sendMessage(e.getMessage()).queue();
				}
			} else {
				channel.sendMessage(("Invalid command format: The correct format is `!edititem <item> <parameter> [value]`")).queue();
			}
		} else {
			channel.sendMessage("You must have the Host role to use this command.").queue();
		}
	}
	
	private static void commandListItems(String[] command, TextChannel channel, boolean isHost) {
		if (isHost) {
			if (command.length >= 2) {
				try {
					Inventory inv = roleplay.findInventory(command[1]);
					channel.sendMessage("List of items in inventory `" + command[1] + "`:\n" + DiscordOutputGenerator.convertItemList(inv.getItems(), 1800)).queue();
				} catch (InvalidInputException e) {
					channel.sendMessage(e.getMessage()).queue();
				}
			} else {
				channel.sendMessage("Invalid command format: The correct format is `!listitems <inv>`").queue();
			}
		} else {
			channel.sendMessage("You must have the Host role to use this command.").queue();
		}
	}
	
	private static void commandMoveItem(String[] command, TextChannel channel, boolean isHost) {
		if (isHost) {
			if (command.length >= 2) {
				try {
					Item item = roleplay.findItem(command[1]);
					Inventory fromInv = roleplay.findInventoryByItemString(command[1]);
					Inventory toInv = roleplay.findInventory(command[2]);
					fromInv.getItems().remove(item);
					toInv.getItems().add(item);
					channel.sendMessage("The item was moved successfully.").queue();
				} catch (InvalidInputException e) {
					channel.sendMessage(e.getMessage()).queue();
				}
			} else {
				channel.sendMessage("Invalid command format: The correct format is `!moveitem <item> <inv>`").queue();
			}
		} else {
			channel.sendMessage("You must have the Host role to use this command.").queue();
		}
	}
	
	private static void commandSeeItem(String[] command, TextChannel channel, boolean isHost) {
		if (isHost) {
			if (command.length >= 2) {
				try {
					Item item = roleplay.findItem(command[1]);
					channel.sendMessage(DiscordOutputGenerator.convertItemAdmin(item)).queue();
				} catch (InvalidInputException e) {
					channel.sendMessage(e.getMessage()).queue();
				}
			} else {
				channel.sendMessage("Invalid command format: The correct format is `!seeitem <item>`").queue();
			}
		} else {
			channel.sendMessage("You must have the Host role to use this command.").queue();
		}
	}
	
	private static void commandAddPlayer(String[] command, TextChannel channel, boolean isHost) {
		if (isHost) {
			if (command.length >= 10) {
				String myChannel = command[1];
					try {
						roleplay.findPlayerByChannel(myChannel);
						channel.sendMessage("Discord channel is already linked to a player.").queue();
						return;
					} catch (InvalidInputException e) {}
				String name = command[2];
				String gender = command[3];
				int height;
				try {
					height = Integer.parseInt(command[4]);
				} catch (NumberFormatException e) {
					channel.sendMessage("Player height must be an integer.").queue();
					return;
				}
				int weight;
				try {
					weight = Integer.parseInt(command[5]);
				} catch (NumberFormatException e) {
					channel.sendMessage("Player weight must be an integer.").queue();
					return;
				}
				double maxItems;
				try {
					maxItems = Double.parseDouble(command[6]);
				} catch (NumberFormatException e) {
					channel.sendMessage("Max items capacity must be a number.").queue();
					return;
				}
				double maxClothes;
				try {
					maxClothes = Double.parseDouble(command[7]);
				} catch (NumberFormatException e) {
					channel.sendMessage("Max clothes capacity must be a number.").queue();
					return;
				}
				Room room;
				try {
					room = roleplay.findRoom(command[8]);
				} catch (InvalidInputException e) {
					channel.sendMessage(e.getMessage()).queue();
					return;
				}
				String webhook = command[9];
				String desc = "";
				if (command.length >= 11) {
					desc = command[10];
					for (int i = 11; i < command.length; i++) {
						desc += " " + command[i];
					}
				}
				try {
					Inventory itemsInv = new Inventory(maxItems);
					Inventory clothesInv = new Inventory(maxClothes);
					Player player = new Player(myChannel, webhook, name, name, "", desc, gender, height, weight, false, itemsInv, clothesInv, room);
					room.getPlayers().add(player);
					roleplay.getPlayers().add(player);
					channel.sendMessage("The player was added successfully.").queue();
				} catch (InvalidInputException e) {
					channel.sendMessage(e.getMessage()).queue();
				}
			} else {
				channel.sendMessage("Invalid command format: The correct format is `!addplayer <channel> <name> <gender> <height> <weight> <max-items> <max-clothes> <room> <webhook> [desc]`").queue();
			}
		} else {
			channel.sendMessage("You must have the Host role to use this command.").queue();
		}
	}
	
	private static void commandDelPlayer(String[] command, TextChannel channel, boolean isHost) {
		if (isHost) {
			if (command.length >= 2) {
				try {
					Player player = roleplay.findPlayer(command[1]);
					player.getRoom().getPlayers().remove(player);
					roleplay.getPlayers().remove(player);
					channel.sendMessage("The player was deleted successfully.").queue();
				} catch (InvalidInputException e) {
					channel.sendMessage(e.getMessage()).queue();
				}
			} else { 
				channel.sendMessage("Invalid command format: The correct format is `!delplayer <player>`").queue();
			}
		} else {
			channel.sendMessage("You must have the Host role to use this command.").queue();
		}
	}
	
	private static void commandEditPlayer(String[] command, TextChannel channel, boolean isHost) {
		if (isHost) {
			if (command.length >= 3) {
				try {
					Player player = roleplay.findPlayer(command[1]);
					switch (command[2]) {
					case "channel":
						if (command.length >= 4) {
							try {
								roleplay.findPlayerByChannel(command[3]);
								channel.sendMessage("Discord channel is already linked to a player.").queue();
								return;
							} catch (InvalidInputException e) {}
							player.setChannel(command[3]);
						} else {
							channel.sendMessage("When the parameter is `channel`, you must enter a value.").queue();
							return;
						}
						break;
					case "webhook":
						if (command.length >= 4) {
							player.setWebhook(command[3]);
						} else {
							channel.sendMessage("When the parameter is `webhook`, you must enter a value.").queue();
							return;
						}
						break;
					case "name":
						if (command.length >= 4) {
							player.setName((command[3].equals("none")) ? "" : command[3]);
						} else {
							channel.sendMessage("When the parameter is `name`, you must enter a value.").queue();
							return;
						}
						break;
					case "display-name":
						if (command.length >= 4) {
							String newDisplayName = command[3];
							for (int i = 4; i < command.length; i++) {
								newDisplayName += " " + command[i];
							}
							player.setDisplayName(newDisplayName);
						} else {
							channel.sendMessage("When the parameter is `display-name`, you must enter a value.").queue();
							return;
						}
						break;
					case "gender":
						if (command.length >= 4) {
							player.setGender(command[3]);
						} else {
							channel.sendMessage("When the parameter is `gender`, you must enter a value.").queue();
							return;
						}
						break;
					case "height":
						if (command.length >= 4) {
							int height;
							try {
								height = Integer.parseInt(command[3]);
							} catch (NumberFormatException e) {
								channel.sendMessage("Player height must be an integer.").queue();
								return;
							}
							player.setHeight(height);
						} else {
							channel.sendMessage("When the parameter is `height`, you must enter a value.").queue();
							return;
						}
						break;
					case "weight":
						if (command.length >= 4) {
							int weight;
							try {
								weight = Integer.parseInt(command[3]);
							} catch (NumberFormatException e) {
								channel.sendMessage("Player weight must be an integer.").queue();
								return;
							}
							player.setWeight(weight);
						} else {
							channel.sendMessage("When the parameter is `weight`, you must enter a value.").queue();
							return;
						}
						break;
					case "hidden":
						if (command.length >= 4) {
							if (!(command[3].equals("true") || command[3].equals("false"))) {
								channel.sendMessage("Hidden must be either true or false.").queue();
								return;
							} else {
								player.setHidden(Boolean.parseBoolean(command[3]));
							}
						} else {
							channel.sendMessage("When the parameter is `hidden`, you must enter a value.").queue();
							return;
						}
						break;
					case "max-items":
						if (command.length >= 4) {
							double maxItems;
							try {
								maxItems = Double.parseDouble(command[3]);
							} catch (NumberFormatException e) {
								channel.sendMessage("Maximum items capacity must be a number.").queue();
								return;
							}
							player.getInv().setCapacity(maxItems);
						} else {
							channel.sendMessage("When the parameter is `max-items`, you must enter a value.").queue();
							return;
						}
						break;
					case "max-clothes":
						if (command.length >= 4) {
							double maxClothes;
							try {
								maxClothes = Double.parseDouble(command[3]);
							} catch (NumberFormatException e) {
								channel.sendMessage("Maximum clothes capacity must be a number.").queue();
								return;
							}
							player.getClothes().setCapacity(maxClothes);
						} else {
							channel.sendMessage("When the parameter is `max-clothes`, you must enter a value.").queue();
							return;
						}
						break;
					case "picture":
						if (command.length >= 4) {
							player.setPicture(command[3]);
						} else {
							player.setPicture("");
						}
						break;
					case "desc":
						String desc = "";
						if (command.length >= 4) {
							desc = command[3];
							for (int i = 4; i < command.length; i++) {
								desc += " " + command[i];
							}
						}
						player.setDescription(desc);
						break;
					default:
						channel.sendMessage("Invalid parameter. Valid parameters are: `channel` `webhook` `name` `display-name` `gender` `height` `weight` `hidden` `max-items` `max-clothes` `picture` `desc`.").queue();
						return;
					}
					channel.sendMessage("The player was edited successfully.").queue();
				} catch (InvalidInputException e) {
					channel.sendMessage(e.getMessage()).queue();
				}
			} else {
				channel.sendMessage("Invalid command format: The correct format is `!editplayer <player> <parameter> [value]`").queue();
			}
		} else {
			channel.sendMessage("You must have the Host role to use this command.").queue();
		}
	}
	
	private static void commandListPlayers(String[] command, TextChannel channel, boolean isHost) {
		if (isHost) {
			try {
				if (command.length >= 2) {
					Room room = roleplay.findRoom(command[1]);
					channel.sendMessage("Players in room `" + command[1] + "`:\n" + DiscordOutputGenerator.convertPlayerList(room.getPlayers(), 1800)).queue();
				} else {
					channel.sendMessage("Players:\n" + DiscordOutputGenerator.convertPlayerList(roleplay.getPlayers(), 1800)).queue();
				}
			} catch (InvalidInputException e) {
				channel.sendMessage(e.getMessage()).queue();
			}
		} else {
			channel.sendMessage("You must have the Host role to use this command.").queue();
		}
	}
	
	private static void commandSeePlayer(String[] command, TextChannel channel, boolean isHost) {
		if (isHost) {
			if (command.length >= 2) {
				Player player;
				try {
					player = roleplay.findPlayer(command[1]);
				} catch (InvalidInputException e) {
					channel.sendMessage(e.getMessage()).queue();
					return;
				}
				channel.sendMessage(DiscordOutputGenerator.convertPlayerAdmin(player)).queue();
			} else {
				channel.sendMessage("Invalid command format: The correct format is `!seeplayer <player>`").queue();
			}
		} else {
			channel.sendMessage("You must have the Host role to use this command.").queue();
		}
	}
	
	private static void commandAddDoor(String[] command, TextChannel channel, boolean isHost) {
		if (isHost) {
			if (command.length >= 5) {
				try {
					Room room1 = roleplay.findRoom(command[1]);
					Room room2 = roleplay.findRoom(command[2]);
					if (!(command[3].equals("true") || command[3].equals("false"))) {
						channel.sendMessage("Hidden must be either true or false.").queue();
						return;
					}
					boolean hidden = Boolean.parseBoolean(command[3]);
					if (!(command[4].equals("true") || command[4].equals("false"))) {
						channel.sendMessage("Locked must be either true or false.").queue();
						return;
					}
					boolean locked = Boolean.parseBoolean(command[4]);
					String lock = "";
					if (command.length >= 6) {
						lock = (command[5].equals("none")) ? "" : command[5];
					}
					Door door = new Door(room1, room2, hidden, locked, lock);
					room1.getDoors().add(door);
					room2.getDoors().add(door);
					channel.sendMessage("The door was added successfully.").queue();
				} catch (InvalidInputException e) {
					channel.sendMessage(e.getMessage()).queue();
				}
			} else {
				channel.sendMessage("Invalid command format: The correct format is `!adddoor <room1> <room2> <hidden> <locked> [lock]`.").queue();
			}
		} else {
			channel.sendMessage("You must have the Host role to use this command.").queue();
		}
	}
	
	private static void commandDelDoor(String[] command, TextChannel channel, boolean isHost) {
		if (isHost) {
			if (command.length >= 2) {
				try {
					Door door = roleplay.findDoor(command[1]);
					door.getRoom1().getDoors().remove(door);
					door.getRoom2().getDoors().remove(door);
					channel.sendMessage("The door was deleted successfully.").queue();
				} catch (InvalidInputException e) {
					channel.sendMessage(e.getMessage());
				}
			} else {
				channel.sendMessage("Invalid command format: The correct format is `!deldoor <door>`.").queue();
			}
		} else {
			channel.sendMessage("You must have the Host role to use this command.").queue();
		}
	}
	
	private static void commandEditDoor(String[] command, TextChannel channel, boolean isHost) {
		if (isHost) {
			if (command.length >= 3) {
				try {
					Door door = roleplay.findDoor(command[1]);
					switch (command[2]) {
					case "room1":
						if (command.length >= 4) {
							Room oldRoom = door.getRoom1();
							Room newRoom = roleplay.findRoom(command[3]);
							oldRoom.getDoors().remove(door);
							newRoom.getDoors().add(door);
							door.setRoom1(newRoom);
						} else {
							channel.sendMessage("When the parameter is `room1`, you must enter a value.").queue();
							return;
						}
						break;
					case "room2":
						if (command.length >= 4) {
							Room oldRoom = door.getRoom2();
							Room newRoom = roleplay.findRoom(command[3]);
							oldRoom.getDoors().remove(door);
							newRoom.getDoors().add(door);
							door.setRoom2(newRoom);
						} else {
							channel.sendMessage("When the parameter is `room2`, you must enter a value.").queue();
							return;
						}
						break;
					case "hidden":
						if (command.length >= 4) {
							if (!(command[3].equals("true") || command[3].equals("false"))) {
								channel.sendMessage("Hidden must be either true or false.").queue();
								return;
							}
							boolean hidden = Boolean.parseBoolean(command[3]);
							door.setHidden(hidden);
						} else {
							channel.sendMessage("When the parameter is `hidden`, you must enter a value.").queue();
							return;
						}
						break;
					case "locked":
						if (command.length >= 4) {
							if (!(command[3].equals("true") || command[3].equals("false"))) {
								channel.sendMessage("Locked must be either true or false.").queue();
								return;
							}
							boolean locked = Boolean.parseBoolean(command[3]);
							door.setLocked(locked);
						} else {
							channel.sendMessage("When the parameter is `locked`, you must enter a value.").queue();
							return;
						}
						break;
					case "lock":
						String lock = "";
						if (command.length >= 4) {
							lock = (command[3].equals("none")) ? "" : command[3];
						}
						door.setLock(lock);
						break;
					default:
						channel.sendMessage("Invalid parameter. Valid parameters are: `room1` `room2` `hidden` `locked` `lock`.").queue();
						return;
					}
					channel.sendMessage("The door was edited successfully.").queue();
				} catch (InvalidInputException e) {
					channel.sendMessage(e.getMessage()).queue();
				}
			} else {
				channel.sendMessage("Invalid command format: The correct format is `!editdoor <door> <parameter> [value]`.").queue();
			}
		} else {
			channel.sendMessage("You must have the Host role to use this command.").queue();
		}
	}
	
	private static void commandListDoors(String[] command, TextChannel channel, boolean isHost) {
		if (isHost) {
			if (command.length >= 2) {
				try {
					Room room = roleplay.findRoom(command[1]);
					channel.sendMessage("List of doors attached to room `" + command[1] + "`:\n" + DiscordOutputGenerator.convertDoorList(room.getDoors(), room, 1800)).queue();
				} catch (InvalidInputException e) {
					channel.sendMessage(e.getMessage()).queue();
				}
			} else {
				channel.sendMessage("Invalid command format: The correct format is `!listdoors <room>`.").queue();
			}
		} else {
			channel.sendMessage("You must have the Host role to use this command.").queue();
		}
	}
	
	private static void commandSeeDoor(String[] command, TextChannel channel, boolean isHost) {
		if (isHost) {
			if (command.length >= 2) {
				try {
					Door door = roleplay.findDoor(command[1]);
					channel.sendMessage(DiscordOutputGenerator.convertDoorAdmin(door)).queue();
				} catch (InvalidInputException e) {
					channel.sendMessage(e.getMessage()).queue();
				}
			} else {
				channel.sendMessage("Invalid command format: The correct format is `!seedoor <door>`.").queue();
			}
		} else {
			channel.sendMessage("You must have the Host role to use this command.").queue();
		}
	}
	
	private static void commandDrag(String[] command, TextChannel channel, boolean isHost) {
		if (isHost) {
			if (command.length >= 3) {
				try {
					Player player = roleplay.findPlayer(command[1]);
					Room toRoom = roleplay.findRoom(command[2]);
					Room fromRoom = player.getRoom();
					toRoom.getPlayers().add(player);
					fromRoom.getPlayers().remove(player);
					player.setRoom(toRoom);
					channel.sendMessage("The player was moved successfully.").queue();
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
	
	private static void command8Ball(TextChannel channel) {
		int number = random.nextInt(30) + 1;
		switch (number) {
		case 1: channel.sendMessage(":8ball: It is certain. :8ball:").queue(); break;
		case 2: channel.sendMessage(":8ball: It is decidedly so. :8ball:").queue(); break;
		case 3: channel.sendMessage(":8ball: Without a doubt. :8ball:").queue(); break;
		case 4: channel.sendMessage(":8ball: Yes, definitely. :8ball:").queue(); break;
		case 5: channel.sendMessage(":8ball: You may rely on it. :8ball:").queue(); break;
		case 6: channel.sendMessage(":8ball: As I see it, yes. :8ball:").queue(); break;
		case 7: channel.sendMessage(":8ball: Most likely. :8ball:").queue(); break;
		case 8: channel.sendMessage(":8ball: Outlook good. :8ball:").queue(); break;
		case 9: channel.sendMessage(":8ball: Yeah sure. :8ball:").queue(); break;
		case 10: channel.sendMessage(":8ball: Signs point to yes. :8ball:").queue(); break;
		case 11: channel.sendMessage(":8ball: Reply hazy, try again. :8ball:").queue(); break;
		case 12: channel.sendMessage(":8ball: I'm tired. Ask again later. :8ball:").queue(); break;
		case 13: channel.sendMessage(":8ball: I cannot tell you now. :8ball:").queue(); break;
		case 14: channel.sendMessage(":8ball: You will know when the time is ripe. :8ball:").queue(); break;
		case 15: channel.sendMessage(":8ball: Concentrate and try again. :8ball:").queue(); break;
		case 16: channel.sendMessage(":8ball: It is unclear. :8ball:").queue(); break;
		case 17: channel.sendMessage(":8ball: Only time can tell. :8ball:").queue(); break;
		case 18: channel.sendMessage(":8ball: How the heck would I know? :8ball:").queue(); break;
		case 19: channel.sendMessage(":8ball: It seems probable. :8ball:").queue(); break;
		case 20: channel.sendMessage(":8ball: It seems unlikely. :8ball:").queue(); break;
		case 21: channel.sendMessage(":8ball: Don't count on it. :8ball:").queue(); break;
		case 22: channel.sendMessage(":8ball: My reply is no. :8ball:").queue(); break;
		case 23: channel.sendMessage(":8ball: My sources say no. :8ball:").queue(); break;
		case 24: channel.sendMessage(":8ball: Outlook not so good. :8ball:").queue(); break;
		case 25: channel.sendMessage(":8ball: Very doubtful. :8ball:").queue(); break;
		case 26: channel.sendMessage(":8ball: No. :8ball:").queue(); break;
		case 27: channel.sendMessage(":8ball: Yes. Just kidding, no. :8ball:").queue(); break;
		case 28: channel.sendMessage(":8ball: I don't want to tell you. :8ball:").queue(); break;
		case 29: channel.sendMessage(":8ball: You should feel shame for asking me that. :8ball:").queue(); break;
		case 30: channel.sendMessage(":8ball: Absolutely fricking not. :8ball:").queue(); break;
		}
	}
	
	private static void commandChoose(String[] command, TextChannel channel) {
		if (command.length >= 2) {
			if (command.length >=3) {
				int choice = random.nextInt(command.length - 1) + 1;
				channel.sendMessage(command[choice]);
			} else {
				channel.sendMessage("You need to put at least 2 options to choose between, separated by spaces!");
			}
		} else {
			channel.sendMessage("Invalid command format: The correct format is `!choose <options>`").queue();
		}
	}
	
	private static void commandExcuse(String[] command, TextChannel channel) {
		String excuse;
		if (command.length >= 2) {
			excuse = command[1];
			for (int i = 2; i < command.length; i++) {
				excuse += " " + command[i];
			}
			excuse += " because ";
		} else {
			excuse = "I can't right now because ";
		}
		String[] attackers = {"Monokuma", "the mastermind", "my evil twin", "Sans Undertale", "UniqueImpact", "my aunt", "Mr. Blobby", "the Numbertaker",
				"Bill Cipher", "Perry the Platypus", "Dr. Doofenshmirtz", "the Queen", "my imaginary friend", "the Kellogg Company", "Spongebob Squarepants",
				"Jeff Bezos", "Mr. Bean", "Shrek", "God", "my little sister", "a ninja", "the Doctor", "Sherlock Holmes", "Boris Johnson", "my butler",
				"a creeper", "your father", "Darth Vader", "Gruntilda", "my landlord", "my psychologist", "my teacher", "your mother", "a very scary man",
				"Father Christmas", "the tooth fairy", "my arch nemesis", "my third cousin's cat", "my pet giraffe", "an enormous gerbil", "everyone in France",
				"Will Smith", "the Pope", "the United Nations", "Elon Musk", "Thomas the Tank Engine", "Tinky Winky", "Vladamir Putin", "Sonic", "Mario",
				"my tennis instructor", "the Discord moderator", "an FBI agent", "Harry Potter", "Voldemort", "a man in a banana costume", "a magician",
				"a lawn gnome", "a creepy doll", "Bob the Builder", "Robbie Rotten", "Iron Man", "Thor", "Spiderman", "Batman", "a highly advanced AI",
				"an e-girl", "an e-boy", "my \"best friend\"", "the tickle monster", "a newborn baby", "Captain Jack Sparrow", "Dory", "Thanos", "my boss",
				"a hideous monster", "an eagle", "Joe Biden", "Nintendo", "Lightning McQueen", "a Karen", "the kraken", "someone", "Microsoft"};
		String[] actions = {"hiding under", "attacking", "spying on", "stealing", "plotting to murder", "getting away with", "trying to marry", "eating",
				"destroying", "punching", "terrorising", "licking", "vandalising", "sitting on", "kidnapping", "verbally assaulting", "gently caressing",
				"trying to buy", "growling at", "looking menacingly at", "about to become", "playing chess against", "systematically destroying", "wearing",
				"setting fire to", "escaping with", "secretly replacing", "shouting at", "whispering at", "knocking over", "pretending to be", "repossessing",
				"pointing a gun at", "trying to bribe", "preparing a nuclear assault against", "about to yeet away", "splashing water at", "dabbing on",
				"beating up", "rapidly approaching", "assassinating", "sneaking up on", "plotting a dastardly scheme against", "tickling", "poking", "fighting",
				"galloping away upon", "now", "claiming to be more important than", "suing", "hiding", "claiming ownership of", "chasing", "frightening",
				"declaring war on", "catapulting", "incincerating", "ravaging", "overenthusiastically interested in", "jealous about", "watering", "pawning off"};
		String[] victims = {"my fridge", "my mother", "my father", "me", "you", "my entire family", "my homework", "my hopes and dreams", "my house", "an egg",
				"my beard", "my toes", "my bed", "my career", "the children", "the moon", "Antarctica", "the sea", "my favourite mug", "my shadow", "my money",
				"my front door", "my car", "all of the birds", "the ozone layer", "my tomatoes", "my orange juice", "the Eiffel Tower", "my Xbox 360 Kinect",
				"a zebra", "my cat", "the TV remote", "my phone", "my shampoo", "my local supermarket", "my enormous yacht", "my washing machine", "my curtains",
				"one million dollars", "the entire neighbourhood", "the biscuit tin", "my lunch", "the White House", "my trousers", "my hat", "my best friend",
				"everyone except me", "my childhood", "my kneecaps", "the crown jewels", "the Mona Lisa", "my bin", "everything", "my garden", "my bathroom",
				"my allotment", "my computer", "my pencil case", "my toaster", "Ireland", "France", "my crops", "my lemons", "the internet", "the WiFi",
				"my wife", "my husband"};
		int attackerNum = random.nextInt(attackers.length);
		int actionNum = random.nextInt(actions.length);
		int victimNum = random.nextInt(victims.length);
		excuse += attackers[attackerNum] + " is " + actions[actionNum] + " " + victims[victimNum] + ".";
		channel.sendMessage(excuse).queue();
	}
	
	private static void commandRoll(String[] command, TextChannel channel) {
		int size = 20;
		if (command.length >= 2) {
			try {
				size = Integer.parseInt(command[1]);
				if ((size < 2) || (size > 1000)) {
					channel.sendMessage("Dice size must be between 2 and 1000").queue();
					return;
				}
			} catch (NumberFormatException e) {
				channel.sendMessage("Dice size must be an integer").queue();
				return;
			}
		}
		int num = 1;
		if (command.length >= 3) {
			try {
				num = Integer.parseInt(command[2]);
				if ((num < 1) || (num > 100)) {
					channel.sendMessage("Dice number must be between 1 and 100").queue();
					return;
				}
			} catch (NumberFormatException e) {
				channel.sendMessage("Dice size must be an integer").queue();
				return;
			}
		}
		String output = "Rolling `" + num + "` dice of size `" + size + "`:\n";
		int total = 0;
		for (int i = 0; i < num; i++) {
			int roll = random.nextInt(size) + 1;
			output += "`" + roll + "` ";
			total += roll;
		}
		output += "\nTotal: `" + total + "`";
		channel.sendMessage(output).queue();
	}

}

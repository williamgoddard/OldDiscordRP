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
					+ "!adddoor <room1> <room2> <hidden> <locked> [lock]\r\n"
					+ "!deldoor <door>\r\n"
					+ "!editdoor <door> <paramter> [value]\r\n"
					+ "!listdoors <room>\r\n"
					+ "!seedoor <door>\r\n"
					+ "\r\n"
					+ "!dragall <room>```").queue();
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

}

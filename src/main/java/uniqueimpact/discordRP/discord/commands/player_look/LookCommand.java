package uniqueimpact.discordRP.discord.commands.player_look;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import uniqueimpact.discordRP.discord.commands.Command;
import uniqueimpact.discordRP.discord.utils.DiscordOutputGenerator;
import uniqueimpact.discordRP.discord.utils.WebhookManager;
import uniqueimpact.discordRP.things.Inventory;
import uniqueimpact.discordRP.things.Item;
import uniqueimpact.discordRP.things.Player;
import uniqueimpact.discordRP.things.Room;
import uniqueimpact.discordRP.utils.InvalidInputException;

import java.util.ArrayList;
import java.util.List;

public class LookCommand implements Command {

    @Override
    public String run(SlashCommandInteractionEvent command) {

        String path = command.getFullCommandName();

        String channelId = command.getChannel().getId();
        Player player;
        try {
            player = roleplay.findPlayerByChannel(channelId);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        switch (path) {
            case "look room":
                return room(command, player);
            case "look item":
                return item(command, player);
            case "look character":
                return character(command, player);
            default:
                return "Error: Invalid command path (" + path + ")";
        }
    }

    private String room(SlashCommandInteractionEvent command, Player player) {

        Room room = player.getRoom();

        WebhookManager.sendSelf("*I am currently in the " + room.getName() + ".*\n" + room.getDescription(), player);

        Inventory inv = player.getRoom().getInv();
        List<Item> items = inv.getItems();

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
            outputString += "*I see these items:*\n" + DiscordOutputGenerator.convertItemList(tItems, 1000) + "\n";
        } else if (iItems.size() == 0) {
            outputString += "*I don't see any items here.*\n";
        }
        if (iItems.size() > 0) {
            outputString += "*I see lots of these items:*\n" + DiscordOutputGenerator.convertItemList(iItems, 400) + "\n";
        }
        if (uItems.size() > 0) {
            outputString += "*I see these objects:*\n" + DiscordOutputGenerator.convertItemList(uItems, 400);
        } else {
            outputString += "*I don't see any objects here.*";
        }
        WebhookManager.sendSelf(outputString, player);

        return null;

    }

    private String item(SlashCommandInteractionEvent command, Player player) {

        String itemName = command.getOption("item") != null ? command.getOption("item").getAsString() : null;
        Integer itemNum = command.getOption("num") != null ? command.getOption("num").getAsInt() : 1;

        Inventory inv = player.getRoom().getInv();
        List<Item> items = inv.getItems();

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

        if (itemName == null ) {
            String outputString = "*I take a look around...*\n";
            if (tItems.size() > 0) {
                outputString += "*I see these items:*\n" + DiscordOutputGenerator.convertItemList(tItems, 1000) + "\n";
            } else if (iItems.size() == 0) {
                outputString += "*I don't see any items here.*\n";
            }
            if (iItems.size() > 0) {
                outputString += "*I see lots of these items:*\n" + DiscordOutputGenerator.convertItemList(iItems, 400) + "\n";
            }
            if (uItems.size() > 0) {
                outputString += "*I see these objects:*\n" + DiscordOutputGenerator.convertItemList(uItems, 400);
            } else {
                outputString += "*I don't see any objects here.*";
            }
            WebhookManager.sendSelf(outputString, player);
            return null;
        }

        Item item;
        try {
            item = inv.findItem(itemName, itemNum);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        WebhookManager.sendSelf("*I examine the " + item.getName() + "*.\n" + DiscordOutputGenerator.convertItem(item), player);
        return null;

    }

    private String character(SlashCommandInteractionEvent command, Player player) {

        String characterName = command.getOption("character") != null ? command.getOption("character").getAsString() : null;

        Room room = player.getRoom();

        if (characterName == null) {
            List<Player> players = room.getPlayers(false);
            if (players.size() > 1) {
                WebhookManager.sendSelf("*I see these people here:*\n" + DiscordOutputGenerator.convertPlayerList(players, 1900), player);
            } else {
                WebhookManager.sendSelf("*I don't see anyone else here.*", player);
            }
            return null;
        }

        Player otherPlayer;
        try {
            otherPlayer = roleplay.findRoomPlayer(room, characterName, false);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        WebhookManager.sendSelf("*I look at " + otherPlayer.getDisplayName() + ".*\n" + DiscordOutputGenerator.convertPlayer(otherPlayer), player);

        Inventory targetClothesInv = otherPlayer.getClothes();
        List<Item> targetClothes = targetClothesInv.getItems();
        if (targetClothes.size() > 0) {
            WebhookManager.sendSelf("*" + otherPlayer.getDisplayName() + " is currently wearing these clothes:*\n" + DiscordOutputGenerator.convertItemList(targetClothes, 1900), player);
        } else {
            WebhookManager.sendSelf("*" + otherPlayer.getDisplayName() + " is currently not wearing much...*", player);
        }

        return null;

    }

}

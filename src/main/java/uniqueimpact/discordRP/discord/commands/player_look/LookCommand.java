package uniqueimpact.discordRP.discord.commands.player_look;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import uniqueimpact.discordRP.discord.commands.Command;
import uniqueimpact.discordRP.discord.utils.DiscordOutputGenerator;
import uniqueimpact.discordRP.things.Inventory;
import uniqueimpact.discordRP.things.Item;
import uniqueimpact.discordRP.things.Chara;
import uniqueimpact.discordRP.things.Room;
import uniqueimpact.discordRP.utils.InvalidInputException;

import java.util.ArrayList;
import java.util.List;

public class LookCommand implements Command {

    @Override
    public String run(SlashCommandInteractionEvent command) {

        String path = command.getFullCommandName();

        String channelId = command.getChannel().getId();
        Chara player;
        try {
            player = roleplay.findCharacterByChannel(channelId);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        switch (path) {
            case "look room":
                return room(command, player);
            case "look item":
                return item(command, player);
            case "look inv":
                return inv(command, player);
            case "look clothes":
                return clothes(command, player);
            case "look character":
                return character(command, player);
            default:
                return "Error: Invalid command path (" + path + ")";
        }
    }

    private String room(SlashCommandInteractionEvent command, Chara player) {

        Room room = player.getRoom();

        return "You are currently in the `" + room.getName() + "`.\n" + room.getDescription();

    }

    private String item(SlashCommandInteractionEvent command, Chara player) {

        String itemName = command.getOption("item").getAsString();
        Integer itemNum = command.getOption("num") != null ? command.getOption("num").getAsInt() : 1;

        Inventory inv = player.getRoom().getInv();

        Item item;
        try {
            item = inv.findItem(itemName, itemNum);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        return "You examine the `" + item.getName() + "`:\n" + DiscordOutputGenerator.convertItem(item);

    }

    private String inv(SlashCommandInteractionEvent command, Chara player) {

        String itemName = command.getOption("item").getAsString();
        Integer itemNum = command.getOption("num") != null ? command.getOption("num").getAsInt() : 1;

        Inventory inv = player.getInv();

        Item item;
        try {
            item = inv.findItem(itemName, itemNum);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        return "You examine the `" + item.getName() + "`:\n" + DiscordOutputGenerator.convertItem(item);

    }

    private String clothes(SlashCommandInteractionEvent command, Chara player) {

        String itemName = command.getOption("item").getAsString();
        Integer itemNum = command.getOption("num") != null ? command.getOption("num").getAsInt() : 1;

        Inventory inv = player.getClothes();

        Item item;
        try {
            item = inv.findItem(itemName, itemNum);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        return "You examine your `" + item.getName() + "`:\n" + DiscordOutputGenerator.convertItem(item);

    }

    private String character(SlashCommandInteractionEvent command, Chara player) {

        String characterName = command.getOption("character").getAsString();

        Room room = player.getRoom();

        if (characterName == null) {
            List<Chara> players = room.getCharacters(false);
            if (players.size() > 1) {
                return "You see these people here:\n" + DiscordOutputGenerator.convertPlayerList(players, 1900);
            } else {
                return "You don't see anyone else here.";
            }
        }

        Chara otherPlayer;
        try {
            otherPlayer = room.findCharacter(characterName, false);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        String outputMessage = DiscordOutputGenerator.convertPlayer(otherPlayer) + "\n";

        Inventory targetClothesInv = otherPlayer.getClothes();
        List<Item> targetClothes = targetClothesInv.getItems();
        if (targetClothes.size() > 0) {
            outputMessage += "`" + otherPlayer.getDisplayName() + "` is currently wearing these clothes:\n" + DiscordOutputGenerator.convertItemList(targetClothes, 300);
        } else {
            outputMessage += "`" + otherPlayer.getDisplayName() + "` is currently not wearing much...";
        }

        return outputMessage;

    }

}

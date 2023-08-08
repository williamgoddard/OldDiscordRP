package uniqueimpact.discordRP.discord.commands.player_look;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
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
    public MessageCreateData run(SlashCommandInteractionEvent command) {
        return new MessageCreateBuilder().setContent(runCommand(command)).build();
    }

    private String runCommand(SlashCommandInteractionEvent command) {

        String path = command.getFullCommandName();

        String channelId = command.getChannel().getId();
        Chara character;
        try {
            character = roleplay.findCharacterByChannel(channelId);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        switch (path) {
            case "look room":
                return room(command, character);
            case "look item":
                return item(command, character);
            case "look inv":
                return inv(command, character);
            case "look clothes":
                return clothes(command, character);
            case "look character":
                return character(command, character);
            case "look self":
                return self(command, character);
            default:
                return "Error: Invalid command path (" + path + ")";
        }
    }

    private String room(SlashCommandInteractionEvent command, Chara character) {

        Room room = character.getRoom();

        String output = "You are currently in the `" + room.getName() + "`.\n" + room.getDescription();
        if (room.getCharacters(false).size() > 1) {
            output += "\nYou see these people here:\n" + DiscordOutputGenerator.convertCharaList(room.getCharacters(false), 400);
        } else {
            output += "\nYou don't see anyone else here.";
        }

        return output;

    }

    private String item(SlashCommandInteractionEvent command, Chara character) {

        String itemName = command.getOption("item").getAsString();
        Integer itemNum = command.getOption("num") != null ? command.getOption("num").getAsInt() : 1;

        Inventory inv = character.getRoom().getInv();

        Item item;
        try {
            item = inv.findItem(itemName, itemNum);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        return "You examine the `" + item.getName() + "`:\n" + DiscordOutputGenerator.convertItem(item);

    }

    private String inv(SlashCommandInteractionEvent command, Chara character) {

        String itemName = command.getOption("item").getAsString();
        Integer itemNum = command.getOption("num") != null ? command.getOption("num").getAsInt() : 1;

        Inventory inv = character.getInv();

        Item item;
        try {
            item = inv.findItem(itemName, itemNum);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        return "You examine the `" + item.getName() + "`:\n" + DiscordOutputGenerator.convertItem(item);

    }

    private String clothes(SlashCommandInteractionEvent command, Chara character) {

        String itemName = command.getOption("item").getAsString();
        Integer itemNum = command.getOption("num") != null ? command.getOption("num").getAsInt() : 1;

        Inventory inv = character.getClothes();

        Item item;
        try {
            item = inv.findItem(itemName, itemNum);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        return "You examine your `" + item.getName() + "`:\n" + DiscordOutputGenerator.convertItem(item);

    }

    private String character(SlashCommandInteractionEvent command, Chara character) {

        String characterName = command.getOption("character").getAsString();

        Room room = character.getRoom();

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

    private String self(SlashCommandInteractionEvent command, Chara character) {

        return DiscordOutputGenerator.convertPlayer(character);

    }

}

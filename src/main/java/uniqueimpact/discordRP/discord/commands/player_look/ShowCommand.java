package uniqueimpact.discordRP.discord.commands.player_look;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import uniqueimpact.discordRP.discord.commands.Command;
import uniqueimpact.discordRP.discord.utils.DiscordOutputGenerator;
import uniqueimpact.discordRP.discord.utils.WebhookManager;
import uniqueimpact.discordRP.things.Chara;
import uniqueimpact.discordRP.things.Inventory;
import uniqueimpact.discordRP.things.Item;
import uniqueimpact.discordRP.things.Room;
import uniqueimpact.discordRP.utils.InvalidInputException;

import java.util.List;

public class ShowCommand implements Command {

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
            case "show item":
                return item(command, character);
            case "show inv":
                return inv(command, character);
            case "show clothes":
                return clothes(command, character);
            default:
                return "Error: Invalid command path (" + path + ")";
        }
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

        WebhookManager.sendOthers("*" + character.getDisplayName() + " shows the `" + item.getName() + "`.*\n" + item.getDescription(), character);
        return "You show the `" + item.getName() + "`:\n" + DiscordOutputGenerator.convertItem(item);

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

        WebhookManager.sendOthers("*" + character.getDisplayName() + " shows the `" + item.getName() + "`.*\n" + item.getDescription(), character);
        return "You show the `" + item.getName() + "`:\n" + DiscordOutputGenerator.convertItem(item);

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

        WebhookManager.sendOthers("*" + character.getDisplayName() + " shows their `" + item.getName() + "`.*\n" + item.getDescription(), character);
        return "You examine your `" + item.getName() + "`:\n" + DiscordOutputGenerator.convertItem(item);

    }

}

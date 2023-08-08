package uniqueimpact.discordRP.discord.commands.player_look;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import uniqueimpact.discordRP.discord.commands.Command;
import uniqueimpact.discordRP.discord.utils.DiscordOutputGenerator;
import uniqueimpact.discordRP.things.Chara;
import uniqueimpact.discordRP.things.Inventory;
import uniqueimpact.discordRP.things.Item;
import uniqueimpact.discordRP.utils.InvalidInputException;

import java.util.List;

public class ItemsCommand implements Command {

    @Override
    public MessageCreateData run(SlashCommandInteractionEvent command) {
        return new MessageCreateBuilder().setContent(runCommand(command)).build();
    }

    private String runCommand(SlashCommandInteractionEvent command) {

        String channelId = command.getChannel().getId();
        Chara player;
        try {
            player = roleplay.findCharacterByChannel(channelId);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        Inventory inv = player.getRoom().getInv();
        List<Item> tItems = inv.getItems(true, null, null);
        List<Item> uItems = inv.getItems(false, null, null);

        String outputString = "You take a look around...\n";

        if (tItems.size() > 0) {
            outputString += "You see these items:\n" + DiscordOutputGenerator.convertItemList(tItems, 1400) + "\n";
        } else {
            outputString += "You don't see any items here.\n";
        }

        if (uItems.size() > 0) {
            outputString += "You see these objects:\n" + DiscordOutputGenerator.convertItemList(uItems, 400);
        } else {
            outputString += "You don't see any objects here.";
        }

        return outputString;

    }

}

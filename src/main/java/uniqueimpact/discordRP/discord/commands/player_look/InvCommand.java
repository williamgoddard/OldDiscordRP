package uniqueimpact.discordRP.discord.commands.player_look;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import uniqueimpact.discordRP.discord.commands.Command;
import uniqueimpact.discordRP.discord.utils.DiscordOutputGenerator;
import uniqueimpact.discordRP.discord.utils.WebhookManager;
import uniqueimpact.discordRP.things.Inventory;
import uniqueimpact.discordRP.things.Item;
import uniqueimpact.discordRP.things.Player;
import uniqueimpact.discordRP.utils.InvalidInputException;

import java.util.List;

public class InvCommand implements Command {

    @Override
    public String run(SlashCommandInteractionEvent command) {

        String itemName = command.getOption("item") != null ? command.getOption("item").getAsString() : null;
        Integer itemNum = command.getOption("num") != null ? command.getOption("num").getAsInt() : 1;

        String channelId = command.getChannel().getId();
        Player player;
        try {
            player = roleplay.findPlayerByChannel(channelId);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        Inventory inv = player.getInv();
        List<Item> items = inv.getItems();

        if (itemName == null ) {

            if (items.size() == 0) {
                return "You are currently not holding anything.";
            }

            return "You are currently holding these items:*\n" + DiscordOutputGenerator.convertItemList(items, 1900);

        }

        Item item;
        try {
            item = inv.findItem(itemName, itemNum);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        return "You examine the `" + item.getName() + "`:\n" + DiscordOutputGenerator.convertItem(item);

    }

}

package uniqueimpact.discordRP.discord.commands.player_inv;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import uniqueimpact.discordRP.discord.commands.Command;
import uniqueimpact.discordRP.discord.utils.WebhookManager;
import uniqueimpact.discordRP.things.Item;
import uniqueimpact.discordRP.things.Player;
import uniqueimpact.discordRP.utils.InvalidInputException;

public class WearCommand implements Command {

    @Override
    public String run(SlashCommandInteractionEvent command) {

        String itemName = command.getOption("item").getAsString();
        Integer itemNum = command.getOption("num") != null ? command.getOption("num").getAsInt() : 1;

        String channelId = command.getChannel().getId();
        Player character;
        try {
            character = roleplay.findPlayerByChannel(channelId);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        Item item;
        try {
            item = character.getInv().findItem(itemName, itemNum);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        if (!item.isWearable()) {
            WebhookManager.sendSelf("*I can't wear the " + item.getName() + ".*", character);
            return null;
        }

        if (item.getWeight() > character.getClothes().getRemainingCapacity()) {
            WebhookManager.sendSelf("*I can't wear my " + item.getName() + " because I would be wearing too much.*", character);
            return null;
        }

        character.getClothes().getItems().add(item);
        character.getInv().getItems().remove(item);

        WebhookManager.sendSelf("*I put on my " + item.getName() + ".*", character);
        WebhookManager.sendOthers("*" + character.getDisplayName() + " put on their " + item.getName() + ".*", character);
        return null;

    }

}

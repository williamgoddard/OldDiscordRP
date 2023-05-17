package uniqueimpact.discordRP.discord.commands.player_inv;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import uniqueimpact.discordRP.discord.commands.Command;
import uniqueimpact.discordRP.discord.utils.WebhookManager;
import uniqueimpact.discordRP.things.Item;
import uniqueimpact.discordRP.things.Chara;
import uniqueimpact.discordRP.utils.InvalidInputException;

public class WearCommand implements Command {

    @Override
    public String run(SlashCommandInteractionEvent command) {

        String itemName = command.getOption("item").getAsString();
        Integer itemNum = command.getOption("num") != null ? command.getOption("num").getAsInt() : 1;

        String channelId = command.getChannel().getId();
        Chara character;
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
            return "You can't wear the `" + item.getName() + "`.";
        }

        if (item.getWeight() > character.getClothes().getRemainingCapacity()) {
            return "You can't wear the `" + item.getName() + "` because you would be wearing too much.";
        }

        character.getClothes().getItems().add(item);
        character.getInv().getItems().remove(item);

        WebhookManager.sendOthers("*" + character.getDisplayName() + " put on their " + item.getName() + ".*", character);
        return "You put on your `" + item.getName() + "`.";

    }

}

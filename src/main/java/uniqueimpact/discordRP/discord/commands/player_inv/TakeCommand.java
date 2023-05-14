package uniqueimpact.discordRP.discord.commands.player_inv;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import uniqueimpact.discordRP.discord.commands.Command;
import uniqueimpact.discordRP.discord.utils.WebhookManager;
import uniqueimpact.discordRP.things.Item;
import uniqueimpact.discordRP.things.Player;
import uniqueimpact.discordRP.things.Room;
import uniqueimpact.discordRP.utils.InvalidInputException;

import java.util.Arrays;

public class TakeCommand implements Command {

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

        Room room = character.getRoom();

        Item item;
        try {
            item = room.getInv().findItem(itemName, itemNum);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        if (!item.isTakeable()) {
            return "You can't take the 1" + item.getName() + "1.";
        }

        if (item.getWeight() > character.getInv().getRemainingCapacity()) {
            return "You can't take the `" + item.getName() + "` because you would be holding too much.";
        }

        if (item.isInfinite()) {

            Item newItem;
            try {
                newItem = item.getCopy();
            } catch (InvalidInputException e) {
                return e.getMessage();
            }

            newItem.setInfinite(false);
            character.getInv().getItems().add(newItem);

            if (Arrays.asList('A', 'E', 'I', 'O', 'U', '8').contains(item.getName().toUpperCase().charAt(0))) {
                WebhookManager.sendOthers("*" + character.getDisplayName() + " took an " + item.getName() + ".*" , character);
                return "You took an `" + item.getName() + "`.";
            } else {
                WebhookManager.sendOthers("*" + character.getDisplayName() + " took a " + item.getName() + ".*" , character);
                return "You took a `" + item.getName() + "`.";
            }

        } else {

            character.getInv().getItems().add(item);
            room.getInv().getItems().remove(item);

            WebhookManager.sendOthers("*" + character.getDisplayName() + " took the " + item.getName() + ".*", character);
            return "You took the `" + item.getName() + "`.";

        }

    }

}

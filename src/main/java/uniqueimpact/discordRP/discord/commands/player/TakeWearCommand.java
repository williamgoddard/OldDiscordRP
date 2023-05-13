package uniqueimpact.discordRP.discord.commands.player;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import uniqueimpact.discordRP.discord.commands.Command;
import uniqueimpact.discordRP.discord.utils.WebhookManager;
import uniqueimpact.discordRP.things.Item;
import uniqueimpact.discordRP.things.Player;
import uniqueimpact.discordRP.things.Room;
import uniqueimpact.discordRP.utils.InvalidInputException;

import java.util.Arrays;

public class TakeWearCommand implements Command {

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
            WebhookManager.sendSelf("*I can't take the " + item.getName() + ".*", character);
            return null;
        }

        if (!item.isWearable()) {
            WebhookManager.sendSelf("*I can't put on the " + item.getName() + ".*", character);
            return null;
        }

        if (item.getWeight() > character.getClothes().getRemainingCapacity()) {
            WebhookManager.sendSelf("*I can't take and put on the " + item.getName() + " because I would be wearing too much.*", character);
            return null;
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
                WebhookManager.sendSelf("*I took and put on an " + item.getName() + ".*", character);
                WebhookManager.sendOthers("*" + character.getDisplayName() + " took and put on an " + item.getName() + ".*" , character);
            } else {
                WebhookManager.sendSelf("*I took and put on a " + item.getName() + ".*", character);
                WebhookManager.sendOthers("*" + character.getDisplayName() + " took and put on a " + item.getName() + ".*" , character);
            }

            return null;

        } else {

            character.getInv().getItems().add(item);
            room.getInv().getItems().remove(item);

            WebhookManager.sendSelf("*I took and put on the " + item.getName() + ".*", character);
            WebhookManager.sendOthers("*" + character.getDisplayName() + " took and put on the " + item.getName() + ".*", character);
            return null;

        }

    }

}

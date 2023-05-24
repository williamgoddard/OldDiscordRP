package uniqueimpact.discordRP.discord.commands.player_inv;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import uniqueimpact.discordRP.discord.commands.Command;
import uniqueimpact.discordRP.discord.utils.WebhookManager;
import uniqueimpact.discordRP.things.Item;
import uniqueimpact.discordRP.things.Chara;
import uniqueimpact.discordRP.things.Room;
import uniqueimpact.discordRP.utils.InvalidInputException;

import java.util.Arrays;

public class TakeWearCommand implements Command {

    @Override
    public String run(SlashCommandInteractionEvent command) {

        String itemName = command.getOption("item").getAsString();
        Integer itemNum = command.getOption("num") != null ? command.getOption("num").getAsInt() : 1;

        String channelId = command.getChannel().getId();
        Chara character;
        try {
            character = roleplay.findCharacterByChannel(channelId);
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
            return "You can't take the `" + item.getName() + "`.";
        }

        if (!item.isWearable()) {
            return "You can't put on the `" + item.getName() + "`.";
        }

        if (item.getWeight() > character.getClothes().getRemainingCapacity()) {
            return "You can't take and put on the `" + item.getName() + "` because you would be wearing too much.";
        }

        if (item.isInfinite()) {

            Item newItem;
            try {
                newItem = item.getSingleCopy();
            } catch (InvalidInputException e) {
                return e.getMessage();
            }

            newItem.setInfinite(false);
            character.getClothes().addItem(newItem);

            if (Arrays.asList('A', 'E', 'I', 'O', 'U', '8').contains(item.getName().toUpperCase().charAt(0))) {
                WebhookManager.sendOthers("*" + character.getDisplayName() + " took and put on an " + item.getName() + ".*" , character);
                return "You took and put on an `" + item.getName() + "`.";
            } else {
                WebhookManager.sendOthers("*" + character.getDisplayName() + " took and put on a " + item.getName() + ".*" , character);
                return "You took and put on a `" + item.getName() + "`.";
            }

        } else {

            character.getClothes().addItem(item);
            room.getInv().delItem(item);

            WebhookManager.sendOthers("*" + character.getDisplayName() + " took and put on the " + item.getName() + ".*", character);
            return "You took and put on the `" + item.getName() + "`.";

        }

    }

}

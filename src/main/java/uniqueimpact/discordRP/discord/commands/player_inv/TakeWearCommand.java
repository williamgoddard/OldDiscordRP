package uniqueimpact.discordRP.discord.commands.player_inv;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import uniqueimpact.discordRP.discord.commands.Command;
import uniqueimpact.discordRP.discord.utils.WebhookManager;
import uniqueimpact.discordRP.things.Item;
import uniqueimpact.discordRP.things.Chara;
import uniqueimpact.discordRP.things.Room;
import uniqueimpact.discordRP.utils.InvalidInputException;

import java.util.Arrays;

public class TakeWearCommand implements Command {

    @Override
    public void run(SlashCommandInteractionEvent command) {
        command.reply(runCommand(command)).queue();
    }

    private String runCommand(SlashCommandInteractionEvent command) {

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

        Item newItem;
        try {
            newItem = item.getSingleCopy();
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        if (!item.isInfinite()) {
            if (item.getQuantity() > 1) {
                try {
                    item.setQuantity(item.getQuantity() - 1);
                } catch (InvalidInputException e) {
                    return e.getMessage();
                }
            } else {
                room.getInv().delItem(item);
            }
        }

        character.getClothes().addItem(newItem);

        WebhookManager.sendOthers("*" + character.getDisplayName() + " took and put on the " + item.getName() + ".*", character);
        return "You took and put on the `" + item.getName() + "`.";

    }

}

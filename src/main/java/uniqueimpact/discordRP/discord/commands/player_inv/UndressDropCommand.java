package uniqueimpact.discordRP.discord.commands.player_inv;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import uniqueimpact.discordRP.discord.commands.Command;
import uniqueimpact.discordRP.discord.utils.WebhookManager;
import uniqueimpact.discordRP.things.Item;
import uniqueimpact.discordRP.things.Chara;
import uniqueimpact.discordRP.things.Room;
import uniqueimpact.discordRP.utils.InvalidInputException;

public class UndressDropCommand implements Command {

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

        Item item;
        try {
            item = character.getClothes().findItem(itemName, itemNum);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        Room room = character.getRoom();

        if (item.getWeight() > room.getInv().getRemainingCapacity()) {
            return "You can't take off and drop your `" + item.getName() + "` because the room is too full.";
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
                character.getClothes().delItem(item);
            }
        }

        room.getInv().addItem(newItem);

        WebhookManager.sendOthers("*" + character.getDisplayName() + " took off and dropped their " + item.getName() + ".*", character);
        return "You took off and dropped your `" + item.getName() + "`.";

    }

}

package uniqueimpact.discordRP.discord.commands.player_inv;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import uniqueimpact.discordRP.discord.commands.Command;
import uniqueimpact.discordRP.discord.utils.WebhookManager;
import uniqueimpact.discordRP.things.Item;
import uniqueimpact.discordRP.things.Chara;
import uniqueimpact.discordRP.utils.InvalidInputException;

public class UndressCommand implements Command {

    @Override
    public MessageCreateData run(SlashCommandInteractionEvent command) {
        return new MessageCreateBuilder().setContent(runCommand(command)).build();
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

        Item item;
        try {
            item = character.getClothes().findItem(itemName, itemNum);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        if (item.getWeight() > character.getInv().getRemainingCapacity()) {
            return "You can't take off your `" + item.getName() + "` because you would be holding too much.";
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

        character.getInv().addItem(newItem);

        WebhookManager.sendOthers("*" + character.getDisplayName() + " took off their " + item.getName() + ".*", character);
        return "You took off your `" + item.getName() + "`.";

    }

}

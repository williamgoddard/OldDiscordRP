package uniqueimpact.discordRP.discord.commands.player_inv;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import uniqueimpact.discordRP.discord.commands.Command;
import uniqueimpact.discordRP.discord.utils.WebhookManager;
import uniqueimpact.discordRP.things.Item;
import uniqueimpact.discordRP.things.Player;
import uniqueimpact.discordRP.things.Room;
import uniqueimpact.discordRP.utils.InvalidInputException;

public class DropCommand implements Command {

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

        Room room = character.getRoom();

        if (item.getWeight() > room.getInv().getRemainingCapacity()) {
            return "You can't drop the `" + item.getName() + "` because the room is too full.";
        }

        room.getInv().getItems().add(item);
        character.getInv().getItems().remove(item);

        WebhookManager.sendOthers(character.getDisplayName() + " dropped their " + item.getName() + ".", character);
        return "You dropped the `" + item.getName() + "`.";

    }

}

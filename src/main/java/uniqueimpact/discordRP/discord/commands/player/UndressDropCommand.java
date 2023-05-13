package uniqueimpact.discordRP.discord.commands.player;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import uniqueimpact.discordRP.discord.commands.Command;
import uniqueimpact.discordRP.discord.utils.WebhookManager;
import uniqueimpact.discordRP.things.Item;
import uniqueimpact.discordRP.things.Player;
import uniqueimpact.discordRP.things.Room;
import uniqueimpact.discordRP.utils.InvalidInputException;

public class UndressDropCommand implements Command {

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
            item = character.getClothes().findItem(itemName, itemNum);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        Room room = character.getRoom();

        if (item.getWeight() > room.getInv().getRemainingCapacity()) {
            WebhookManager.sendSelf("*I can't take off and drop my " + item.getName() + " because the room is too full.*", character);
            return null;
        }

        room.getInv().getItems().add(item);
        character.getClothes().getItems().remove(item);

        WebhookManager.sendSelf("*I took off and dropped my " + item.getName() + ".*", character);
        WebhookManager.sendOthers("*" + character.getDisplayName() + " took off and dropped their " + item.getName() + ".*", character);
        return null;

    }

}

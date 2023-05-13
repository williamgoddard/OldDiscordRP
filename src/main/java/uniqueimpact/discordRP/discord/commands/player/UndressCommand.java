package uniqueimpact.discordRP.discord.commands.player;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import uniqueimpact.discordRP.discord.commands.Command;
import uniqueimpact.discordRP.discord.utils.WebhookManager;
import uniqueimpact.discordRP.things.Item;
import uniqueimpact.discordRP.things.Player;
import uniqueimpact.discordRP.things.Room;
import uniqueimpact.discordRP.utils.InvalidInputException;

public class UndressCommand implements Command {

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

        if (item.getWeight() > character.getInv().getRemainingCapacity()) {
            WebhookManager.sendSelf("*I can't take off my " + item.getName() + " because I would be holding too much.*", character);
            return null;
        }

        character.getInv().getItems().add(item);
        character.getClothes().getItems().remove(item);

        WebhookManager.sendSelf("*I took off my " + item.getName() + ".*", character);
        WebhookManager.sendOthers("*" + character.getDisplayName() + " took off their " + item.getName() + ".*", character);
        return null;

    }

}

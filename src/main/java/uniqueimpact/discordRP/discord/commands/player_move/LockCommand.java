package uniqueimpact.discordRP.discord.commands.player_move;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import uniqueimpact.discordRP.discord.commands.Command;
import uniqueimpact.discordRP.discord.utils.WebhookManager;
import uniqueimpact.discordRP.things.*;
import uniqueimpact.discordRP.utils.InvalidInputException;

import java.util.List;

public class LockCommand implements Command {

    @Override
    public String run(SlashCommandInteractionEvent command) {

        String roomName = command.getOption("room").getAsString();
        Integer roomNum = command.getOption("num") != null ? command.getOption("num").getAsInt() : 1;

        String channelId = command.getChannel().getId();
        Chara character;
        try {
            character = roleplay.findPlayerByChannel(channelId);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        Room room = character.getRoom();

        Door door;
        try {
            door = roleplay.findSpecificRoomDoor(room, roomName, roomNum, false, false);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        Inventory inv = character.getInv();
        List<Item> items = inv.getItems();

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if (!item.getKey().equals("") && item.getKey().equals(door.getKeyword())) {
                door.setLocked(true);
                WebhookManager.sendOthers("*" + character.getDisplayName() + " locks the door to the " + roomName + " with their " + item.getName() + ".", character);
                return "You lock the door to the `" + roomName + "` with the `" + item.getName() + "`.";
            }
        }

        return "You don't have anything that can lock the door to the `" + roomName + "`.";

    }

}

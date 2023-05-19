package uniqueimpact.discordRP.discord.commands.player_move;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import uniqueimpact.discordRP.discord.commands.Command;
import uniqueimpact.discordRP.discord.utils.WebhookManager;
import uniqueimpact.discordRP.things.*;
import uniqueimpact.discordRP.utils.InvalidInputException;

import java.util.List;

public class UnlockCommand implements Command {

    @Override
    public String run(SlashCommandInteractionEvent command) {

        String roomName = command.getOption("room").getAsString();
        Integer roomNum = command.getOption("num") != null ? command.getOption("num").getAsInt() : 1;

        String channelId = command.getChannel().getId();
        Chara character;
        try {
            character = roleplay.findCharacterByChannel(channelId);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        Room room = character.getRoom();

        Door door;
        try {
            door = room.findDoor(roomName, roomNum, true, false);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        Inventory inv = character.getInv();
        List<Item> items = inv.getItems();

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if (!item.getKeyword().equals("") && item.getKeyword().equals(door.getKeyword())) {
                door.setLocked(false);
                WebhookManager.sendOthers("*" + character.getDisplayName() + " unlocks the door to the " + roomName + " with their " + item.getName() + ".*", character);
                return "You unlock the door to the `" + roomName + "` with the `" + item.getName() + "`.";
            }
        }

        return "You don't have anything that can unlock the door to the `" + roomName + "`.";

    }

}

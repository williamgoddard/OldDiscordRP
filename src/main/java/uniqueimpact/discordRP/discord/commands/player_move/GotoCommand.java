package uniqueimpact.discordRP.discord.commands.player_move;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import uniqueimpact.discordRP.discord.commands.Command;
import uniqueimpact.discordRP.discord.utils.DiscordOutputGenerator;
import uniqueimpact.discordRP.discord.utils.WebhookManager;
import uniqueimpact.discordRP.things.Door;
import uniqueimpact.discordRP.things.Player;
import uniqueimpact.discordRP.things.Room;
import uniqueimpact.discordRP.utils.InvalidInputException;

import java.util.List;

public class GotoCommand implements Command {

    @Override
    public String run(SlashCommandInteractionEvent command) {

        String roomName = command.getOption("room") != null ? command.getOption("room").getAsString() : null;
        Integer roomNum = command.getOption("num") != null ? command.getOption("num").getAsInt() : 1;

        String channelId = command.getChannel().getId();
        Player character;
        try {
            character = roleplay.findPlayerByChannel(channelId);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        Room room = character.getRoom();

        if (roomName == null) {

            List<Door> uDoors = room.getSpecificDoors(false, false);
            List<Door> lDoors = room.getSpecificDoors(true, false);

            String outputString = "You look for places you can go.\n";
            if (uDoors.size() > 0) {
                outputString += "I can go to these places from here:\n" + DiscordOutputGenerator.convertDoorList(uDoors, room, 1000) + "\n";
            } else {
                outputString += "I can't go anywhere from here.\n";
            }
            if (lDoors.size() > 0) {
                outputString += "The doors to these places are locked:\n" + DiscordOutputGenerator.convertDoorList(lDoors, room, 800);
            }

            return outputString;

        }

        Door door;
        try {
            door = roleplay.findSpecificRoomDoor(room, roomName, roomNum, false, false);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        Room targetRoom = door.getOtherRoom(room);

        WebhookManager.sendOthers("*" + character.getDisplayName() + " leaves to the " + targetRoom.getName() + ".*", character);

        targetRoom.getPlayers().add(character);
        character.setRoom(targetRoom);
        room.getPlayers().remove(character);

        WebhookManager.sendOthers("*" + character.getDisplayName() + " enters from the " + room.getName() + ".*", character);

        List<Player> players = targetRoom.getPlayers(false);
        if (players.size() > 1) {
            return "You go to the " + targetRoom.getName() + ". You see these people here:\n" + DiscordOutputGenerator.convertPlayerList(players, 1900);
        } else {
            return "You go to the " + targetRoom.getName() + ". You don't see anyone else here.";
        }

    }

}

package uniqueimpact.discordRP.discord.commands.player_move;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import uniqueimpact.discordRP.discord.commands.Command;
import uniqueimpact.discordRP.discord.utils.DiscordOutputGenerator;
import uniqueimpact.discordRP.discord.utils.WebhookManager;
import uniqueimpact.discordRP.things.Door;
import uniqueimpact.discordRP.things.Chara;
import uniqueimpact.discordRP.things.Room;
import uniqueimpact.discordRP.utils.InvalidInputException;

import java.util.List;

public class GotoCommand implements Command {

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
            door = room.findDoor(roomName, roomNum, false, false);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        Room targetRoom;
        try {
            targetRoom = door.getOtherRoom(room);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        WebhookManager.sendOthers("*" + character.getDisplayName() + " leaves to the " + targetRoom.getName() + ".*", character);

        targetRoom.addCharacter(character);
        character.setRoom(targetRoom);
        room.delCharacter(character);

        WebhookManager.sendOthers("*" + character.getDisplayName() + " enters from the " + room.getName() + ".*", character);

        List<Chara> players = targetRoom.getCharacters(false);
        if (players.size() > 1) {
            return "You go to the " + targetRoom.getName() + ". You see these people here:\n" + DiscordOutputGenerator.convertPlayerList(players, 1900);
        } else {
            return "You go to the " + targetRoom.getName() + ". You don't see anyone else here.";
        }

    }

}

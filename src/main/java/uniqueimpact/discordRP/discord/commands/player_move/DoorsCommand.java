package uniqueimpact.discordRP.discord.commands.player_move;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import uniqueimpact.discordRP.discord.commands.Command;
import uniqueimpact.discordRP.discord.utils.DiscordOutputGenerator;
import uniqueimpact.discordRP.discord.utils.WebhookManager;
import uniqueimpact.discordRP.things.Chara;
import uniqueimpact.discordRP.things.Door;
import uniqueimpact.discordRP.things.Room;
import uniqueimpact.discordRP.utils.InvalidInputException;

import java.util.List;

public class DoorsCommand implements Command {

    @Override
    public MessageCreateData run(SlashCommandInteractionEvent command) {
        return new MessageCreateBuilder().setContent(runCommand(command)).build();
    }

    private String runCommand(SlashCommandInteractionEvent command) {

        String channelId = command.getChannel().getId();
        Chara character;
        try {
            character = roleplay.findCharacterByChannel(channelId);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        Room room = character.getRoom();

        List<Door> uDoors = room.getDoors(false, false);
        List<Door> lDoors = room.getDoors(true, false);

        String outputString = "You look for places you can go.\n";
        if (uDoors.size() > 0) {
            outputString += "You can go to these places from here:\n" + DiscordOutputGenerator.convertDoorList(uDoors, room, 1000) + "\n";
        } else {
            outputString += "You can't go anywhere from here.\n";
        }
        if (lDoors.size() > 0) {
            outputString += "These doors are locked:\n" + DiscordOutputGenerator.convertDoorList(lDoors, room, 800);
        }

        return outputString;

    }

}

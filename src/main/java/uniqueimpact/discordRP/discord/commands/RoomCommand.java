package uniqueimpact.discordRP.discord.commands;

import me.wgoddard.utilities.RoomUtils;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class RoomCommand implements Command {

    @Override
    public String run(SlashCommandInteractionEvent command) {

        String path = command.getFullCommandName();

        switch (path) {
            case "room create":
                return create(command);
            case "room list":
                return list(command);
            case "room look":
                return look(command);
            case "room edit":
                return edit(command);
            case "room delete":
                return delete(command);
            default:
                return "Error: Invalid command path (" + path + ")";
        }

    }

    private String create(SlashCommandInteractionEvent command) {
        String guildId = command.getGuild().getId();
        String room = command.getOption("room").getAsString();
        return RoomUtils.create(guildId, room, "name").toString();
    }

    private String list(SlashCommandInteractionEvent command) {
        String guildId = command.getGuild().getId();
        return RoomUtils.getAll(guildId, "name").toString();
    }

    private String look(SlashCommandInteractionEvent command) {
        String guildId = command.getGuild().getId();
        String room = command.getOption("room").getAsString();
        int num = (command.getOption("num") != null) ? command.getOption("num").getAsInt() : 1;
        return RoomUtils.get(guildId, room, num, "name").toString();
    }

    private String edit(SlashCommandInteractionEvent command) {
        String guildId = command.getGuild().getId();
        String room = command.getOption("room").getAsString();
        int num = (command.getOption("num") != null) ? command.getOption("num").getAsInt() : 1;
        String newName = (command.getOption("name") != null)
                ? command.getOption("name").getAsString() : null;
        return RoomUtils.edit(guildId, room, num, newName, "name").toString();
    }

    private String delete(SlashCommandInteractionEvent command) {
        String guildId = command.getGuild().getId();
        String room = command.getOption("room").getAsString();
        int num = (command.getOption("num") != null) ? command.getOption("num").getAsInt() : 1;
        return RoomUtils.delete(guildId, room, num).toString();
    }

}

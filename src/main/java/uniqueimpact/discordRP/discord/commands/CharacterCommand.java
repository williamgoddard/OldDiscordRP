package uniqueimpact.discordRP.discord.commands;

import com.google.gson.JsonObject;
import me.wgoddard.utilities.CharacterUtils;
import me.wgoddard.utilities.RoomUtils;
import me.wgoddard.utilities.WebhookUtils;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class CharacterCommand implements Command {

    @Override
    public String run(SlashCommandInteractionEvent command) {

        String path = command.getFullCommandName();

        switch (path) {
            case "character create":
                return create(command);
            case "character list":
                return list(command);
            case "character look":
                return look(command);
            case "character edit":
                return edit(command);
            case "character move":
                return move(command);
            case "character delete":
                return delete(command);
            default:
                return "Error: Invalid command path (" + path + ")";
        }

    }

    private String create(SlashCommandInteractionEvent command) {
        String guildId = command.getGuild().getId();
        String chara = command.getOption("character").getAsString();
        String channelId = command.getOption("channel").getAsChannel().getId();
        String room = command.getOption("room").getAsString();
        int roomNum = (command.getOption("roomnum") != null) ? command.getOption("roomnum").getAsInt() : 1;
        JsonObject roomJson = RoomUtils.get(guildId, room, roomNum, "")
                .getAsJsonObject("data").getAsJsonObject("room");
        if (!roomJson.get("success").getAsBoolean()) {
            return roomJson.get("errors").getAsJsonArray().get(0).getAsString();
        }
        int roomId = roomJson.getAsJsonObject("room").get("id").getAsInt();
        String webhook = WebhookUtils.createOrGetWebhook(command.getOption("channel").getAsChannel().asTextChannel());
        return CharacterUtils.create(guildId, chara, channelId, webhook, roomId, "name").toString();
    }

    private String list(SlashCommandInteractionEvent command) {
        String guildId = command.getGuild().getId();
        return CharacterUtils.getAll(guildId, "name").toString();
    }

    private String look(SlashCommandInteractionEvent command) {
        String guildId = command.getGuild().getId();
        String chara = command.getOption("character").getAsString();
        return CharacterUtils.get(guildId, chara, "name").toString();
    }

    private String edit(SlashCommandInteractionEvent command) {
        String guildId = command.getGuild().getId();
        String chara = command.getOption("character").getAsString();
        String newName = (command.getOption("name") != null)
                ? command.getOption("name").getAsString() : null;
        String channel = (command.getOption("channel") != null)
                ? command.getOption("channel").getAsChannel().getId() : null;
        String webhook = (channel != null) ?
                WebhookUtils.createOrGetWebhook(command.getOption("channel").getAsChannel().asTextChannel())
                : null;
        return CharacterUtils.edit(guildId, chara, newName, channel, webhook, -1, "name").toString();
    }

    private String move(SlashCommandInteractionEvent command) {
        String guildId = command.getGuild().getId();
        String chara = command.getOption("character").getAsString();
        String room = command.getOption("room").getAsString();
        int num = (command.getOption("num") != null) ? command.getOption("num").getAsInt() : 1;
        JsonObject roomJson = RoomUtils.get(guildId, room, num, "")
                .getAsJsonObject("data").getAsJsonObject("room");
        if (!roomJson.get("success").getAsBoolean()) {
            return roomJson.get("errors").getAsJsonArray().get(0).getAsString();
        }
        int roomId = roomJson.getAsJsonObject("room").get("id").getAsInt();
        return CharacterUtils.edit(guildId, chara, null, null, null, roomId, "name").toString();
    }

    private String delete(SlashCommandInteractionEvent command) {
        String guildId = command.getGuild().getId();
        String chara = command.getOption("character").getAsString();
        return CharacterUtils.delete(guildId, chara).toString();
    }

}

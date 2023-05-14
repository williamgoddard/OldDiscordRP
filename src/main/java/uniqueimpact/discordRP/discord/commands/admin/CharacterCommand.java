package uniqueimpact.discordRP.discord.commands.admin;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import uniqueimpact.discordRP.discord.commands.Command;
import uniqueimpact.discordRP.discord.utils.AdminChecker;
import uniqueimpact.discordRP.discord.utils.DiscordOutputGenerator;
import uniqueimpact.discordRP.discord.utils.WebhookManager;
import uniqueimpact.discordRP.things.Inventory;
import uniqueimpact.discordRP.things.Player;
import uniqueimpact.discordRP.things.Room;
import uniqueimpact.discordRP.utils.InvalidInputException;

public class CharacterCommand implements Command {

    @Override
    public String run(SlashCommandInteractionEvent command) {

        if (!AdminChecker.isAdmin(command.getMember())) {
            return "You do not have permission to use this command.";
        }

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

        String name = command.getOption("name").getAsString();
        TextChannel channel = command.getOption("channel").getAsChannel().asTextChannel();
        String roomName = command.getOption("room").getAsString();
        Integer roomNum = command.getOption("room_num") != null ? command.getOption("room_num").getAsInt() : 1;
        String displayName = command.getOption("display_name") != null ? command.getOption("display_name").getAsString() : null;
        String picture = command.getOption("picture") != null ? command.getOption("picture").getAsString() : null;
        String description = command.getOption("description") != null ? command.getOption("description").getAsString() : null;
        Double itemsCapacity = (command.getOption("items_capacity") != null) ? command.getOption("items_capacity").getAsDouble() : 0.0;
        Double clothesCapacity = (command.getOption("clothes_capacity") != null) ? command.getOption("clothes_capacity").getAsDouble() : 0.0;
        Boolean hidden = (command.getOption("hidden") != null) ? command.getOption("hidden").getAsBoolean() : false;

        try {
            roleplay.findPlayer(name);
            return "A character with that name already exists.";
        } catch (InvalidInputException ignored) {}

        String channelId = channel.getId();
        String webhook = WebhookManager.createOrGetWebhook(channel);

        Room room;
        try {
            room = roleplay.findRoom(roomName, roomNum);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        Inventory inv;
        try {
            inv = new Inventory(itemsCapacity);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        Inventory clothes;
        try {
            clothes = new Inventory(clothesCapacity);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        Player player;
        try {
            player = new Player(channelId, webhook, name, displayName, picture, description, hidden, inv, clothes, room);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        room.getPlayers().add(player);
        roleplay.getPlayers().add(player);

        return "The character was added successfully.";

    }

    private String list(SlashCommandInteractionEvent command) {

        String roomName = command.getOption("room") != null ? command.getOption("room").getAsString() : null;
        Integer roomNum = command.getOption("room_num") != null ? command.getOption("room_num").getAsInt() : 1;

        if (roomName == null) {
            return "Characters:\n" + DiscordOutputGenerator.convertPlayerList(roleplay.getPlayers(), 1800);
        }

        Room room;
        try {
            room = roleplay.findRoom(roomName, roomNum);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        return "Characters in room `" + roomName + "`:\n" + DiscordOutputGenerator.convertPlayerList(room.getPlayers(), 1800);

    }

    private String look(SlashCommandInteractionEvent command) {

        String name = command.getOption("character").getAsString();

        Player player;
        try {
            player = roleplay.findPlayer(name);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        return DiscordOutputGenerator.convertPlayerAdmin(player);

    }

    private String edit(SlashCommandInteractionEvent command) {

        String name = command.getOption("character").getAsString();
        String newName = command.getOption("name") != null ? command.getOption("name").getAsString() : null;
        TextChannel channel = command.getOption("channel") != null ? command.getOption("channel").getAsChannel().asTextChannel() : null;
        String displayName = command.getOption("display_name") != null ? command.getOption("display_name").getAsString() : null;
        String picture = command.getOption("picture") != null ? command.getOption("picture").getAsString() : null;
        String description = command.getOption("description") != null ? command.getOption("description").getAsString() : null;
        Double itemsCapacity = (command.getOption("items_capacity") != null) ? command.getOption("items_capacity").getAsDouble() : null;
        Double clothesCapacity = (command.getOption("clothes_capacity") != null) ? command.getOption("clothes_capacity").getAsDouble() : null;
        Boolean hidden = (command.getOption("hidden") != null) ? command.getOption("hidden").getAsBoolean() : false;

        Player player;
        try {
            player = roleplay.findPlayer(name);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        if (channel != null) {
            String channelId = channel.getId();
            String webhook = WebhookManager.createOrGetWebhook(channel);

            try {
                player.edit(channelId, webhook, newName, displayName, picture, description, hidden, itemsCapacity, clothesCapacity);
            } catch (InvalidInputException e) {
                return e.getMessage();
            }
        }

        try {
            player.edit(null, null, newName, displayName, picture, description, hidden, itemsCapacity, clothesCapacity);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        return "The character was edited successfully.";

    }

    private String move(SlashCommandInteractionEvent command) {

        String name = command.getOption("character").getAsString();
        String roomName = command.getOption("room").getAsString();
        Integer roomNum = command.getOption("room_num") != null ? command.getOption("room_num").getAsInt() : 1;

        Player player;
        try {
            player = roleplay.findPlayer(name);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        Room room;
        try {
            room = roleplay.findRoom(roomName, roomNum);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        player.setRoom(room);

        return "The character was moved successfully.";

    }

    private String delete(SlashCommandInteractionEvent command) {

        String name = command.getOption("character").getAsString();

        Player player;
        try {
            player = roleplay.findPlayer(name);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        roleplay.getRooms().remove(player);

        return  "The character was deleted successfully.";

    }

}

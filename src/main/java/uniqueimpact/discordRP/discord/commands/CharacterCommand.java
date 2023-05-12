package uniqueimpact.discordRP.discord.commands;

import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
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
        Integer roomNum = command.getOption("roomNum") != null ? command.getOption("roomNum").getAsInt() : 1;
        String displayName = command.getOption("displayName") != null ? command.getOption("displayName").getAsString() : null;
        String picture = command.getOption("picture") != null ? command.getOption("picture").getAsString() : null;
        String description = command.getOption("description") != null ? command.getOption("description").getAsString() : null;
        Double itemsCapacity = (command.getOption("itemsCapacity") != null) ? command.getOption("itemsCapacity").getAsDouble() : 0.0;
        Double clothesCapacity = (command.getOption("clothesCapacity") != null) ? command.getOption("clothesCapacity").getAsDouble() : 0.0;
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
        Integer roomNum = command.getOption("roomNum") != null ? command.getOption("roomNum").getAsInt() : 1;

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
        String displayName = command.getOption("displayName") != null ? command.getOption("displayName").getAsString() : null;
        String picture = command.getOption("picture") != null ? command.getOption("picture").getAsString() : null;
        String description = command.getOption("description") != null ? command.getOption("description").getAsString() : null;
        Double itemsCapacity = (command.getOption("itemsCapacity") != null) ? command.getOption("itemsCapacity").getAsDouble() : null;
        Double clothesCapacity = (command.getOption("clothesCapacity") != null) ? command.getOption("clothesCapacity").getAsDouble() : null;
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


        Player player = roleplay.findPlayer(command[1]);
        switch (command[2]) {
            case "channel":
                if (command.length >= 4) {
                    try {
                        roleplay.findPlayerByChannel(command[3]);
                        channel.sendMessage("Discord channel is already linked to a player.").queue();
                        return;
                    } catch (InvalidInputException e) {}
                    player.setChannel(command[3]);
                } else {
                    channel.sendMessage("When the parameter is `channel`, you must enter a value.").queue();
                    return;
                }
                break;
            case "webhook":
                if (command.length >= 4) {
                    player.setWebhook(command[3]);
                } else {
                    channel.sendMessage("When the parameter is `webhook`, you must enter a value.").queue();
                    return;
                }
                break;
            case "name":
                if (command.length >= 4) {
                    player.setName((command[3].equals("none")) ? "" : command[3]);
                } else {
                    channel.sendMessage("When the parameter is `name`, you must enter a value.").queue();
                    return;
                }
                break;
            case "display-name":
                if (command.length >= 4) {
                    String newDisplayName = command[3];
                    for (int i = 4; i < command.length; i++) {
                        newDisplayName += " " + command[i];
                    }
                    player.setDisplayName(newDisplayName);
                } else {
                    channel.sendMessage("When the parameter is `display-name`, you must enter a value.").queue();
                    return;
                }
                break;
            case "hidden":
                if (command.length >= 4) {
                    if (!(command[3].equals("true") || command[3].equals("false"))) {
                        channel.sendMessage("Hidden must be either true or false.").queue();
                        return;
                    } else {
                        player.setHidden(Boolean.parseBoolean(command[3]));
                    }
                } else {
                    channel.sendMessage("When the parameter is `hidden`, you must enter a value.").queue();
                    return;
                }
                break;
            case "max-items":
                if (command.length >= 4) {
                    double maxItems;
                    try {
                        maxItems = Double.parseDouble(command[3]);
                    } catch (NumberFormatException e) {
                        channel.sendMessage("Maximum items capacity must be a number.").queue();
                        return;
                    }
                    player.getInv().setCapacity(maxItems);
                } else {
                    channel.sendMessage("When the parameter is `max-items`, you must enter a value.").queue();
                    return;
                }
                break;
            case "max-clothes":
                if (command.length >= 4) {
                    double maxClothes;
                    try {
                        maxClothes = Double.parseDouble(command[3]);
                    } catch (NumberFormatException e) {
                        channel.sendMessage("Maximum clothes capacity must be a number.").queue();
                        return;
                    }
                    player.getClothes().setCapacity(maxClothes);
                } else {
                    channel.sendMessage("When the parameter is `max-clothes`, you must enter a value.").queue();
                    return;
                }
                break;
            case "picture":
                if (command.length >= 4) {
                    player.setPicture(command[3]);
                } else {
                    player.setPicture("");
                }
                break;
            case "desc":
                String desc = "";
                if (command.length >= 4) {
                    desc = command[3];
                    for (int i = 4; i < command.length; i++) {
                        desc += " " + command[i];
                    }
                }
                player.setDescription(desc);
                break;
            default:
                channel.sendMessage("Invalid parameter. Valid parameters are: `channel` `webhook` `name` `display-name` `gender` `height` `weight` `hidden` `max-items` `max-clothes` `picture` `desc`.").queue();
                return;
        }


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

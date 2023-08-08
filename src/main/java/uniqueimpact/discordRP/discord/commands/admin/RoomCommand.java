package uniqueimpact.discordRP.discord.commands.admin;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import uniqueimpact.discordRP.discord.commands.Command;
import uniqueimpact.discordRP.discord.utils.AdminChecker;
import uniqueimpact.discordRP.discord.utils.DiscordOutputGenerator;
import uniqueimpact.discordRP.things.Room;
import uniqueimpact.discordRP.utils.InvalidInputException;

import java.util.List;

public class RoomCommand implements Command {

    @Override
    public void run(SlashCommandInteractionEvent command) {

        if (!AdminChecker.isAdmin(command.getMember())) {
            command.reply("You do not have permission to use this command.").queue();
            return;
        }

        String path = command.getFullCommandName();
        String response;

        switch (path) {
            case "room create":
                response = create(command);
                break;
            case "room list":
                response =  list(command);
                break;
            case "room look":
                response =  look(command);
                break;
            case "room edit":
                response =  edit(command);
                break;
            case "room delete":
                response =  delete(command);
                break;
            default:
                response =  "Error: Invalid command path (" + path + ")";
        }

        command.reply(response).queue();

    }

    private String create(SlashCommandInteractionEvent command) {

        String name = command.getOption("name").getAsString();
        String desc = command.getOption("description").getAsString();
        Double capacity = command.getOption("capacity") != null ? command.getOption("capacity").getAsDouble() : 0.0;

        Room room;
        try {
            room = new Room(name, desc, capacity);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        roleplay.addRoom(room);
        return "The room was created successfully.";

    }

    private String list(SlashCommandInteractionEvent command) {

        List<Room> rooms = roleplay.getRooms();

        if (rooms.size() == 0) {
            return "No rooms are currently registered.";
        }

        return "List of rooms registered to the roleplay:\n" + DiscordOutputGenerator.convertRoomList(rooms, 1900);

    }

    private String look(SlashCommandInteractionEvent command) {

        String name = command.getOption("room").getAsString();
        int num = (command.getOption("num") != null) ? command.getOption("num").getAsInt() : 1;

        Room room;
        try {
            room = roleplay.findRoom(name, num);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        return DiscordOutputGenerator.convertRoomAdmin(room);

    }

    private String edit(SlashCommandInteractionEvent command) {

        String name = command.getOption("room").getAsString();
        int num = (command.getOption("num") != null) ? command.getOption("num").getAsInt() : 1;

        String newName = (command.getOption("name") != null) ? command.getOption("name").getAsString() : null;
        String description = (command.getOption("description") != null) ? command.getOption("description").getAsString() : null;
        Double capacity = (command.getOption("capacity") != null) ? command.getOption("capacity").getAsDouble() : null;

        Room room;
        try {
            room = roleplay.findRoom(name, num);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        String response = "";

        if (newName != null) {
            try {
                room.setName(newName);
                roleplay.delRoom(room);
                roleplay.addRoom(room);
                response += "The room's name was edited successfully.\n";
            } catch (InvalidInputException e) {
                response += "The room's name was not edited: " + e.getMessage() + "\n";
            }
        }

        if (description != null) {
            try {
                room.setDescription(description);
                response += "The room's description was edited successfully.\n";
            } catch (InvalidInputException e) {
                response += "The room's description was not edited: " + e.getMessage() + "\n";
            }
        }

        if (capacity != null) {
            try {
                room.getInv().setCapacity(capacity);
                response += "The room's capacity was edited successfully.\n";
            } catch (InvalidInputException e) {
                response += "The room's capacity was not edited: " + e.getMessage() + "\n";
            }
        }

        if (response.equals("")) {
            return "The room was not edited: At least one field must be selected for editing.";
        }

        return response;

    }

    private String delete(SlashCommandInteractionEvent command) {

        String name = command.getOption("room").getAsString();
        int num = (command.getOption("num") != null) ? command.getOption("num").getAsInt() : 1;

        Room room;
        try {
            room = roleplay.findRoom(name, num);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        try {
            roleplay.delRoom(room);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        return "The room was deleted successfully.";

    }

}

package uniqueimpact.discordRP.discord.commands.admin;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import uniqueimpact.discordRP.discord.commands.Command;
import uniqueimpact.discordRP.discord.utils.AdminChecker;
import uniqueimpact.discordRP.discord.utils.DiscordOutputGenerator;
import uniqueimpact.discordRP.things.Door;
import uniqueimpact.discordRP.things.Room;
import uniqueimpact.discordRP.utils.InvalidInputException;

public class DoorCommand implements Command {

    public void run(SlashCommandInteractionEvent command) {

        if (!AdminChecker.isAdmin(command.getMember())) {
            command.reply("You do not have permission to use this command.").queue();
            return;
        }

        String path = command.getFullCommandName();
        String response;

        switch (path) {
            case "door create":
                response = create(command);
                break;
            case "door list":
                response =  list(command);
                break;
            case "door look":
                response =  look(command);
                break;
            case "door edit":
                response =  edit(command);
                break;
            case "door delete":
                response =  delete(command);
                break;
            default:
                response = "Error: Invalid command path (" + path + ")";
        }

        command.reply(response).queue();

    }

    private String create(SlashCommandInteractionEvent command) {

        String room1Name = command.getOption("room1").getAsString();
        String room2Name = command.getOption("room2").getAsString();
        Integer room1Num = command.getOption("room1_num") != null ? command.getOption("room1_num").getAsInt() : 1;
        Integer room2Num = command.getOption("room2_num") != null ? command.getOption("room2_num").getAsInt() : 1;
        Boolean hidden = (command.getOption("hidden") != null) ? command.getOption("hidden").getAsBoolean() : false;
        Boolean locked = (command.getOption("locked") != null) ? command.getOption("locked").getAsBoolean() : false;
        String keyword = command.getOption("keyword") != null ? command.getOption("keyword").getAsString() : null;

        Room room1;
        try {
            room1 = roleplay.findRoom(room1Name, room1Num);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        Room room2;
        try {
            room2 = roleplay.findRoom(room2Name, room2Num);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        Door door;
        try {
            door = new Door(room1, room2, hidden, locked, keyword);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        room1.addDoor(door);
        room2.addDoor(door);

        return "The door was created successfully.";

    }

    private String list(SlashCommandInteractionEvent command) {

        String roomName = command.getOption("room").getAsString();
        Integer roomNum = command.getOption("room_num") != null ? command.getOption("room_num").getAsInt() : 1;

        Room room;
        try {
            room = roleplay.findRoom(roomName, roomNum);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        return "List of doors in room `" + room.getName() + "`:\n" + DiscordOutputGenerator.convertDoorList(room.getDoors(), room, 1900);

    }

    private String look(SlashCommandInteractionEvent command) {

        String room1Name = command.getOption("room1").getAsString();
        String room2Name = command.getOption("room2").getAsString();
        Integer room1Num = command.getOption("room1_num") != null ? command.getOption("room1_num").getAsInt() : 1;
        Integer room2Num = command.getOption("room2_num") != null ? command.getOption("room2_num").getAsInt() : 1;

        Room room1;
        try {
            room1 = roleplay.findRoom(room1Name, room1Num);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        Room room2;
        try {
            room2 = roleplay.findRoom(room2Name, room2Num);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        Door door;
        try {
            door = room1.findDoor(room2);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        return DiscordOutputGenerator.convertDoorAdmin(door);

    }

    private String edit(SlashCommandInteractionEvent command) {

        String room1Name = command.getOption("room1").getAsString();
        String room2Name = command.getOption("room2").getAsString();
        Integer room1Num = command.getOption("room1_num") != null ? command.getOption("room1_num").getAsInt() : 1;
        Integer room2Num = command.getOption("room2_num") != null ? command.getOption("room2_num").getAsInt() : 1;
        Boolean hidden = command.getOption("hidden") != null ? command.getOption("hidden").getAsBoolean() : null;
        Boolean locked = command.getOption("locked") != null ? command.getOption("locked").getAsBoolean() : null;
        String keyword = command.getOption("keyword") != null ? command.getOption("keyword").getAsString() : null;

        Room room1;
        try {
            room1 = roleplay.findRoom(room1Name, room1Num);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        Room room2;
        try {
            room2 = roleplay.findRoom(room2Name, room2Num);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        Door door;
        try {
            door = room1.findDoor(room2);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        String response = "";

        if (hidden != null) {
            door.setHidden(hidden);
            response += "The door was set to be hidden successfully.\n";
        }

        if (locked != null) {
            door.setLocked(locked);
            response += "The door's locked status was edited successfully.\n";
        }

        if (keyword != null) {
            try {
                door.setKeyword(keyword);
                response += "The door keyword was edited successfully.\n";
            } catch (InvalidInputException e) {
                response += "The door's keyword was not edited: " + e.getMessage() + "\n";
            }
        }

        if (response.equals("")) {
            return "The door was not edited: At least one field must be selected for editing.";
        }

        return response;

    }

    private String delete(SlashCommandInteractionEvent command) {

        String room1Name = command.getOption("room1").getAsString();
        String room2Name = command.getOption("room2").getAsString();
        Integer room1Num = command.getOption("room1_num") != null ? command.getOption("room1_num").getAsInt() : 1;
        Integer room2Num = command.getOption("room2_num") != null ? command.getOption("room2_num").getAsInt() : 1;

        Room room1;
        try {
            room1 = roleplay.findRoom(room1Name, room1Num);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        Room room2;
        try {
            room2 = roleplay.findRoom(room2Name, room2Num);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        Door door;
        try {
            door = room1.findDoor(room2);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        room1.delDoor(door);
        room2.delDoor(door);

        return "The door was deleted successfully.";

    }

}

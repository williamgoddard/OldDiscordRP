package uniqueimpact.discordRP.discord.commands.admin;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import uniqueimpact.discordRP.discord.commands.Command;
import uniqueimpact.discordRP.discord.utils.AdminChecker;
import uniqueimpact.discordRP.discord.utils.DiscordOutputGenerator;
import uniqueimpact.discordRP.things.Door;
import uniqueimpact.discordRP.things.Room;
import uniqueimpact.discordRP.utils.InvalidInputException;

public class DoorCommand implements Command {

    public String run(SlashCommandInteractionEvent command) {

        if (!AdminChecker.isAdmin(command.getMember())) {
            return "You do not have permission to use this command.";
        }

        String path = command.getFullCommandName();

        switch (path) {
            case "door create":
                return create(command);
            case "door list":
                return list(command);
            case "door look":
                return look(command);
            case "door edit":
                return edit(command);
            case "door delete":
                return delete(command);
            default:
                return "Error: Invalid command path (" + path + ")";
        }

    }

    private String create(SlashCommandInteractionEvent command) {

        String room1Name = command.getOption("room1").getAsString();
        String room2Name = command.getOption("room2").getAsString();
        Integer room1Num = command.getOption("room1Num") != null ? command.getOption("room1Num").getAsInt() : 1;
        Integer room2Num = command.getOption("room2Num") != null ? command.getOption("room2Num").getAsInt() : 1;
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

        room1.getDoors().add(door);
        room2.getDoors().add(door);

        return "The door was created successfully.";

    }

    private String list(SlashCommandInteractionEvent command) {

        String roomName = command.getOption("room").getAsString();
        Integer roomNum = command.getOption("roomNum") != null ? command.getOption("roomNum").getAsInt() : 1;

        Room room;
        try {
            room = roleplay.findRoom(roomName, roomNum);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        return "List of doors in room `" + roomName + "`:\n" + DiscordOutputGenerator.convertDoorList(room.getDoors(), room, 1900);

    }

    private String look(SlashCommandInteractionEvent command) {

        String room1Name = command.getOption("room1").getAsString();
        String room2Name = command.getOption("room2").getAsString();
        Integer room1Num = command.getOption("room1Num") != null ? command.getOption("room1Num").getAsInt() : 1;
        Integer room2Num = command.getOption("room2Num") != null ? command.getOption("room2Num").getAsInt() : 1;

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
        Integer room1Num = command.getOption("room1Num") != null ? command.getOption("room1Num").getAsInt() : null;
        Integer room2Num = command.getOption("room2Num") != null ? command.getOption("room2Num").getAsInt() : null;
        Boolean hidden = (command.getOption("hidden") != null) ? command.getOption("hidden").getAsBoolean() : null;
        Boolean locked = (command.getOption("locked") != null) ? command.getOption("locked").getAsBoolean() : null;
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

        try {
            door.edit(hidden, locked, keyword);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        return "The door was edited successfully.";

    }

    private String delete(SlashCommandInteractionEvent command) {

        String room1Name = command.getOption("room1").getAsString();
        String room2Name = command.getOption("room2").getAsString();
        Integer room1Num = command.getOption("room1Num") != null ? command.getOption("room1Num").getAsInt() : 1;
        Integer room2Num = command.getOption("room2Num") != null ? command.getOption("room2Num").getAsInt() : 1;

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

        room1.getDoors().remove(door);
        room2.getDoors().remove(door);

        return "The door was deleted successfully.";

    }

}

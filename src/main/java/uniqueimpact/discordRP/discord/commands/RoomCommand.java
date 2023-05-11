package uniqueimpact.discordRP.discord.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import uniqueimpact.discordRP.discord.utils.AdminChecker;
import uniqueimpact.discordRP.discord.utils.DiscordOutputGenerator;
import uniqueimpact.discordRP.things.Door;
import uniqueimpact.discordRP.things.Inventory;
import uniqueimpact.discordRP.things.Room;
import uniqueimpact.discordRP.utils.InvalidInputException;

public class RoomCommand implements Command {

    @Override
    public String run(SlashCommandInteractionEvent command) {

        if (!AdminChecker.isAdmin(command.getMember())) {
            return "You do not have permission to use this command.";
        }

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

        String name = command.getOption("room").getAsString();
        String desc = (command.getOption("description") != null) ? command.getOption("description").getAsString() : null;
        Double capacity = (command.getOption("capacity") != null) ? command.getOption("capacity").getAsDouble() : null;

        Inventory inv;
        try {
            inv = new Inventory(capacity);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        Room room;
        try {
            room = new Room(name, desc, inv);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        roleplay.getRooms().add(room);
        return "The room was created successfully.";

    }

    private String list(SlashCommandInteractionEvent command) {

        return "List of rooms:\n" + DiscordOutputGenerator.convertRoomList(roleplay.getRooms(), 1900);

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
        Double capacity = (command.getOption("capacity") != null) ? command.getOption("name").getAsDouble() : null;

        Room room;
        try {
            room = roleplay.findRoom(name, num);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        try {
            room.edit(newName, description, capacity);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        return "The room was edited successfully.";

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

        if (room.getPlayers().size() > 0) {
            return "The room could not be deleted because it contains characters.";
        }

        roleplay.getRooms().remove(room);

        for (int i = 0; i < room.getDoors().size(); i++) {
            Door door = room.getDoors().get(i);
            Room otherRoom = door.getOtherRoom(room);
            otherRoom.getDoors().remove(door);
        }

        return "The room was deleted successfully.";

    }

}

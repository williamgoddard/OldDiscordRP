package uniqueimpact.discordRP.discord.commands.admin;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import uniqueimpact.discordRP.discord.commands.Command;
import uniqueimpact.discordRP.discord.utils.AdminChecker;
import uniqueimpact.discordRP.things.Inventory;
import uniqueimpact.discordRP.things.Player;
import uniqueimpact.discordRP.things.Room;
import uniqueimpact.discordRP.things.User;
import uniqueimpact.discordRP.utils.InvalidInputException;

import java.util.List;

public class InventoryCommand implements Command {

    @Override
    public String run(SlashCommandInteractionEvent command) {

        if (!AdminChecker.isAdmin(command.getMember())) {
            return "You do not have permission to use this command.";
        }

        String path = command.getFullCommandName();

        switch (path) {
            case "inventory room":
                return room(command);
            case "inventory items":
                return items(command);
            case "inventory clothes":
                return clothes(command);
            default:
                return "Error: Invalid command path (" + path + ")";
        }

    }

    private String room(SlashCommandInteractionEvent command) {

        String roomName = command.getOption("room").getAsString();
        Integer roomNum = command.getOption("room_num") != null ? command.getOption("room_num").getAsInt() : 1;

        Room room;
        try {
            room = roleplay.findRoom(roomName, roomNum);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        String userId = command.getUser().getId();

        User user;
        try {
            user = roleplay.findUser(userId);
        } catch (InvalidInputException e) {
            try {
                user = new User(userId, null, null);
                roleplay.getUsers().add(user);
            } catch (InvalidInputException ex) {
                return ex.getMessage();
            }
        }

        user.setInventory(room.getInv());

        return "Selected the inventory of room `" + roomName + "` successfully.";

    }

    private String items(SlashCommandInteractionEvent command) {

        String playerName = command.getOption("character").getAsString();

        Player player;
        try {
            player = roleplay.findPlayer(playerName);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        String userId = command.getUser().getId();

        User user;
        try {
            user = roleplay.findUser(userId);
        } catch (InvalidInputException e) {
            try {
                user = new User(userId, null, null);
                roleplay.getUsers().add(user);
            } catch (InvalidInputException ex) {
                return ex.getMessage();
            }
        }

        user.setInventory(player.getInv());

        return "Selected the inventory of character `" + playerName + "` successfully.";

    }

    private String clothes(SlashCommandInteractionEvent command) {

        String playerName = command.getOption("character").getAsString();

        Player player;
        try {
            player = roleplay.findPlayer(playerName);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        String userId = command.getUser().getId();

        User user;
        try {
            user = roleplay.findUser(userId);
        } catch (InvalidInputException e) {
            try {
                user = new User(userId, null, null);
                roleplay.getUsers().add(user);
            } catch (InvalidInputException ex) {
                return ex.getMessage();
            }
        }

        user.setInventory(player.getClothes());

        return "Selected the clothes of character `" + playerName + "` successfully.";

    }

}

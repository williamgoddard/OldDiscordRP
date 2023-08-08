package uniqueimpact.discordRP.discord.commands.admin;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import uniqueimpact.discordRP.discord.commands.Command;
import uniqueimpact.discordRP.discord.utils.AdminChecker;
import uniqueimpact.discordRP.things.Chara;
import uniqueimpact.discordRP.things.Room;
import uniqueimpact.discordRP.things.User;
import uniqueimpact.discordRP.utils.InvalidInputException;

public class InventoryCommand implements Command {

    @Override
    public void run(SlashCommandInteractionEvent command) {

        if (!AdminChecker.isAdmin(command.getMember())) {
            command.reply("You do not have permission to use this command.").queue();
            return;
        }

        String path = command.getFullCommandName();
        String response;

        switch (path) {
            case "inventory storage":
                response = storage(command);
                break;
            case "inventory room":
                response = room(command);
                break;
            case "inventory items":
                response = items(command);
                break;
            case "inventory clothes":
                response = clothes(command);
                break;
            default:
                response = "Error: Invalid command path (" + path + ")";
        }

        command.reply(response).queue();

    }

    private String storage(SlashCommandInteractionEvent command) {

        String userId = command.getMember().getId();

        User user;
        try {
            user = roleplay.findUser(userId);
        } catch (InvalidInputException e) {
            try {
                user = new User(command.getMember());
                roleplay.addUser(user);
            } catch (InvalidInputException ex) {
                return ex.getMessage();
            }
        }

        user.setInventory(roleplay.getStorageInv());

        return "Selected the roleplay's storage inventory successfully.";

    }

    private String room(SlashCommandInteractionEvent command) {

        String roomName = command.getOption("room").getAsString();
        Integer roomNum = command.getOption("num") != null ? command.getOption("num").getAsInt() : 1;

        Room room;
        try {
            room = roleplay.findRoom(roomName, roomNum);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        String userId = command.getMember().getId();

        User user;
        try {
            user = roleplay.findUser(userId);
        } catch (InvalidInputException e) {
            try {
                user = new User(command.getMember());
                roleplay.addUser(user);
            } catch (InvalidInputException ex) {
                return ex.getMessage();
            }
        }

        user.setInventory(room.getInv());

        return "Selected the inventory of room `" + room.getName() + "` successfully.";

    }

    private String items(SlashCommandInteractionEvent command) {

        String characterName = command.getOption("character").getAsString();

        Chara character;
        try {
            character = roleplay.findCharacter(characterName);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        String userId = command.getMember().getId();

        User user;
        try {
            user = roleplay.findUser(userId);
        } catch (InvalidInputException e) {
            try {
                user = new User(command.getMember());
                roleplay.addUser(user);
            } catch (InvalidInputException ex) {
                return ex.getMessage();
            }
        }

        user.setInventory(character.getInv());

        return "Selected the inventory of character `" + character.getName() + "` successfully.";

    }

    private String clothes(SlashCommandInteractionEvent command) {

        String characterName = command.getOption("character").getAsString();

        Chara character;
        try {
            character = roleplay.findCharacter(characterName);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        String userId = command.getMember().getId();

        User user;
        try {
            user = roleplay.findUser(userId);
        } catch (InvalidInputException e) {
            try {
                user = new User(command.getMember());
                roleplay.addUser(user);
            } catch (InvalidInputException ex) {
                return ex.getMessage();
            }
        }

        user.setInventory(character.getClothes());

        return "Selected the clothes of character `" + character.getName() + "` successfully.";

    }

}

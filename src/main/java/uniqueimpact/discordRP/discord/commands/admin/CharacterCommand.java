package uniqueimpact.discordRP.discord.commands.admin;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import uniqueimpact.discordRP.discord.commands.Command;
import uniqueimpact.discordRP.discord.utils.AdminChecker;
import uniqueimpact.discordRP.discord.utils.DiscordOutputGenerator;
import uniqueimpact.discordRP.things.Chara;
import uniqueimpact.discordRP.things.Room;
import uniqueimpact.discordRP.utils.InvalidInputException;

import java.util.List;

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
            case "character move-all":
                return moveall(command);
            case "character freeze-all":
                return freezeall(command);
            case "character unfreeze-all":
                return unfreezeall(command);
            case "character delete":
                return delete(command);
            default:
                return "Error: Invalid command path (" + path + ")";
        }

    }

    private String create(SlashCommandInteractionEvent command) {

        // Required Fields
        String name = command.getOption("name").getAsString();
        TextChannel channel = command.getOption("channel").getAsChannel().asTextChannel();
        String roomName = command.getOption("room").getAsString();
        String description = command.getOption("description").getAsString();

        // Optional Fields
        Integer roomNum = command.getOption("room_num") != null ? command.getOption("room_num").getAsInt() : 1;
        String displayName = command.getOption("display_name") != null ? command.getOption("display_name").getAsString() : name;
        String picture = command.getOption("picture") != null ? command.getOption("picture").getAsString() : null;
        Double itemsCapacity = (command.getOption("items_capacity") != null) ? command.getOption("items_capacity").getAsDouble() : 0.0;
        Double clothesCapacity = (command.getOption("clothes_capacity") != null) ? command.getOption("clothes_capacity").getAsDouble() : 0.0;
        Boolean hidden = (command.getOption("hidden") != null) ? command.getOption("hidden").getAsBoolean() : false;
        Boolean frozen = (command.getOption("frozen") != null) ? command.getOption("frozen").getAsBoolean() : false;
        Boolean npc = (command.getOption("npc") != null) ? command.getOption("npc").getAsBoolean() : false;

        try {
            roleplay.findCharacter(name);
            return "A character with that name already exists.";
        } catch (InvalidInputException ignored) {}

        try {
            roleplay.findCharacterByChannel(channel.getId());
            return "A character is already linked to that channel.";
        } catch (InvalidInputException ignored) {}

        Room room;
        try {
            room = roleplay.findRoom(roomName, roomNum);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        Chara character;
        try {
            character = new Chara(channel, name, displayName, picture, description, hidden, frozen, npc, itemsCapacity, clothesCapacity, room);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        room.addCharacter(character);
        roleplay.addCharacter(character);

        return "The character was created successfully.";

    }

    private String list(SlashCommandInteractionEvent command) {

        String roomName = command.getOption("room") != null ? command.getOption("room").getAsString() : null;
        Integer roomNum = command.getOption("room_num") != null ? command.getOption("room_num").getAsInt() : 1;

        if (roomName == null) {
            List<Chara> characters = roleplay.getCharas();

            if (characters.size() == 0) {
                return "No characters are currently registered.";
            }

            return "List of characters registered to the roleplay:\n" + DiscordOutputGenerator.convertCharaList(roleplay.getCharas(), 1900);
        }

        Room room;
        try {
            room = roleplay.findRoom(roomName, roomNum);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        List<Chara> characters = room.getCharacters();

        if (characters.size() == 0) {
            return "There are no characters in room `" + roomName + "`.";
        }

        return "Characters in room `" + roomName + "`:\n" + DiscordOutputGenerator.convertCharaList(room.getCharacters(), 1900);

    }

    private String look(SlashCommandInteractionEvent command) {

        String name = command.getOption("character").getAsString();

        Chara character;
        try {
            character = roleplay.findCharacter(name);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        return DiscordOutputGenerator.convertPlayerAdmin(character);

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
        Boolean hidden = (command.getOption("hidden") != null) ? command.getOption("hidden").getAsBoolean() : null;
        Boolean frozen = (command.getOption("frozen") != null) ? command.getOption("frozen").getAsBoolean() : null;
        Boolean npc = (command.getOption("npc") != null) ? command.getOption("npc").getAsBoolean() : null;

        Chara character;
        try {
            character = roleplay.findCharacter(name);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        if (newName != null) {
            try {
                roleplay.findCharacter(newName);
                return "A character with that name already exists.";
            } catch (InvalidInputException ignored) {}
        }

        if (channel != null) {
            try {
                roleplay.findCharacterByChannel(channel.getId());
                return "A character is already linked to that channel.";
            } catch (InvalidInputException ignored) {}
        }

        String response = "";

        if (newName != null) {
            try {
                character.setName(newName);
                roleplay.delCharacter(character);
                roleplay.addCharacter(character);
                character.getRoom().delCharacter(character);
                character.getRoom().addCharacter(character);
                response += "The character's name was edited successfully.\n";
            } catch (InvalidInputException e) {
                response += "The character's name was not edited: " + e.getMessage() + "\n";
            }
        }

        if (channel != null) {
            character.setChannel(channel);
            response += "The character's channel was edited successfully.\n";
        }

        if (description != null) {
            try {
                character.setDescription(description);
            } catch (InvalidInputException e) {
                response += "The character's description was not edited: " + e.getMessage() + "\n";
            }
            response += "The character's channel was edited successfully.\n";
        }

        if (displayName != null) {
            try {
                character.setDisplayName(displayName);
            } catch (InvalidInputException e) {
                response += "The character's display name was not edited: " + e.getMessage() + "\n";
            }
            response += "The character's channel was edited successfully.\n";
        }

        if (picture != null) {
            character.setPicture(picture);
            response += "The character's channel was edited successfully.\n";
        }

        if (itemsCapacity != null) {
            try {
                character.getInv().setCapacity(itemsCapacity);
                response += "The character's inventory capacity was edited successfully.\n";
            } catch (InvalidInputException e) {
                response += "The character's inventory capacity was not edited: " + e.getMessage() + "\n";
            }
        }

        if (clothesCapacity != null) {
            try {
                character.getClothes().setCapacity(clothesCapacity);
                response += "The character's clothes capacity was edited successfully.\n";
            } catch (InvalidInputException e) {
                response += "The character's clothes capacity was not edited: " + e.getMessage() + "\n";
            }
        }

        if (hidden != null) {
            character.setHidden(hidden);
            response += "The character's hidden status was edited successfully.\n";
        }

        if (frozen != null) {
            character.setFrozen(frozen);
            response += "The character's frozen status was edited successfully.\n";
        }

        if (npc != null) {
            character.setNpc(npc);
            response += "The character's npc status was edited successfully.\n";
        }

        if (response.equals("")) {
            return "The character was not edited: At least one field must be selected for editing.";
        }

        return response;

    }

    private String move(SlashCommandInteractionEvent command) {

        String name = command.getOption("character").getAsString();
        String roomName = command.getOption("room").getAsString();
        Integer roomNum = command.getOption("room_num") != null ? command.getOption("room_num").getAsInt() : 1;

        Chara character;
        try {
            character = roleplay.findCharacter(name);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        Room room;
        try {
            room = roleplay.findRoom(roomName, roomNum);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        character.getRoom().delCharacter(character);
        character.setRoom(room);
        room.addCharacter(character);

        try {
        } catch (NullPointerException e) {
            return e.getMessage();
        }

        return "The character was moved successfully.";

    }

    private String moveall(SlashCommandInteractionEvent command) {

        String roomName = command.getOption("room").getAsString();
        Integer roomNum = command.getOption("room_num") != null ? command.getOption("room_num").getAsInt() : 1;

        Room room;
        try {
            room = roleplay.findRoom(roomName, roomNum);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        for (Chara character : roleplay.getCharas()) {
            if (!character.isNpc()) {
                character.getRoom().delCharacter(character);
                character.setRoom(room);
                room.addCharacter(character);
            }
        }

        return "All characters were moved successfully.";

    }

    private String freezeall(SlashCommandInteractionEvent command) {

        for (Chara character : roleplay.getCharas()) {
            if (!character.isNpc()) {
                character.setFrozen(true);
            }
        }

        return "All characters were frozen successfully.";

    }

    private String unfreezeall(SlashCommandInteractionEvent command) {

        for (Chara character : roleplay.getCharas()) {
            if (!character.isNpc()) {
                character.setFrozen(false);
            }
        }

        return "All characters were frozen successfully.";

    }

    private String delete(SlashCommandInteractionEvent command) {

        String name = command.getOption("character").getAsString();

        Chara character;
        try {
            character = roleplay.findCharacter(name);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        roleplay.delCharacter(character);

        return  "The character was deleted successfully.";

    }

}

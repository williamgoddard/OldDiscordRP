package uniqueimpact.discordRP.discord.utils;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

import java.util.ArrayList;
import java.util.List;

public class CommandSetup {

    // Set up the commands
    public static void addCommands(JDA bot) {

        List<CommandData> commands = new ArrayList<>();

        // Drop Command
        commands.add(Commands.slash("drop", "Drop an item from your inventory into the room").addOptions(
                new OptionData(OptionType.STRING, "item", "The name of the item", true)
                        .setRequiredLength(1, 32),
                new OptionData(OptionType.INTEGER, "num", "The number of the specific item, if there are multiple items with the same name", false)
                        .setRequiredRange(1, Integer.MAX_VALUE)));

        // Take Command
        commands.add(Commands.slash("take", "Take an item from the room into your inventory").addOptions(
                new OptionData(OptionType.STRING, "item", "The name of the item", true)
                        .setRequiredLength(1, 32),
                new OptionData(OptionType.INTEGER, "num", "The number of the specific item, if there are multiple items with the same name", false)
                        .setRequiredRange(1, Integer.MAX_VALUE)));

        // Undress Command
        commands.add(Commands.slash("undress", "Undress an item of clothing and hold it in your inventory").addOptions(
                new OptionData(OptionType.STRING, "item", "The name of the item", true)
                        .setRequiredLength(1, 32),
                new OptionData(OptionType.INTEGER, "num", "The number of the specific item, if there are multiple items with the same name", false)
                        .setRequiredRange(1, Integer.MAX_VALUE)));

        // Wear Command
        commands.add(Commands.slash("wear", "Wear an item of clothing from your inventory").addOptions(
                new OptionData(OptionType.STRING, "item", "The name of the item", true)
                        .setRequiredLength(1, 32),
                new OptionData(OptionType.INTEGER, "num", "The number of the specific item, if there are multiple items with the same name", false)
                        .setRequiredRange(1, Integer.MAX_VALUE)));

        // Undress Drop Command
        commands.add(Commands.slash("undressdrop", "Undress an item of clothing and drop it into the room").addOptions(
                new OptionData(OptionType.STRING, "item", "The name of the item", true)
                        .setRequiredLength(1, 32),
                new OptionData(OptionType.INTEGER, "num", "The number of the specific item, if there are multiple items with the same name", false)
                        .setRequiredRange(1, Integer.MAX_VALUE)));

        // Take Wear Command
        commands.add(Commands.slash("takewear", "Take an item from the room and wear it").addOptions(
                new OptionData(OptionType.STRING, "item", "The name of the item", true)
                        .setRequiredLength(1, 32),
                new OptionData(OptionType.INTEGER, "num", "The number of the specific item, if there are multiple items with the same name", false)
                        .setRequiredRange(1, Integer.MAX_VALUE)));

        // Room Command
        commands.add(Commands.slash("room", "Command to perform admin actions on rooms").addSubcommands(
                new SubcommandData("create", "Create a room").addOptions(
                        new OptionData(OptionType.STRING, "name", "The name of the room", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.STRING, "description", "The description of the room", false)
                                .setRequiredLength(1, 1500),
                        new OptionData(OptionType.NUMBER, "capacity", "The capacity of the room (Set to '0' for no limit)", false)
                                .setRequiredRange(0.0, Double.MAX_VALUE)
                ),
                new SubcommandData("list", "List all of the rooms"),
                new SubcommandData("look", "Look at a room").addOptions(
                        new OptionData(OptionType.STRING, "room", "The name of the room", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.INTEGER, "num", "The number of the specific room, if there are multiple rooms with the same name", false)
                                .setRequiredRange(1, Integer.MAX_VALUE)
                ),
                new SubcommandData("edit", "Edit a room").addOptions(
                        new OptionData(OptionType.STRING, "room", "The name of the room", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.INTEGER, "num", "The number of the specific room, if there are multiple rooms with the same name", false)
                                .setRequiredRange(1, Integer.MAX_VALUE),
                        new OptionData(OptionType.STRING, "name", "The new name of the room", false)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.STRING, "description", "The new description of the room (Set to 'none' to clear)", false)
                                .setRequiredLength(1, 1500),
                        new OptionData(OptionType.NUMBER, "capacity", "The new capacity of the room (Set to '0' for no limit)", false)
                                .setRequiredRange(0.0, Double.MAX_VALUE)
                ),
                new SubcommandData("delete", "Delete a room").addOptions(
                        new OptionData(OptionType.STRING, "room", "The name of the room", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.INTEGER, "num", "The number of the specific room, if there are multiple rooms with the same name", false)
                                .setRequiredRange(1, Integer.MAX_VALUE)
                )
        ));

        // Character Command
        commands.add(Commands.slash("character", "Command to perform admin actions on characters").addSubcommands(
                new SubcommandData("create", "Create a character").addOptions(
                        new OptionData(OptionType.STRING, "name", "The name of the character", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.CHANNEL, "channel", "The channel linked to this character", true)
                                .setChannelTypes(ChannelType.TEXT),
                        new OptionData(OptionType.STRING, "room", "The room the character is in", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.INTEGER, "roomNum", "The number of the specific room, if there are multiple rooms with the same name", false)
                                .setRequiredRange(1, Integer.MAX_VALUE),
                        new OptionData(OptionType.STRING, "displayName", "The display name of the character", false)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.STRING, "picture", "The image URL of the character", false)
                                .setRequiredLength(1, 200),
                        new OptionData(OptionType.STRING, "description", "The description of the character", false)
                                .setRequiredLength(1, 1500),
                        new OptionData(OptionType.NUMBER, "itemsCapacity", "The capacity of the player's inventory (Set to '0' for no limit)", false)
                                .setRequiredRange(0.0, Double.MAX_VALUE),
                        new OptionData(OptionType.NUMBER, "clothesCapacity", "The capacity of the player's clothes (Set to '0' for no limit)", false)
                                .setRequiredRange(0.0, Double.MAX_VALUE),
                        new OptionData(OptionType.BOOLEAN, "hidden", "Whether the player is hidden", false)
                ),
                new SubcommandData("list", "List all of the characters").addOptions(
                        new OptionData(OptionType.STRING, "room", "The room to list the characters in", false)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.INTEGER, "roomNum", "The number of the specific room, if there are multiple rooms with the same name", false)
                                .setRequiredRange(1, Integer.MAX_VALUE)
                ),
                new SubcommandData("look", "Look at a character").addOptions(
                        new OptionData(OptionType.STRING, "character", "The name of the character", true)
                                .setRequiredLength(1, 32)
                ),
                new SubcommandData("edit", "Edit a character").addOptions(
                        new OptionData(OptionType.STRING, "character", "The name of the character", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.STRING, "name", "The new name of the character", false)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.CHANNEL, "channel", "The new channel linked to this character", false)
                                .setChannelTypes(ChannelType.TEXT),
                        new OptionData(OptionType.STRING, "displayName", "The display name of the character (Set to 'none' to clear)", false)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.STRING, "picture", "The image URL of the character (Set to 'none' to clear)", false)
                                .setRequiredLength(1, 200),
                        new OptionData(OptionType.STRING, "description", "The description of the character (Set to 'none' to clear)", false)
                                .setRequiredLength(1, 1500),
                        new OptionData(OptionType.NUMBER, "itemsCapacity", "The new capacity of the player's inventory (Set to '0' for no limit)", false)
                                .setRequiredRange(0.0, Double.MAX_VALUE),
                        new OptionData(OptionType.NUMBER, "clothesCapacity", "The new capacity of the player's clothes (Set to '0' for no limit)", false)
                                .setRequiredRange(0.0, Double.MAX_VALUE),
                        new OptionData(OptionType.BOOLEAN, "hidden", "Whether the player is hidden", false)
                ),
                new SubcommandData("move", "Move a character").addOptions(
                        new OptionData(OptionType.STRING, "character", "The name of the character", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.STRING, "room", "The name of the room", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.INTEGER, "roomNum", "The number of the specific room, if there are multiple rooms with the same name", false)
                                .setRequiredRange(1, Integer.MAX_VALUE)
                ),
                new SubcommandData("delete", "Delete a character").addOptions(
                        new OptionData(OptionType.STRING, "character", "The name of the character", true)
                                .setRequiredLength(1, 32)
                )
        ));

        // Item Command
        commands.add(Commands.slash("item", "Command to perform admin actions on items").addSubcommands(
                new SubcommandData("create", "Create an item").addOptions(
                        new OptionData(OptionType.STRING, "location-type", "The type of location the item is in", true)
                                .addChoice("room", "room")
                                .addChoice("character", "character")
                                .addChoice("clothes", "clothes"),
                        new OptionData(OptionType.STRING, "location", "The name of the location of the item", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.STRING, "name", "The name of the item", true)
                                .setRequiredLength(1, 32)),
                new SubcommandData("look", "Look at an item").addOptions(
                        new OptionData(OptionType.STRING, "location-type", "The type of location the item is in", true)
                                .addChoice("room", "room")
                                .addChoice("character", "character")
                                .addChoice("clothes", "clothes"),
                        new OptionData(OptionType.STRING, "location", "The name of the location of the item", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.STRING, "name", "The name of the item", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.INTEGER, "num", "The number of the specific item, if there are multiple items with the same name", false)
                                .setRequiredRange(1, Integer.MAX_VALUE)),
                new SubcommandData("edit", "Edit an item").addOptions(
                        new OptionData(OptionType.STRING, "location-type", "The type of location the item is in", true)
                                .addChoice("room", "room")
                                .addChoice("character", "character")
                                .addChoice("clothes", "clothes"),
                        new OptionData(OptionType.STRING, "location", "The name of the location of the item", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.STRING, "name", "The name of the item", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.INTEGER, "num", "The number of the specific item, if there are multiple items with the same name", false)
                                .setRequiredRange(1, Integer.MAX_VALUE)),
                new SubcommandData("delete", "Delete an item").addOptions(
                        new OptionData(OptionType.STRING, "location-type", "The type of location the item is in", true)
                                .addChoice("room", "room")
                                .addChoice("character", "character")
                                .addChoice("clothes", "clothes"),
                        new OptionData(OptionType.STRING, "location", "The name of the location of the item", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.STRING, "name", "The name of the item", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.INTEGER, "num", "The number of the specific item, if there are multiple items with the same name", false)
                                .setRequiredRange(1, Integer.MAX_VALUE)),
                new SubcommandData("copy", "Copy an item to the clipboard (Clipboard is per-server)").addOptions(
                        new OptionData(OptionType.STRING, "location-type", "The type of location the item is in", true)
                                .addChoice("room", "room")
                                .addChoice("character", "character")
                                .addChoice("clothes", "clothes"),
                        new OptionData(OptionType.STRING, "location", "The name of the location of the item", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.STRING, "name", "The name of the item", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.INTEGER, "num", "The number of the specific item, if there are multiple items with the same name", false)
                                .setRequiredRange(1, Integer.MAX_VALUE)),
                new SubcommandData("paste", "Paste an item from the clipboard (Clipboard is per-server)").addOptions(
                        new OptionData(OptionType.STRING, "location-type", "The type of location the item is in", true)
                                .addChoice("room", "room")
                                .addChoice("character", "character")
                                .addChoice("clothes", "clothes"),
                        new OptionData(OptionType.STRING, "location", "The name of the location of the item", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.STRING, "name", "The name of the item", true)
                                .setRequiredLength(1, 32))));

        // Inventory Command
        commands.add(Commands.slash("inventory", "Command to select an inventory to work with").addSubcommands(
                new SubcommandData("room", "Select a room's inventory").addOptions(
                        new OptionData(OptionType.STRING, "room", "The name of the room", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.INTEGER, "num", "The number of the specific room, if there are multiple room with the same name", false)
                                .setRequiredRange(1, Integer.MAX_VALUE)),
                new SubcommandData("items", "Select a character's inventory").addOptions(
                        new OptionData(OptionType.STRING, "character", "The name of the character", true)
                                .setRequiredLength(1, 32)),
                new SubcommandData("clothes", "Select a character's clothes").addOptions(
                        new OptionData(OptionType.STRING, "character", "The name of the character", true)
                                .setRequiredLength(1, 32))));

        // Update Commands
        bot.getGuildById("933432644857909339").updateCommands().addCommands(commands).queue();
        bot.updateCommands().addCommands(commands).queue();

    }

}

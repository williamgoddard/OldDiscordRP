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

        // Help Command
        commands.add(Commands.slash("help", "List all of the player commands."));

        // Drop Command
        commands.add(Commands.slash("drop", "Drop an item from your inventory into the room").addOptions(
                new OptionData(OptionType.STRING, "item", "The name of the item", true)
                        .setRequiredLength(1, 32),
                new OptionData(OptionType.INTEGER, "num", "The number of the specific item, if there are multiple items with the same name", false)
                        .setRequiredRange(1, 1000000)
        ));

        // Take Command
        commands.add(Commands.slash("take", "Take an item from the room into your inventory").addOptions(
                new OptionData(OptionType.STRING, "item", "The name of the item", true)
                        .setRequiredLength(1, 32),
                new OptionData(OptionType.INTEGER, "num", "The number of the specific item, if there are multiple items with the same name", false)
                        .setRequiredRange(1, 1000000)
        ));

        // Undress Command
        commands.add(Commands.slash("undress", "Undress an item of clothing and hold it in your inventory").addOptions(
                new OptionData(OptionType.STRING, "item", "The name of the item", true)
                        .setRequiredLength(1, 32),
                new OptionData(OptionType.INTEGER, "num", "The number of the specific item, if there are multiple items with the same name", false)
                        .setRequiredRange(1, 1000000)
        ));

        // Wear Command
        commands.add(Commands.slash("wear", "Wear an item of clothing from your inventory").addOptions(
                new OptionData(OptionType.STRING, "item", "The name of the item", true)
                        .setRequiredLength(1, 32),
                new OptionData(OptionType.INTEGER, "num", "The number of the specific item, if there are multiple items with the same name", false)
                        .setRequiredRange(1, 1000000)
        ));

        // Undress Drop Command
        commands.add(Commands.slash("undressdrop", "Undress an item of clothing and drop it into the room").addOptions(
                new OptionData(OptionType.STRING, "item", "The name of the item", true)
                        .setRequiredLength(1, 32),
                new OptionData(OptionType.INTEGER, "num", "The number of the specific item, if there are multiple items with the same name", false)
                        .setRequiredRange(1, 1000000)
        ));

        // Take Wear Command
        commands.add(Commands.slash("takewear", "Take an item from the room and wear it").addOptions(
                new OptionData(OptionType.STRING, "item", "The name of the item", true)
                        .setRequiredLength(1, 32),
                new OptionData(OptionType.INTEGER, "num", "The number of the specific item, if there are multiple items with the same name", false)
                        .setRequiredRange(1, 1000000)
        ));

        // Look Command
        commands.add(Commands.slash("look", "Look around at your surroundings.").addSubcommands(
                new SubcommandData("room", "Look around at the room"),
                new SubcommandData("item", "Look at an item in the room").addOptions(
                        new OptionData(OptionType.STRING, "item", "The name of the item", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.INTEGER, "num", "The number of the specific item, if there are multiple items with the same name", false)
                                .setRequiredRange(1, 1000000)
                ),
                new SubcommandData("inv", "Look at an item you are currently holding.").addOptions(
                        new OptionData(OptionType.STRING, "item", "The name of the item", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.INTEGER, "num", "The number of the specific item, if there are multiple items with the same name", false)
                                .setRequiredRange(1, 1000000)
                ),
                new SubcommandData("clothes", "Look at an item you are currently wearing.").addOptions(
                        new OptionData(OptionType.STRING, "item", "The name of the item", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.INTEGER, "num", "The number of the specific item, if there are multiple items with the same name", false)
                                .setRequiredRange(1, 1000000)
                ),
                new SubcommandData("character", "Look at a character in the room.").addOptions(
                        new OptionData(OptionType.STRING, "character", "The name of the character", true)
                                .setRequiredLength(1, 32)
                )
        ));

        // Items Command
        commands.add(Commands.slash("items", "List all the items in the room."));

        // Inv Command
        commands.add(Commands.slash("inv", "List all the items you are currently holding."));

        // Clothes Command
        commands.add(Commands.slash("clothes", "List all the items you are currently wearing."));

        // Doors command
        commands.add(Commands.slash("doors", "List all the doors you can access from here."));

        // Goto command
        commands.add(Commands.slash("goto", "Move to another room.").addOptions(
                new OptionData(OptionType.STRING, "room", "The name of the room to move to", true)
                        .setRequiredLength(1, 32),
                new OptionData(OptionType.INTEGER, "num", "The number of the specific room, if there are multiple rooms with the same name", false)
                        .setRequiredRange(1, 1000000)
        ));

        // Lock command
        commands.add(Commands.slash("lock", "Lock the door to another room.").addOptions(
                new OptionData(OptionType.STRING, "room", "The name of the room to lock the door to", true)
                        .setRequiredLength(1, 32),
                new OptionData(OptionType.INTEGER, "num", "The number of the specific room, if there are multiple rooms with the same name", false)
                        .setRequiredRange(1, 1000000)
        ));

        // Unlock command
        commands.add(Commands.slash("unlock", "Unlock the door to another room.").addOptions(
                new OptionData(OptionType.STRING, "room", "The name of the room to unlock the door to", true)
                        .setRequiredLength(1, 32),
                new OptionData(OptionType.INTEGER, "num", "The number of the specific room, if there are multiple rooms with the same name", false)
                        .setRequiredRange(1, 1000000)
        ));

        // Admin Help Command
        commands.add(Commands.slash("adminhelp", "List all of the admin commands."));

        // Room Command
        commands.add(Commands.slash("room", "Command to perform admin actions on rooms").addSubcommands(
                new SubcommandData("create", "Create a room").addOptions(
                        new OptionData(OptionType.STRING, "name", "The name of the room", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.STRING, "description", "The description of the room", true)
                                .setRequiredLength(1, 1500),
                        new OptionData(OptionType.NUMBER, "capacity", "The capacity of the room", false)
                                .setRequiredRange(0.0, 1000000.0)
                ),
                new SubcommandData("list", "List all of the rooms"),
                new SubcommandData("look", "Look at a room").addOptions(
                        new OptionData(OptionType.STRING, "room", "The name of the room", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.INTEGER, "num", "The number of the specific room, if there are multiple rooms with the same name", false)
                                .setRequiredRange(1, 1000000)
                ),
                new SubcommandData("edit", "Edit a room").addOptions(
                        new OptionData(OptionType.STRING, "room", "The name of the room", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.INTEGER, "num", "The number of the specific room, if there are multiple rooms with the same name", false)
                                .setRequiredRange(1, 1000000),
                        new OptionData(OptionType.STRING, "name", "The new name of the room", false)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.STRING, "description", "The new description of the room", false)
                                .setRequiredLength(1, 1500),
                        new OptionData(OptionType.NUMBER, "capacity", "The new capacity of the room", false)
                                .setRequiredRange(0.0, 1000000.0)
                ),
                new SubcommandData("delete", "Delete a room").addOptions(
                        new OptionData(OptionType.STRING, "room", "The name of the room", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.INTEGER, "num", "The number of the specific room, if there are multiple rooms with the same name", false)
                                .setRequiredRange(1, 1000000)
                )
        ));

        // Character Command
        commands.add(Commands.slash("character", "Command to perform admin actions on characters").addSubcommands(
                new SubcommandData("create", "Create a character").addOptions(
                        new OptionData(OptionType.STRING, "name", "The name of the character", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.CHANNEL, "channel", "The channel linked to this character", true)
                                .setChannelTypes(ChannelType.TEXT),
                        new OptionData(OptionType.STRING, "description", "The description of the character", true)
                                .setRequiredLength(1, 1500),
                        new OptionData(OptionType.STRING, "room", "The room the character is in", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.INTEGER, "room_num", "The number of the specific room, if there are multiple rooms with the same name", false)
                                .setRequiredRange(1, 1000000),
                        new OptionData(OptionType.STRING, "display_name", "The display name of the character", false)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.STRING, "picture", "The image URL of the character", false)
                                .setRequiredLength(1, 200),
                        new OptionData(OptionType.NUMBER, "items_capacity", "The capacity of the player's inventory", false)
                                .setRequiredRange(0.0, 1000000.0),
                        new OptionData(OptionType.NUMBER, "clothes_capacity", "The capacity of the player's clothes", false)
                                .setRequiredRange(0.0, 1000000.0),
                        new OptionData(OptionType.BOOLEAN, "hidden", "Whether the player is hidden", false)
                ),
                new SubcommandData("list", "List all of the characters, or all characters in a room").addOptions(
                        new OptionData(OptionType.STRING, "room", "The room to list the characters in", false)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.INTEGER, "room_num", "The number of the specific room, if there are multiple rooms with the same name", false)
                                .setRequiredRange(1, 1000000)
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
                        new OptionData(OptionType.STRING, "description", "The description of the character", false)
                                .setRequiredLength(1, 1500),
                        new OptionData(OptionType.STRING, "display_name", "The display name of the character (Set to 'none' to clear)", false)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.STRING, "picture", "The image URL of the character (Set to 'none' to clear)", false)
                                .setRequiredLength(1, 200),
                        new OptionData(OptionType.NUMBER, "items_capacity", "The new capacity of the player's inventory", false)
                                .setRequiredRange(0.0, 1000000.0),
                        new OptionData(OptionType.NUMBER, "clothes_capacity", "The new capacity of the player's clothes", false)
                                .setRequiredRange(0.0, 1000000.0),
                        new OptionData(OptionType.BOOLEAN, "hidden", "Whether the player is hidden (Default false)", false)
                ),
                new SubcommandData("move", "Move a character").addOptions(
                        new OptionData(OptionType.STRING, "character", "The name of the character", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.STRING, "room", "The name of the room", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.INTEGER, "room_num", "The number of the specific room, if there are multiple rooms with the same name", false)
                                .setRequiredRange(1, 1000000)
                ),
                new SubcommandData("delete", "Delete a character").addOptions(
                        new OptionData(OptionType.STRING, "character", "The name of the character", true)
                                .setRequiredLength(1, 32)
                )
        ));

        // Item Command
        commands.add(Commands.slash("item", "Command to perform admin actions on items").addSubcommands(
                new SubcommandData("create", "Create an item in the currently selected inventory").addOptions(
                        new OptionData(OptionType.STRING, "name", "The name of the item", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.STRING, "description", "The description of the item", true)
                                .setRequiredLength(1, 1500),
                        new OptionData(OptionType.NUMBER, "weight", "The weight of the item", false)
                                .setRequiredRange(0.0, 1000000.0),
                        new OptionData(OptionType.BOOLEAN, "takeable", "Whether the item can be taken (Default true)", false),
                        new OptionData(OptionType.BOOLEAN, "wearable", "Whether the item can be worn (Default false)", false),
                        new OptionData(OptionType.BOOLEAN, "infinite", "Whether the item can be taken infinitely (Default false)", false),
                        new OptionData(OptionType.STRING, "keyword", "The keyword associated with this item. Items can lock and unlock doors with the same keyword.", false)
                                .setRequiredLength(1, 32)
                ),
                new SubcommandData("list", "List all of the items in the currently selected inventory"
                ),
                new SubcommandData("look", "Look at an item in the currently selected inventory").addOptions(
                        new OptionData(OptionType.STRING, "item", "The name of the item", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.INTEGER, "num", "The number of the specific item, if there are multiple items with the same name", false)
                                .setRequiredRange(1, 1000000)
                ),
                new SubcommandData("edit", "Edit an item in the currently selected inventory").addOptions(
                        new OptionData(OptionType.STRING, "item", "The name of the item", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.INTEGER, "num", "The number of the specific item, if there are multiple items with the same name", false)
                                .setRequiredRange(1, 1000000),
                        new OptionData(OptionType.STRING, "name", "The new name of the item", false)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.STRING, "description", "The new description of the item", false)
                                .setRequiredLength(1, 1500),
                        new OptionData(OptionType.NUMBER, "weight", "The new weight of the item", false)
                                .setRequiredRange(0.0, 1000000.0),
                        new OptionData(OptionType.BOOLEAN, "takeable", "Whether the item can be taken", false),
                        new OptionData(OptionType.BOOLEAN, "wearable", "Whether the item can be worn", false),
                        new OptionData(OptionType.BOOLEAN, "infinite", "Whether the item can be taken infinitely", false),
                        new OptionData(OptionType.STRING, "keyword", "The new keyword associated with this item (Set to 'none' to clear)", false)
                ),
                new SubcommandData("delete", "Delete an item from the currently selected inventory").addOptions(
                        new OptionData(OptionType.STRING, "item", "The name of the item", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.INTEGER, "num", "The number of the specific item, if there are multiple items with the same name", false)
                                .setRequiredRange(1, 1000000)
                ),
                new SubcommandData("copy", "Copy an item to the clipboard from the currently selected inventory").addOptions(
                        new OptionData(OptionType.STRING, "item", "The name of the item", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.INTEGER, "num", "The number of the specific item, if there are multiple items with the same name", false)
                                .setRequiredRange(1, 1000000)
                ),
                new SubcommandData("paste", "Paste the item from the clipboard into the currently selected inventory")
        ));

        // Inventory Command
        commands.add(Commands.slash("inventory", "Command to select an inventory to work with").addSubcommands(
                new SubcommandData("room", "Select a room's inventory").addOptions(
                        new OptionData(OptionType.STRING, "room", "The name of the room", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.INTEGER, "num", "The number of the specific room, if there are multiple room with the same name", false)
                                .setRequiredRange(1, 1000000)
                ),
                new SubcommandData("items", "Select a character's inventory").addOptions(
                        new OptionData(OptionType.STRING, "character", "The name of the character", true)
                                .setRequiredLength(1, 32)
                ),
                new SubcommandData("clothes", "Select a character's clothes").addOptions(
                        new OptionData(OptionType.STRING, "character", "The name of the character", true)
                                .setRequiredLength(1, 32)
                )
        ));

        // Door Command
        commands.add(Commands.slash("door", "Command to perform admin actions on doors").addSubcommands(
                new SubcommandData("create", "Create a door").addOptions(
                        new OptionData(OptionType.STRING, "room1", "The name of the first room the door is attached to", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.STRING, "room2", "The name of the second room the door is attached to", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.INTEGER, "room1_num", "The number of the specific room, if there are multiple rooms with the same name", false)
                                .setRequiredRange(1, 1000000),
                        new OptionData(OptionType.INTEGER, "room2_num", "The number of the specific room, if there are multiple rooms with the same name", false)
                                .setRequiredRange(1, 1000000),
                        new OptionData(OptionType.BOOLEAN, "hidden", "Whether the door is hidden (Default false)", false),
                        new OptionData(OptionType.BOOLEAN, "locked", "Whether the door is locked (Default false)", false),
                        new OptionData(OptionType.STRING, "keyword", "The keyword associated with this door. Items can lock and unlock doors with the same keyword.", false)
                                .setRequiredLength(1, 32)
                ),
                new SubcommandData("list", "List all of the doors in a room").addOptions(
                        new OptionData(OptionType.STRING, "room", "The room to list the doors in", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.INTEGER, "room_num", "The number of the specific room, if there are multiple rooms with the same name", false)
                                .setRequiredRange(1, 1000000)
                ),
                new SubcommandData("look", "Look at a door").addOptions(
                        new OptionData(OptionType.STRING, "room1", "The name of the first room the door is attached to", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.STRING, "room2", "The name of the second room the door is attached to", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.INTEGER, "room1_num", "The number of the specific room, if there are multiple rooms with the same name", false)
                                .setRequiredRange(1, 1000000),
                        new OptionData(OptionType.INTEGER, "room2_num", "The number of the specific room, if there are multiple rooms with the same name", false)
                                .setRequiredRange(1, 1000000)
                ),
                new SubcommandData("edit", "Edit a door").addOptions(
                        new OptionData(OptionType.STRING, "room1", "The name of the first room the door is attached to", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.STRING, "room2", "The name of the second room the door is attached to", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.INTEGER, "room1_num", "The number of the specific room, if there are multiple rooms with the same name", false)
                                .setRequiredRange(1, 1000000),
                        new OptionData(OptionType.INTEGER, "room2_num", "The number of the specific room, if there are multiple rooms with the same name", false)
                                .setRequiredRange(1, 1000000),
                        new OptionData(OptionType.BOOLEAN, "hidden", "Whether the door is hidden (Default false)", false),
                        new OptionData(OptionType.BOOLEAN, "locked", "Whether the door is locked (Default false)", false),
                        new OptionData(OptionType.STRING, "keyword", "The new keyword associated with this door (Set to 'none' to clear)", false)
                                .setRequiredLength(1, 32)
                ),
                new SubcommandData("delete", "Delete a door").addOptions(
                        new OptionData(OptionType.STRING, "room1", "The name of the first room the door is attached to", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.STRING, "room2", "The name of the second room the door is attached to", true)
                                .setRequiredLength(1, 32),
                        new OptionData(OptionType.INTEGER, "room1_num", "The number of the specific room, if there are multiple rooms with the same name", false)
                                .setRequiredRange(1, 1000000),
                        new OptionData(OptionType.INTEGER, "room2_num", "The number of the specific room, if there are multiple rooms with the same name", false)
                                .setRequiredRange(1, 1000000)
                )
        ));

        // Roll Command
        commands.add(Commands.slash("roll", "Roll the dice!").addOptions(
                new OptionData(OptionType.INTEGER, "sides", "The number of sides on the dice", false)
                        .setRequiredRange(1, 1000),
                new OptionData(OptionType.INTEGER, "count", "The number of dice", false)
                        .setRequiredRange(1, 100)
        ));

        // 8Ball Command
        commands.add(Commands.slash("8ball", "Ask a yes or no question to the all-knowing 8-ball to learn from its infinite wisdom!").addOptions(
                new OptionData(OptionType.STRING, "question", "The question you would like to ask the 8-ball", true)
                        .setRequiredLength(1, 1000)
        ));

        // Excuse Command
        commands.add(Commands.slash("excuse", "Get a quick, randomly generated excuse for anything!").addOptions(
                new OptionData(OptionType.STRING, "event", "The event which you need an excuse for.", false)
                        .setRequiredLength(1, 1000)
        ));

        // Secret Command
        commands.add(Commands.slash("secret", "I wonder what this command does...?").addOptions(
                new OptionData(OptionType.STRING, "command", "The secret command you want to run...", true)
                        .setRequiredLength(1, 1000)
        ));

        // Update Commands
        bot.updateCommands().addCommands().queue();
        bot.getGuildById("933432644857909339").updateCommands().addCommands(commands).queue();

    }

}

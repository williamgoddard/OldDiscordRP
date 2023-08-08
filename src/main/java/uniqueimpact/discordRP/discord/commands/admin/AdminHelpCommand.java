package uniqueimpact.discordRP.discord.commands.admin;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import uniqueimpact.discordRP.discord.commands.Command;
import uniqueimpact.discordRP.discord.utils.AdminChecker;

public class AdminHelpCommand implements Command {

    @Override
    public MessageCreateData run(SlashCommandInteractionEvent command) {

        if (!AdminChecker.isAdmin(command.getMember())) {
             return new MessageCreateBuilder().setContent("You do not have permission to use this command.").build();
        }

        String response =
                """
                **Room Commands**
                `/room create` Create a room
                `/room list` List all of the rooms
                `/room look` Look at a room
                `/room edit` Edit a room
                `/room delete` Delete a room
                
                **Character Commands**
                `/character create` Create a character
                `/character list` List all of the characters, or all characters in a room
                `/character look` Look at a character
                `/character edit` Edit a character
                `/character move` Move a character
                `/character move-all` Move all characters (Excludes NPC characters)
                `/character freeze-all` Freeze all characters (Excludes NPC characters)
                `/character unfreeze-all` Unfreeze all characters (Excludes NPC characters)
                `/character delete` Delete a character
                
                **Door Commands**
                `/door create` Create a door
                `/door list` List all of the doors
                `/door look` Look at a door
                `/door edit` Edit a door
                `/door delete` Delete a door
                
                **Inventory Commands**
                `/inventory storage` Select the roleplay's storage inventory
                `/inventory room` Select a room's inventory
                `/inventory items` Select a character's inventory
                `/inventory clothes` Select a character's clothes
                
                **Item Commands**
                `/item create` Create an item in the currently selected inventory
                `/item list` List all of the items in the currently selected inventory
                `/item look` Look at an item in the currently selected inventory
                `/item edit` Edit an item in the currently selected inventory
                `/item delete` Delete an item from the currently selected inventory
                `/item cut` Cut an item to the clipboard from the currently selected inventory
                `/item copy` Copy an item to the clipboard from the currently selected inventory
                `/item paste` Paste the item from the clipboard into the currently selected inventory
                """;

        return new MessageCreateBuilder().setContent(response).build();

    }

}

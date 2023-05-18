package uniqueimpact.discordRP.discord.commands.admin;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import uniqueimpact.discordRP.discord.commands.Command;
import uniqueimpact.discordRP.discord.utils.AdminChecker;
import uniqueimpact.discordRP.discord.utils.DiscordOutputGenerator;
import uniqueimpact.discordRP.things.Item;
import uniqueimpact.discordRP.things.User;
import uniqueimpact.discordRP.utils.InvalidInputException;

public class ItemCommand implements Command {

    @Override
    public String run(SlashCommandInteractionEvent command) {

        if (!AdminChecker.isAdmin(command.getMember())) {
            return "You do not have permission to use this command.";
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

        String path = command.getFullCommandName();

        if (user.getInventory() == null) {
            return "You must first select an inventory with `/inventory` to use this command.";
        }

        switch (path) {
            case "item create":
                return create(command, user);
            case "item list":
                return list(command, user);
            case "item look":
                return look(command, user);
            case "item edit":
                return edit(command, user);
            case "item delete":
                return delete(command, user);
            case "item copy":
                return copy(command, user);
            case "item paste":
                return paste(command, user);
            default:
                return "Error: Invalid command path (" + path + ")";
        }

    }

    private String create(SlashCommandInteractionEvent command, User user) {

        String name = command.getOption("name").getAsString();
        String description = command.getOption("description") != null ? command.getOption("description").getAsString() : null;
        Double weight = (command.getOption("weight") != null) ? command.getOption("weight").getAsDouble() : 0.0;
        Boolean takeable = (command.getOption("hidden") != null) ? command.getOption("hidden").getAsBoolean() : true;
        Boolean wearable = (command.getOption("wearable") != null) ? command.getOption("wearable").getAsBoolean() : false;
        Boolean infinite = (command.getOption("infinite") != null) ? command.getOption("infinite").getAsBoolean() : false;
        String keyword = command.getOption("keyword") != null ? command.getOption("keyword").getAsString() : null;

        Item item;
        try {
            item = new Item(name, description, weight, takeable, wearable, infinite, keyword);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        user.getInventory().getItems().add(item);
        return "The item was created successfully.";

    }

    private String list(SlashCommandInteractionEvent command, User user) {

        return "List of items in currently selected inventory:\n" + DiscordOutputGenerator.convertItemList(user.getInventory().getItems(), 1900);

    }

    private String look(SlashCommandInteractionEvent command, User user) {

        String name = command.getOption("item").getAsString();
        int num = (command.getOption("num") != null) ? command.getOption("num").getAsInt() : null;

        Item item;
        try {
            item = user.getInventory().findItem(name, num);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        return DiscordOutputGenerator.convertItemAdmin(item);

    }

    private String edit(SlashCommandInteractionEvent command, User user) {

        String name = command.getOption("item").getAsString();
        int num = (command.getOption("num") != null) ? command.getOption("num").getAsInt() : 1;
        String newName = command.getOption("name") != null ? command.getOption("name").getAsString() : null;
        String description = command.getOption("description") != null ? command.getOption("description").getAsString() : null;
        Double weight = (command.getOption("weight") != null) ? command.getOption("weight").getAsDouble() : null;
        Boolean takeable = (command.getOption("hidden") != null) ? command.getOption("hidden").getAsBoolean() : null;
        Boolean wearable = (command.getOption("hidden") != null) ? command.getOption("hidden").getAsBoolean() : null;
        Boolean infinite = (command.getOption("hidden") != null) ? command.getOption("hidden").getAsBoolean() : null;
        String keyword = command.getOption("keyword") != null ? command.getOption("keyword").getAsString() : null;

        Item item;
        try {
            item = user.getInventory().findItem(name, num);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        try {
            item.edit(newName, description, weight, takeable, wearable, infinite, keyword);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        return "The item was edited successfully";

    }

    private String delete(SlashCommandInteractionEvent command, User user) {

        String name = command.getOption("item").getAsString();
        int num = (command.getOption("num") != null) ? command.getOption("num").getAsInt() : 1;

        Item item = null;
        try {
            item = user.getInventory().findItem(name, num);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        user.getInventory().getItems().remove(item);

        return "The item was deleted successfully.";

    }

    private String copy(SlashCommandInteractionEvent command, User user) {

        String name = command.getOption("item").getAsString();
        int num = (command.getOption("num") != null) ? command.getOption("num").getAsInt() : 1;

        Item item = null;
        try {
            item = user.getInventory().findItem(name, num);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        try {
            user.setClipboard(item);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        return "The item was copied to your clipboard successfully.";

    }

    private String paste(SlashCommandInteractionEvent command, User user) {

        if (user.getClipboard() == null) {
            return "You cannot paste an item because your clipboard is currently empty.";
        }

        Item item = user.getClipboard();

        user.getInventory().getItems().add(item);

        return "The item was pasted successfully.";

    }

}

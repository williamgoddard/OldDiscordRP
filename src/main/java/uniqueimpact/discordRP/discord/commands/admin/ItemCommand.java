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
                user = new User(command.getMember());
                roleplay.addUser(user);
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
                return list(user);
            case "item look":
                return look(command, user);
            case "item edit":
                return edit(command, user);
            case "item delete":
                return delete(command, user);
            case "item cut":
                return cut(command, user);
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
        String description = command.getOption("description").getAsString();
        Double weight = (command.getOption("weight") != null) ? command.getOption("weight").getAsDouble() : 1.0;
        Integer quantity = (command.getOption("quantity") != null) ? command.getOption("quantity").getAsInt() : 1;
        Boolean takeable = (command.getOption("takeable") != null) ? command.getOption("takeable").getAsBoolean() : true;
        Boolean wearable = (command.getOption("wearable") != null) ? command.getOption("wearable").getAsBoolean() : false;
        Boolean infinite = (command.getOption("infinite") != null) ? command.getOption("infinite").getAsBoolean() : false;
        String keyword = command.getOption("keyword") != null ? command.getOption("keyword").getAsString() : null;

        Item item;
        try {
            item = new Item(name, description, weight, quantity, takeable, wearable, infinite, keyword);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        user.getInventory().addItem(item);
        return "The item was created successfully.";

    }

    private String list(User user) {

        return "List of items in currently selected inventory:\n" + DiscordOutputGenerator.convertItemList(user.getInventory().getItems(), 1900);

    }

    private String look(SlashCommandInteractionEvent command, User user) {

        String name = command.getOption("item").getAsString();
        int num = (command.getOption("num") != null) ? command.getOption("num").getAsInt() : 1;

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
        Integer quantity = (command.getOption("quantity") != null) ? command.getOption("quantity").getAsInt() : null;
        Boolean takeable = (command.getOption("takeable") != null) ? command.getOption("takeable").getAsBoolean() : null;
        Boolean wearable = (command.getOption("wearable") != null) ? command.getOption("wearable").getAsBoolean() : null;
        Boolean infinite = (command.getOption("infinite") != null) ? command.getOption("infinite").getAsBoolean() : null;
        String keyword = command.getOption("keyword") != null ? command.getOption("keyword").getAsString() : null;

        Item item;
        try {
            item = user.getInventory().findItem(name, num);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        String response = "";

        if (newName != null) {
            try {
                item.setName(newName);
                user.getInventory().delItem(item);
                user.getInventory().addItem(item);
                response += "The item's name was edited successfully.\n";
            } catch (InvalidInputException e) {
                response += "The item's name was not edited: " + e.getMessage() + "\n";
            }
        }

        if (description != null) {
            try {
                item.setDescription(description);
                response += "The item's description was edited successfully.\n";
            } catch (InvalidInputException e) {
                response += "The item's description was not edited: " + e.getMessage() + "\n";
            }
        }

        if (weight != null) {
            try {
                item.setWeight(weight);
                response += "The item's weight was edited successfully.\n";
            } catch (InvalidInputException e) {
                response += "The item's weight was not edited: " + e.getMessage() + "\n";
            }
        }

        if (quantity != null) {
            try {
                item.setQuantity(quantity);
                response += "The item's quantity was edited successfully.\n";
            } catch (InvalidInputException e) {
                response += "The item's quantity was not edited: " + e.getMessage() + "\n";
            }
        }

        if (takeable != null) {
            item.setTakeable(takeable);
            response += "The item's takeable value was edited successfully.\n";
        }

        if (wearable != null) {
            item.setWearable(wearable);
            response += "The item's wearable value was edited successfully.\n";
        }

        if (infinite != null) {
            item.setInfinite(infinite);
            response += "The item's infinite value was edited successfully.\n";
        }

        if (keyword != null) {
            try {
                item.setKeyword(keyword);
                response += "The item's keyword was edited successfully.\n";
            } catch (InvalidInputException e) {
                response += "The item's keyword was not edited: " + e.getMessage() + "\n";
            }
        }

        if (response.equals("")) {
            return "The item was not edited: At least one field must be selected for editing.";
        }

        return response;

    }

    private String delete(SlashCommandInteractionEvent command, User user) {

        String name = command.getOption("item").getAsString();
        int num = (command.getOption("num") != null) ? command.getOption("num").getAsInt() : 1;

        Item item;
        try {
            item = user.getInventory().findItem(name, num);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        user.getInventory().delItem(item);

        return "The item was deleted successfully.";

    }

    private String cut(SlashCommandInteractionEvent command, User user) {

        String name = command.getOption("item").getAsString();
        int num = (command.getOption("num") != null) ? command.getOption("num").getAsInt() : 1;

        Item item;
        try {
            item = user.getInventory().findItem(name, num);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        try {
            user.setClipboard(item.getCopy());
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        user.getInventory().delItem(item);

        return "The item was cut to your clipboard successfully.";

    }

    private String copy(SlashCommandInteractionEvent command, User user) {

        String name = command.getOption("item").getAsString();
        int num = (command.getOption("num") != null) ? command.getOption("num").getAsInt() : 1;

        Item item;
        try {
            item = user.getInventory().findItem(name, num);
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        try {
            user.setClipboard(item.getCopy());
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

        try {
            user.getInventory().getItems().add(item.getCopy());
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        return "The item was pasted successfully.";

    }

}

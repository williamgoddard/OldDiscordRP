package uniqueimpact.discordRP.things;

import uniqueimpact.discordRP.utils.InputChecker;
import uniqueimpact.discordRP.utils.InvalidInputException;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String discordId;
    private Item clipboard;
    private Inventory inventory;

    public User(String discordId, Item clipboard, Inventory inventory) throws InvalidInputException {

        if (!InputChecker.validDiscordID(discordId)) {
            throw new InvalidInputException("Discord user ID must be an 18 digit number.");
        }

        this.discordId = discordId;
        this.clipboard = clipboard;
        this.inventory = inventory;

    }

    public String getDiscordId() {
        return discordId;
    }

    public void setDiscordId(String discordId) throws InvalidInputException {
        if (!InputChecker.validDiscordID(discordId)) {
            throw new InvalidInputException("Discord user ID must be an 18 digit number.");
        }
        this.discordId = discordId;
    }

    public Item getClipboard() {
        return clipboard;
    }

    public void setClipboard(Item clipboard) {
        this.clipboard = clipboard;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}

package uniqueimpact.discordRP.things;

import net.dv8tion.jda.api.entities.Member;
import uniqueimpact.discordRP.utils.InvalidInputException;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String discordId;
    private Item clipboard;
    private Inventory inventory;

    public User(Member member, Item clipboard, Inventory inventory) throws InvalidInputException {

        if (member == null) {
            throw new InvalidInputException("Discord Member must be assigned.");
        }

        this.discordId = member.getId();
        this.clipboard = clipboard;
        this.inventory = inventory;

    }

    public User(Member member) throws InvalidInputException {

        if (member == null) {
            throw new InvalidInputException("Discord Member must be assigned.");
        }

        this.discordId = member.getId();
        this.clipboard = null;
        this.inventory = null;

    }


    public String getDiscordId() {
        return discordId;
    }

    public Item getClipboard() {
        return clipboard;
    }

    public void setClipboard(Item clipboard) throws InvalidInputException {
        this.clipboard = clipboard.getCopy();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

}

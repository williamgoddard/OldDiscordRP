package uniqueimpact.discordRP.things;

import java.io.Serializable;

import uniqueimpact.discordRP.utils.InputChecker;
import uniqueimpact.discordRP.utils.InvalidInputException;

public class Player implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String channel;
	private String webhook;
	private String name;
	private String displayName;
	private String picture;
	private String description;
	private Boolean hidden;
	private Inventory inv;
	private Inventory clothes;
	private Room room;
	
	public Player(String channel, String webhook, String name, String displayName, String picture, String description, Boolean hidden, Inventory inv, Inventory clothes, Room room) throws InvalidInputException {
		if (!InputChecker.validDiscordID(channel)) {
			throw new InvalidInputException("Discord channel ID must be an 18 digit number.");
		}
		if (!InputChecker.validName(name)) {
			throw new InvalidInputException("Name must be 32 characters at most, and may use only letters, numbers, hyphens and underscores.");
		}
		if (!InputChecker.validDescription(description)) {
			throw new InvalidInputException("Description must be at most 1500 characters.");
		}
		this.channel = channel;
		this.name = name;
		this.displayName = displayName;
		this.picture = picture;
		this.description = description;
		this.inv = inv;
		this.clothes = clothes;
		this.room = room;
		this.webhook = webhook;
		this.hidden = hidden;
	}

	public void edit(String channelId, String webhook, String newName, String displayName, String picture, String description, Boolean hidden, Double itemsCapacity, Double clothesCapacity) throws InvalidInputException {

		if (channelId == null && webhook == null && newName == null && displayName == null && picture == null && description == null && hidden == null && itemsCapacity == null && clothesCapacity == null) {
			throw new InvalidInputException("At least one field must be selected for editing.");
		}

		if (!(channelId == null || InputChecker.validDiscordID(channelId))) {
			throw new InvalidInputException("Discord channel ID must be an 18 digit number.");
		}

		if (!(newName == null || InputChecker.validName(newName))) {
			throw new InvalidInputException("Name must be 32 characters at most, and may use only letters, numbers, hyphens and underscores.");
		}

		if (!(displayName == null || InputChecker.validName(displayName))) {
			throw new InvalidInputException("Display name must be 32 characters at most, and may use only letters, numbers, hyphens and underscores.");
		}

		if (!(description == null || InputChecker.validDescription(description))) {
			throw new InvalidInputException("Description must be at most 1500 characters.");
		}

		if (!(itemsCapacity == null || itemsCapacity >= 0)) {
			throw new InvalidInputException("Inventory capacity must be at least 0.");
		}

		if (!(clothesCapacity == null || clothesCapacity >= 0)) {
			throw new InvalidInputException("Clothes capacity must be at least 0.");
		}

		if (channelId != null) {
			this.channel = channelId;
		}

		if (webhook != null) {
			this.webhook = webhook;
		}

		if (newName != null) {
			this.name = newName;
		}

		if (displayName != null) {
			this.displayName = displayName;
		}

		if (picture != null) {
			this.picture = picture;
		}

		if (description != null) {
			this.description = description;
		}

		if (hidden != null) {
			this.hidden = hidden;
		}

		if (itemsCapacity != null) {
			this.inv.setCapacity(itemsCapacity);
		}

		if (clothesCapacity != null) {
			this.clothes.setCapacity(clothesCapacity);
		}

	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) throws InvalidInputException {
		if (!InputChecker.validDiscordID(channel)) {
			throw new InvalidInputException("Discord channel ID must be an 18 digit number.");
		}
		this.channel = channel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws InvalidInputException {
		if (!InputChecker.validName(name)) {
			throw new InvalidInputException("Name must be 32 characters at most, and may use only letters, numbers, hyphens and underscores.");
		}
		this.name = name;
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String firstName) throws InvalidInputException {
		if (!InputChecker.validDisplayName(firstName)) {
			throw new InvalidInputException("Display name must be 32 characters at most, and may use only letters, numbers, hyphens, underscores and spaces.");
		}
		this.displayName = firstName;
	}
	
	public String getPicture() {
		return picture;
	}
	
	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) throws InvalidInputException {
		if (!InputChecker.validDescription(description)) {
			throw new InvalidInputException("Description must be at most 1500 characters.");
		}
		this.description = description;
	}
	
	public Inventory getInv() {
		return inv;
	}

	public void setInv(Inventory inv) {
		this.inv = inv;
	}
	
	public Boolean isHidden() {
		return hidden;
	}
	
	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	public Inventory getClothes() {
		return clothes;
	}

	public void setClothes(Inventory clothes) {
		this.clothes = clothes;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public String getWebhook() {
		return webhook;
	}

	public void setWebhook(String webhook) {
		this.webhook = webhook;
	}

}

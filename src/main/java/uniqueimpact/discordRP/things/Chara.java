package uniqueimpact.discordRP.things;

import java.io.Serializable;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import uniqueimpact.discordRP.discord.utils.WebhookManager;
import uniqueimpact.discordRP.utils.InputChecker;
import uniqueimpact.discordRP.utils.InvalidInputException;

public class Chara implements Serializable {
	
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

	public Chara(TextChannel channel, String name, String displayName, String picture, String description, boolean hidden, double inv_capacity, double clothes_capacity, Room room) throws InvalidInputException {

		if (channel == null) {
			throw new InvalidInputException("Character Discord Channel must be assigned.");
		}

		if (name == null) {
			throw new InvalidInputException("Character name must be assigned.");
		}

		if (description == null) {
			throw new InvalidInputException("Character description must be assigned.");
		}

		if (room == null) {
			throw new InvalidInputException("Room must be assigned.");
		}

		if (name.length() < 1 || name.length() > 32) {
			throw new InvalidInputException("Character name must be between 1 and 32 characters.");
		}

		if (displayName.length() < 1 || displayName.length() > 32) {
			throw new InvalidInputException("Character display name must be between 1 and 32 characters.");
		}

		if (description.length() < 1 || description.length() > 1500) {
			throw new InvalidInputException("Description name must be between 1 and 1500 characters.");
		}

		if (room == null) {
			throw new InvalidInputException("Room must be assigned.");
		}

		this.channel = channel.getId();
		this.webhook = WebhookManager.createOrGetWebhook(channel);
		this.name = name;
		this.displayName = displayName == null ? name : displayName;
		this.picture = picture;
		this.description = description;
		this.hidden = hidden;
		this.inv = new Inventory(inv_capacity);
		this.clothes = new Inventory(clothes_capacity);
		this.room = room;
	}

	// Get the character's channel
	public String getChannel() {
		return channel;
	}

	// Get the character's webhook
	public String getWebhook() {
		return webhook;
	}

	// Set the character's channel
	public void setChannel(TextChannel channel) {

		if (channel == null) {
			return;
		}

		this.channel = channel.getName();
		this.webhook = WebhookManager.createOrGetWebhook(channel);

	}

	// Get the character's name
	public String getName() {
		return name;
	}

	// Set the character's name
	public void setName(String name) throws InvalidInputException {

		if (name == null) {
			return;
		}

		if (name.length() < 1 || name.length() > 32) {
			throw new InvalidInputException("Character name must be between 1 and 32 characters.");
		}

		this.name = name;
	}

	// Get the chararacter's display name
	public String getDisplayName() {
		return displayName;
	}

	// Set the character's display name
	public void setDisplayName(String displayName) throws InvalidInputException {

		if (displayName == null) {
			return;
		}

		if (displayName.length() < 1 || displayName.length() > 32) {
			throw new InvalidInputException("Character display name must be between 1 and 32 characters.");
		}

		this.displayName = displayName;
	}

	// Get the character's picture
	public String getPicture() {
		return picture;
	}

	// Set the character's picture
	public void setPicture(String picture) {
		if (picture == null) {
			return;
		}

		this.picture = picture;
	}

	// Get the character's description
	public String getDescription() {
		return description;
	}

	// Set the character's description
	public void setDescription(String description) throws InvalidInputException {
		if (description == null) {
			return;
		}

		if (description.length() < 1 || description.length() > 1500) {
			throw new InvalidInputException("Description name must be between 1 and 1500 characters.");
		}

		this.description = description;
	}

	// Get whether the character is hidden
	public boolean isHidden() {
		return hidden;
	}

	// Set whether the character is hidden
	public void setHidden(Boolean hidden) {
		if (hidden == null) {
			return;
		}

		this.hidden = hidden;
	}

	// Get the character's inventory
	public Inventory getInv() {
		return inv;
	}

	// Get the character's clothes
	public Inventory getClothes() {
		return clothes;
	}

	// Get the character's room
	public Room getRoom() {
		return room;
	}

	// Set the character's room
	public void setRoom(Room room) {
		if (room == null) {
			return;
		}

		this.room = room;
	}

	@Deprecated
	public Chara(String channel, String webhook, String name, String displayName, String picture, String description, boolean hidden, Inventory inv, Inventory clothes, Room room) throws InvalidInputException {

		if (name.length() < 1 || name.length() > 32) {
			throw new InvalidInputException("Character name must be between 1 and 32 characters.");
		}

		if (displayName.length() < 1 || displayName.length() > 32) {
			throw new InvalidInputException("Character display name must be between 1 and 32 characters.");
		}

		if (description.length() < 1 || description.length() > 1500) {
			throw new InvalidInputException("Description name must be between 1 and 1500 characters.");
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

	@Deprecated
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

	// Set the character's channel
	@Deprecated
	public void setChannel(String channel) throws InvalidInputException {

		if (channel == null) {
			return;
		}

		this.channel = channel;

	}

}

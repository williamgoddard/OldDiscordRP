package uniqueimpact.discordRP.things;

import java.io.Serializable;

import uniqueimpact.discordRP.utils.InputChecker;
import uniqueimpact.discordRP.utils.InvalidInputException;

public class Player implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String channel;
	private String webhook;
	private String firstName;
	private String displayName;
	private String picture;
	private String description;
	private String sex;
	private int height;
	private int weight;
	private Boolean hidden;
	private Inventory inv;
	private Inventory clothes;
	private Room room;
	
	public Player(String channel, String webhook, String name, String displayName, String picture, String description, String gender, int height, int weight, Boolean hidden, Inventory inv, Inventory clothes, Room room) throws InvalidInputException {
		if (!InputChecker.validDiscordID(channel)) {
			throw new InvalidInputException("Discord channel ID must be an 18 digit number.");
		}
		if (!InputChecker.validName(name)) {
			throw new InvalidInputException("Name must be 32 characters at most, and may use only letters, numbers, hyphens and underscores.");
		}
		if (!InputChecker.validDescription(description)) {
			throw new InvalidInputException("Description must be at most 1500 characters.");
		}
		if (!InputChecker.validName(gender)) {
			throw new InvalidInputException("Gender must be 32 characters at most, and may use only letters, numbers, hyphens and underscores.");
		}
		this.channel = channel;
		this.firstName = name;
		this.displayName = displayName;
		this.picture = picture;
		this.description = description;
		this.sex = gender;
		this.height = height;
		this.weight = weight;
		this.inv = inv;
		this.clothes = clothes;
		this.room = room;
		this.webhook = webhook;
		this.hidden = hidden;
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
		return firstName;
	}

	public void setName(String firstName) throws InvalidInputException {
		if (!InputChecker.validName(firstName)) {
			throw new InvalidInputException("Name must be 32 characters at most, and may use only letters, numbers, hyphens and underscores.");
		}
		this.firstName = firstName;
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

	public String getGender() {
		return sex;
	}

	public void setGender(String gender) throws InvalidInputException {
		if (!InputChecker.validName(gender)) {
			throw new InvalidInputException("Gender must be 32 characters at most, and may use only letters, numbers, hyphens and underscores.");
		}
		this.sex = gender;
	}
	
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
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

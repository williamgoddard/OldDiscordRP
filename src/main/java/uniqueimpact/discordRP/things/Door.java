package uniqueimpact.discordRP.things;

import java.io.Serializable;

import uniqueimpact.discordRP.utils.InputChecker;
import uniqueimpact.discordRP.utils.InvalidInputException;

public class Door implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Room room1;
	private Room room2;
	private boolean hidden;
	private boolean locked;
	private String keyword;
	
	public Door(Room room1, Room room2, boolean hidden, boolean locked, String keyword) throws InvalidInputException {

		if (room1 == null) {
			throw new InvalidInputException("Room 1 must be assigned.");
		}

		if (room2 == null) {
			throw new InvalidInputException("Room 2 must be assigned.");
		}

		if (room1 == room2) {
			throw new InvalidInputException("Room 1 and Room 2 must be different.");
		}

		if (keyword.length() < 1 || keyword.length() > 32) {
			throw new InvalidInputException("Keyword must be between 1 and 32 characters.");
		}

		this.room1 = room1;
		this.room2 = room2;
		this.hidden = hidden;
		this.locked = locked;
		this.keyword = keyword.equalsIgnoreCase("none") ? null : keyword;
	}

	// Get the door's first room
	public Room getRoom1() {
		return room1;
	}

	// Get the door's second room
	public Room getRoom2() {
		return room2;
	}

	// Get whether the door is hidden
	public boolean isHidden() {
		return hidden;
	}

	// Set whether the door is hidden
	public void setHidden(Boolean hidden) {
		if (hidden == null) {
			return;
		}

		this.hidden = hidden;
	}

	// Get whether the door is locked
	public boolean isLocked() {
		return locked;
	}

	// Set whether the door is locked
	public void setLocked(Boolean locked) {
		if (locked == null) {
			return;
		}

		this.locked = locked;
	}

	// Get the door's keyword, or null if none is assigned
	public String getKeyword() {
		return keyword;
	}

	// Set the door's keyword. Clear the keyword if set to "none"
	public void setKeyword(String keyword) throws InvalidInputException {

		if (keyword == null) {
			return;
		}

		if (keyword.length() < 1 || keyword.length() > 32) {
			throw new InvalidInputException("Keyword must be between 1 and 32 characters.");
		}

		this.keyword = keyword.equalsIgnoreCase("none") ? null : keyword;
	}
	
	// Get the other room connected do this door
	public Room getOtherRoom(Room room) throws InvalidInputException {
		if (room == room1) {
			return room2;
		} else if (room == room2) {
			return room1;
		}
		throw new InvalidInputException("The door is not connected to the specified room.");
	}

	@Deprecated
	public void edit(Boolean hidden, Boolean locked, String lock) throws InvalidInputException {

		if (hidden == null && locked == null && lock == null) {
			throw new InvalidInputException("At least one field must be selected for editing.");
		}

		if (!(lock == null || InputChecker.validName(lock))) {
			throw new InvalidInputException("Keyword must be 32 characters at most, and may use only letters, numbers, hyphens and underscores.");
		}

		if (hidden != null) {
			this.hidden = hidden;
		}

		if (locked != null) {
			this.locked = locked;
		}

		if (lock != null) {
			this.keyword = lock;
		}

	}

}

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
		if (!InputChecker.validKey(keyword)) {
			throw new InvalidInputException("Lock must be 32 characters at most, and may use only letters, numbers, hyphens and underscores.");
		}
		this.room1 = room1;
		this.room2 = room2;
		this.hidden = hidden;
		this.locked = locked;
		this.keyword = keyword;
	}

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

	public Room getRoom1() {
		return room1;
	}

	public void setRoom1(Room room1) {
		this.room1 = room1;
	}

	public Room getRoom2() {
		return room2;
	}

	public void setRoom2(Room room2) {
		this.room2 = room2;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) throws InvalidInputException {
		if (!InputChecker.validKey(keyword)) {
			throw new InvalidInputException("Keyword must be 32 characters at most, and may use only letters, numbers, hyphens and underscores.");
		}
		this.keyword = keyword;
	}
	
	public Room getOtherRoom(Room room) {
		if (room == room1) {
			return room2;
		} else {
			return room1;
		}
	}

}

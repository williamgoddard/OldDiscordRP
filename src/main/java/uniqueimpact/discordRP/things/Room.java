package uniqueimpact.discordRP.things;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import uniqueimpact.discordRP.utils.InputChecker;
import uniqueimpact.discordRP.utils.InvalidInputException;

public class Room implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String description;
	private Inventory inv;
	private List<Player> players;
	private List<Door> doors;
	
	public Room(String name, String description, Inventory inv) throws InvalidInputException {

		if (!InputChecker.validName(name)) {
			throw new InvalidInputException("Name must be 40 characters at most, and may use only letters, numbers, hyphens and underscores.");
		}

		if (!InputChecker.validDescription(description)) {
			throw new InvalidInputException("Description must be at most 1500 characters.");
		}

		this.name = name;
		this.description = description;
		this.inv = inv;
		this.players = new ArrayList<>();
		this.doors = new ArrayList<>();

	}

	public void edit(String name, String description, Double capacity) throws InvalidInputException {

		if (name == null && description == null && capacity == null) {
			throw new InvalidInputException("At least one field must be selected for editing.");
		}

		if (!(name == null || InputChecker.validName(name))) {
			throw new InvalidInputException("Name must be 40 characters at most, and may use only letters, numbers, hyphens and underscores.");
		}

		if (!(description == null || InputChecker.validDescription(description))) {
			throw new InvalidInputException("Description must be at most 1500 characters.");
		}

		if (!(capacity == null || capacity >= 0)) {
			throw new InvalidInputException("Inventory capacity must be at least 0.");
		}

		if (name != null) {
			this.name = name;
		}

		if (description != null) {
			this.description = description;
		}

		if (capacity != null) {
			this.inv.setCapacity(capacity);
		}

	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws InvalidInputException {

		if (!InputChecker.validName(name)) {
			throw new InvalidInputException("Name must be 40 characters at most, and may use only letters, numbers, hyphens and underscores.");
		}

		this.name = name;
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

	public List<Player> getPlayers() {
		return players;
	}
	
	public List<Player> getPlayers(Boolean includeHidden) {

		List<Player> resultPlayers = new ArrayList<>();
		for (Player player : players) {
			if (includeHidden || !player.isHidden()) {
				resultPlayers.add(player);
			}
		}

		return resultPlayers;

	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public List<Door> getDoors() {
		return doors;
	}
	
	public List<Door> getDoors(boolean includeHidden) {

		List<Door> resultDoors = new ArrayList<>();
		for (Door door : doors) {
			if (includeHidden || !door.isHidden()) {
				resultDoors.add(door);
			}
		}

		return resultDoors;

	}
	
	public List<Door> getSpecificDoors(boolean locked) {

		List<Door> resultDoors = new ArrayList<>();
		for (Door door : doors) {
			if (locked == door.isLocked()) {
				resultDoors.add(door);
			}
		}

		return resultDoors;

	}
	
	public List<Door> getSpecificDoors(boolean locked, boolean includeHidden) {

		List<Door> resultDoors = new ArrayList<>();
		for (Door door : doors) {
			if ((includeHidden || !door.isHidden()) && (locked == door.isLocked())) {
				resultDoors.add(door);
			}
		}

		return resultDoors;

	}

	public void setDoors(List<Door> doors) {
		this.doors = doors;
	}

	public Door findDoor(Room room) throws InvalidInputException {

		for (Door door : this.doors) {
			if (door.getRoom1().equals(room) || door.getRoom2().equals(room)) {
				return door;
			}
		}

		throw new InvalidInputException("Door not found.");

	}

}

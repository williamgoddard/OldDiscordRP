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
	private List<Chara> characters;
	private List<Door> doors;

	public Room(String name, String description, double capacity) throws InvalidInputException {

		if (name == null) {
			throw new InvalidInputException("Room name must be assigned.");
		}

		if (name.length() < 1 || name.length() > 32) {
			throw new InvalidInputException("Room name must be between 1 and 32 characters.");
		}

		if (description.length() < 1 || description.length() > 1500) {
			throw new InvalidInputException("Description name must be between 1 and 1500 characters.");
		}

		this.name = name;
		this.description = description;
		this.inv = new Inventory(capacity);
		this.characters = new ArrayList<>();
		this.doors = new ArrayList<>();

	}

	// Get the room's name
	public String getName() {
		return name;
	}

	// Set the room's name
	public void setName(String name) throws InvalidInputException {

		if (name == null) {
			return;
		}

		if (name.length() < 1 || name.length() > 32) {
			throw new InvalidInputException("Room name must be between 1 and 32 characters.");
		}

		this.name = name;
	}

	// Get the room's description
	public String getDescription() {
		return description;
	}

	// Set the room's description
	public void setDescription(String description) throws InvalidInputException {

		if (description == null) {
			return;
		}

		if (name.length() < 1 || name.length() > 1500) {
			throw new InvalidInputException("Description name must be between 1 and 1500 characters.");
		}

		this.description = description;
	}

	// Get the room's inventory
	public Inventory getInv() {
		return inv;
	}

	// Get the room's players
	public List<Chara> getCharacters() {
		return characters;
	}

	// Get the room's players which match hidden
	public List<Chara> getCharacters(Boolean hidden) {

		List<Chara> resultPlayers = new ArrayList<>();
		for (Chara player : characters) {
			if (hidden == null || player.isHidden() == hidden) {
				resultPlayers.add(player);
			}
		}

		return resultPlayers;

	}

	// Get the room's doors
	public List<Door> getDoors() {
		return doors;
	}

	//Get the room's doors which match hidden and locked
	public List<Door> getDoors(Boolean hidden, Boolean locked) {

		List<Door> resultDoors = new ArrayList<>();
		for (Door door : doors) {
			if ((hidden == null || door.isHidden() == hidden) && (locked == null || door.isLocked() == locked)) {
				resultDoors.add(door);
			}
		}

		return resultDoors;

	}

	// Find a specific door by the other room, matching hidden and locked
	public Door findDoor(Room otherRoom, Boolean hidden, Boolean locked) throws InvalidInputException {

		for (Door door : this.doors) {
			if (door.getOtherRoom(this) == otherRoom) {
				if ((hidden == null || door.isHidden() == hidden) && (locked == null || door.isLocked() == locked)) {
					return door;
				}
				break;
			}
		}

		throw new InvalidInputException("There is no door from room `" + this.getName() + "` to room `" + otherRoom.getName() + "`.");

	}

	// Find a specific door by the other room
	public Door findDoor(Room otherRoom) throws InvalidInputException {
		return this.findDoor(otherRoom, null, null);
	}

	// Find a specific door by the name and number of the other room, matching hidden and locked
	public Door findDoor(String otherRoomName, int num, Boolean locked, Boolean hidden) throws InvalidInputException {

		int doorsFound = 0;

		for (Door door : getSpecificDoors(locked, hidden)) {
			if (door.getOtherRoom(this).getName().toLowerCase().equals(otherRoomName)) {
				doorsFound++;
				if (doorsFound == num) {
					return door;
				}
			}
		}

		throw new InvalidInputException("Door not found. There are " + doorsFound + " matching doors.");

	}

	// Find a specific door by the name and number of the other room
	public Door findDoor(String otherRoomName, int num) throws InvalidInputException {
		return findDoor(otherRoomName, num, null, null);
	}

	// Find a specific character in the room by name, matching hidden
	public Chara findCharacter(String name, Boolean hidden) throws InvalidInputException {

		if (this.name == null) {
			throw new InvalidInputException("Character name must be assigned.");
		}

		if (this.name.length() < 1 || this.name.length() > 32) {
			throw new InvalidInputException("Character name must be between 1 and 32 characters.");
		}

		for (Chara character : getCharacters()) {
			if (character.getName().equalsIgnoreCase(name)) {
				if (hidden == null || character.isHidden() == hidden) {
					return character;
				}
				break;
			}
		}

		throw new InvalidInputException("The character `" + this.name + "` could not be found.");

	}

	// Find a specific character in the room by name
	public Chara findCharacter(String name) throws InvalidInputException {
		return this.findCharacter(name, null);
	}

	// Add a character to the room
	public void addCharacter(Chara character) {
		this.characters.add(character);
	}

	// Add a door to the room
	public void addDoor(Door door) {
		this.doors.add(door);
	}

	// Remove a character from the room
	public void delCharacter(Chara character) {
		this.characters.remove(character);
	}

	// Remove a door from the room
	public void delDoor(Door door) {
		this.doors.remove(door);
	}

	@Deprecated
	public void edit(String name, String description, Double capacity) throws InvalidInputException {

		if (name == null && description == null && capacity == null) {
			throw new InvalidInputException("At least one field must be selected for editing.");
		}

		if (!(name == null || InputChecker.validName(name))) {
			throw new InvalidInputException("Name must be 32 characters at most, and may use only letters, numbers, hyphens and underscores.");
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

	@Deprecated
	public Room(String name, String description, Inventory inv) throws InvalidInputException {

		if (name.length() < 1 || name.length() > 32) {
			throw new InvalidInputException("Room name must be between 1 and 32 characters.");
		}

		if (name.length() < 1 || name.length() > 1500) {
			throw new InvalidInputException("Description name must be between 1 and 1500 characters.");
		}

		this.name = name;
		this.description = description;
		this.inv = inv;
		this.characters = new ArrayList<>();
		this.doors = new ArrayList<>();

	}

	@Deprecated
	public List<Door> getSpecificDoors(boolean locked, boolean includeHidden) {

		List<Door> resultDoors = new ArrayList<>();
		for (Door door : doors) {
			if ((includeHidden || !door.isHidden()) && (locked == door.isLocked())) {
				resultDoors.add(door);
			}
		}

		return resultDoors;

	}

}

package uniqueimpact.discordRP.things;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import uniqueimpact.discordRP.utils.InvalidInputException;

public class Room implements Serializable, Comparable<Room> {
	
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
	public List<Door> getDoors(Boolean locked, Boolean hidden) {

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

		for (Door door : getDoors(locked, hidden)) {
			if (door.getOtherRoom(this).getName().equalsIgnoreCase(otherRoomName)) {
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

		throw new InvalidInputException("The character `" + name + "` could not be found.");

	}

	// Find a specific character in the room by name
	public Chara findCharacter(String name) throws InvalidInputException {
		return this.findCharacter(name, null);
	}

	// Add a character to the room
	public void addCharacter(Chara character) {
		if (character == null) {
			return;
		}

		int p1 = 0;
		int p2 = characters.size();
		while (p1 < p2) {
			int midpoint = (p1 + p2) / 2;
			Chara currentChara = characters.get(midpoint);
			if (character.compareTo(currentChara) > 0) {
				p1 = midpoint + 1;
			} else {
				p2 = midpoint;
			}
		}

		characters.add(p1, character);
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

	@Override
	public int compareTo(@NotNull Room room) {

		return (this.name.compareToIgnoreCase(room.getName()));

	}
}

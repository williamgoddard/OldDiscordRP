package uniqueimpact.discordRP.things;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import uniqueimpact.discordRP.utils.InvalidInputException;

public class Roleplay implements Serializable {

	private static final long serialVersionUID = 1L;

	private static Roleplay instance;

	private List<User> users;
	private List<Room> rooms;
	private List<Chara> charas;


	private Roleplay() {
		try {
			load();
			users = instance.users;
			rooms = instance.rooms;
			charas = instance.charas;
		} catch (Exception e) {
			users = new ArrayList<>();
			rooms = new ArrayList<>();
			charas = new ArrayList<>();
		}
	}

	// Get the roleplay instance
	public static Roleplay getInstance() {
		if (instance == null) {
			instance = new Roleplay();
		}
		return instance;
	}

	// Saves all data to files
	public void save() {
		try {
			FileOutputStream fileOut = new FileOutputStream("savedata");
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(instance);
            objectOut.close();
		} catch (Exception e) {
            e.printStackTrace();
        } 
	}

	// Loads data from files
	private void load() throws Exception {
		FileInputStream fileIn = new FileInputStream("savedata");
		ObjectInputStream objectIn = new ObjectInputStream(fileIn);
		instance = (Roleplay) objectIn.readObject();
		objectIn.close();
	}

	// Get the list of users registered to the roleplay
	public List<User> getUsers() {
		return users;
	}

	// Get the list of rooms registered to the roleplay
	public List<Room> getRooms() {
		return rooms;
	}

	// Get the list of characters registered to the roleplay
	public List<Chara> getCharas() {
		return charas;
	}

	// Find a user registered to the roleplay from their Discord User ID
	public User findUser(String discordId) throws InvalidInputException {

		if (discordId == null) {
			throw new InvalidInputException("Discord User ID must be assigned.");
		}

		for (User user : users) {
			if (user.getDiscordId().equals(discordId)) {
				return user;
			}
		}

		throw new InvalidInputException("No registered user with Discord ID `" + discordId + "` could be found.");

	}

	// Find a room registered to the roleplay from its name and number
	public Room findRoom(String name, int num) throws InvalidInputException {

		if (name == null) {
			throw new InvalidInputException("Room name must be assigned.");
		}

		if (name.length() < 1 || name.length() > 32) {
			throw new InvalidInputException("Room name must be between 1 and 32 characters.");
		}

		if (num < 1) {
			throw new InvalidInputException("Room number must be at least 1.");
		}

		int matchingRooms = 0;
		for (Room room : rooms) {
			if (room.getName().equalsIgnoreCase(name)) {
				matchingRooms += 1;
				if (matchingRooms >= num) {
					return room;
				}
			}
		}

		throw new InvalidInputException("Room not found. There are " + matchingRooms + " rooms named `" + name + "`.");

	}

	// Find a room registered to the roleplay from its name
	public Room findRoom(String name) throws InvalidInputException {
		return this.findRoom(name, 1);
	}

	// Find a character registered to the roleplay from its name, and whether it's hidden
	public Chara findCharacter(String name, Boolean hidden) throws InvalidInputException {

		if (name == null) {
			throw new InvalidInputException("Character name must be assigned.");
		}

		for (Chara chara : charas) {
			if (chara.getName().equalsIgnoreCase(name)) {
				if (hidden == null || chara.isHidden() == hidden) {
					return chara;
				}
				break;
			}
		}

		throw new InvalidInputException("The character `" + name + "` could not be found.");

	}

	// Find a character registered to the roleplay from its name
	public Chara findCharacter(String name) throws InvalidInputException {
		return this.findCharacter(name, null);
	}

	// Find a character registered to the roleplay from its channel, and whether it's hidden
	public Chara findCharacterByChannel(String discordId, Boolean hidden) throws InvalidInputException {

		if (discordId == null) {
			throw new InvalidInputException("Discord Channel ID must be assigned.");
		}

		for (Chara chara : charas) {
			if (chara.getChannel().equals(discordId)) {
				if (hidden == null || chara.isHidden() == hidden) {
					return chara;
				}
				break;
			}
		}

		throw new InvalidInputException("The character could not be found.");

	}

	// Find a character registered to the roleplay from its channel
	public Chara findCharacterByChannel(String discordId) throws InvalidInputException {
		return findCharacterByChannel(discordId, null);
	}

	// Register a user to the roleplay
	public void addUser(User user) {
		this.users.add(user);
	}

	// Register a room to the roleplay
	public void addRoom(Room room) {
		this.rooms.add(room);
	}

	// Register a character to the roleplay
	public void addCharacter(Chara character) {
		this.charas.add(character);
	}

	// Delete a user from the roleplay
	public void delUser(User user) {
		this.users.remove(user);
	}

	// Delete a room from the roleplay
	public void delRoom(Room room) throws InvalidInputException {

		this.rooms.remove(room);

		for (Door door : room.getDoors()) {
			door.getOtherRoom(room).delDoor(door);
		}

		for (User user : users) {
			if (user.getInventory() == room.getInv()) {
				user.setInventory(null);
			}
		}

	}

	// Delete a character from the roleplay
	public void delCharacter(Chara character) {

		for (User user : users) {
			if (user.getInventory() == character.getInv() || user.getInventory() == character.getClothes()) {
				user.setInventory(null);
			}
		}

		this.charas.remove(character);

	}

	@Deprecated
	// Find a door from a room
	public Door findSpecificRoomDoor(Room room, String otherRoomName, int num, boolean locked, boolean includeHidden) throws InvalidInputException {
		List<Door> matchingDoors = new ArrayList<>();
		for (int i = 0; i < room.getSpecificDoors(locked, includeHidden).size(); i++) {
			Door door = room.getSpecificDoors(locked, includeHidden).get(i);
			if (door.getRoom1().getName().toLowerCase().equals(otherRoomName) || door.getRoom2().getName().toLowerCase().equals(otherRoomName)) {
				matchingDoors.add(door);
			}
		}
		if (num <= matchingDoors.size()) {
			return matchingDoors.get(num-1);
		} else {
			throw new InvalidInputException("Door not found. There are " + matchingDoors.size() + " matching doors.");
		}
	}


}

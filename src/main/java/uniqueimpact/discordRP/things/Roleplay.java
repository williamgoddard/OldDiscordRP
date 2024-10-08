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

	private Inventory storageInv;


	private Roleplay() {
		try {
			load();
			users = instance.users;
			rooms = instance.rooms;
			charas = instance.charas;
			storageInv = instance.storageInv;
		} catch (Exception e) {
			users = new ArrayList<>();
			rooms = new ArrayList<>();
			charas = new ArrayList<>();
			try {
				storageInv = new Inventory(0);
			} catch (InvalidInputException ex) {
				throw new RuntimeException(ex);
			}
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
			FileOutputStream backupFileOut = new FileOutputStream("savedata_backup_" + System.currentTimeMillis());
			ObjectOutputStream backupObjectOut = new ObjectOutputStream(backupFileOut);
            objectOut.writeObject(instance);
			backupObjectOut.writeObject(instance);
            objectOut.close();
			backupObjectOut.close();
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

	// Get the roleplay's storage inventory
	public Inventory getStorageInv() {
		return storageInv;
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

		int p1 = 0;
		int p2 = rooms.size();
		while (p1 < p2) {
			int midpoint = (p1 + p2) / 2;
			Room currentRoom = rooms.get(midpoint);
			if (room.compareTo(currentRoom) >= 0) {
				p1 = midpoint + 1;
			} else {
				p2 = midpoint;
			}
		}

		rooms.add(p1, room);

	}

	// Register a character to the roleplay
	public void addCharacter(Chara character) {

		int p1 = 0;
		int p2 = charas.size();
		while (p1 < p2) {
			int midpoint = (p1 + p2) / 2;
			Chara currentChara = charas.get(midpoint);
			if (character.compareTo(currentChara) > 0) {
				p1 = midpoint + 1;
			} else {
				p2 = midpoint;
			}
		}

		charas.add(p1, character);

	}

	// Delete a user from the roleplay
	public void delUser(User user) {
		this.users.remove(user);
	}

	// Delete a room from the roleplay
	public void delRoom(Room room) throws InvalidInputException {

		if (room.getCharacters().size() > 0) {
			throw new InvalidInputException("The room could not be deleted because it contains characters.");
		}

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

		character.getRoom().delCharacter(character);

		this.charas.remove(character);

	}

}

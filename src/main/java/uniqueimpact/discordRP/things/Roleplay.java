package uniqueimpact.discordRP.things;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import uniqueimpact.discordRP.utils.InputChecker;
import uniqueimpact.discordRP.utils.InvalidInputException;

public class Roleplay implements Serializable {

	private static final long serialVersionUID = 1L;

	private static Roleplay instance;

	private List<User> users;
	private List<Room> rooms;
	private List<Player> players;
	
	private Roleplay() {
		try {
			load();
			users = instance.users;
			rooms = instance.rooms;
			players = instance.players;
		} catch (Exception e) {
			users = new ArrayList<>();
			rooms = new ArrayList<>();
			players = new ArrayList<>();
		}
	}
	
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

	public List<User> getUsers() {
		return users;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public User findUser(String discordId) throws InvalidInputException {

		if (!InputChecker.validDiscordID(discordId)) {
			throw new InvalidInputException("Discord user id must be an 18 digit number.");
		}

		for (User user : users) {
			if (user.getDiscordId().equals(discordId)) {
				return user;
			}
		}

		throw new InvalidInputException("The user could not be found.");

	}
	
	public Room findRoom(String name, int num) throws InvalidInputException {

		if (!InputChecker.validName(name)) {
			throw new InvalidInputException("Room name must be 32 characters at most, and may use only letters, numbers, hyphens and underscores.");
		}

		if (num < 1) {
			throw new InvalidInputException("Room number must be at least 1.");
		}

		List<Room> matchingRooms = new ArrayList<>();

		for (Room room : rooms) {
			if (room.getName().equalsIgnoreCase(name)) {
				matchingRooms.add(room);
			}
		}

		if (num > matchingRooms.size()) {
			throw new InvalidInputException("Room not found. There are " + matchingRooms.size() + " matching rooms.");
		}

		return matchingRooms.get(num-1);

	}
	
	public Player findPlayer(String name) throws InvalidInputException {

		if (!InputChecker.validName(name)) {
			throw new InvalidInputException("Room name must be 32 characters at most, and may use only letters, numbers, hyphens and underscores.");
		}

		for (Player player : players) {
			if (player.getName().equalsIgnoreCase(name)) {
				return player;
			}
		}

		throw new InvalidInputException("The character could not be found.");

	}
	
	public Player findPlayerByChannel(String channel) throws InvalidInputException {

		if (!InputChecker.validDiscordID(channel)) {
			throw new InvalidInputException("Discord channel ID must be an 18 digit number.");
		}

		for (Player player : players) {
			if (player.getChannel().equals(channel)) {
				return player;
			}
		}

		throw new InvalidInputException("This Discord channel is not linked to a character.");

	}
	
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

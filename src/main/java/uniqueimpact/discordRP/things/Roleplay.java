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
	
	private List<Room> rooms;
	private List<Player> players;
	
	private Roleplay() {
		try {
			load();
			rooms = instance.rooms;
			players = instance.players;
		} catch (Exception e) {
			rooms = new ArrayList<Room>();
			players = new ArrayList<Player>();
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

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	
	public Room findRoom(String name, int num) throws InvalidInputException {

		if (!InputChecker.validName(name)) {
			throw new InvalidInputException("Name must be 40 characters at most, and may use only letters, numbers, hyphens and underscores.");
		}

		if (num < 1) {
			throw new InvalidInputException("Room number must be at least 1.");
		}

		List<Room> matchingRooms = new ArrayList<>();

		for (int i = 0; i < rooms.size(); i++) {
			Room room = rooms.get(i);
			if (room.getName().toLowerCase().equals(name)) {
				matchingRooms.add(room);
			}
		}

		if (num > matchingRooms.size()) {
			throw new InvalidInputException("Room not found. There are " + matchingRooms.size() + " matching rooms.");
		}

		return matchingRooms.get(num-1);

	}

	public Room findRoom(String name) throws InvalidInputException {
		return findRoom(name, 1);
	}
	
	public Player findPlayer(String inputString) throws InvalidInputException {
		String[] inputSplit = inputString.toLowerCase().split("#");
		if (inputSplit.length >= 1 && inputSplit.length <= 2) {
			List<Player> matchingPlayers = new ArrayList<Player>();
			if (InputChecker.validName(inputSplit[0])) {
				for (int i = 0; i < players.size(); i++) {
					Player player = players.get(i);
					if (player.getName().toLowerCase().equals(inputSplit[0])) {
						matchingPlayers.add(player);
					}
				}
			} else {
				throw new InvalidInputException("Name must be 40 characters at most, and may use only letters, numbers, hyphens and underscores.");
			}
			int num = 1;
			if (inputSplit.length == 2) {
				try {
					if (Integer.parseInt(inputSplit[1]) >= 1) {
						num = Integer.parseInt(inputSplit[1]);
					} else {
						throw new InvalidInputException("Player number must be at least 1.");
					}
				} catch (NumberFormatException e) {
					throw new InvalidInputException("Player number must be an integer.");
				}
			}
			if (num <= matchingPlayers.size()) {
				return matchingPlayers.get(num-1);
			} else {
				throw new InvalidInputException("Player not found. There are " + matchingPlayers.size() + " matching players.");
			}
		} else {
			throw new InvalidInputException("Player must be formatted as name[#num].");
		}
	}
	
	public Player findPlayerByChannel(String channel) throws InvalidInputException {
		if (InputChecker.validDiscordID(channel)) {
			for (int i = 0; i < players.size(); i++) {
				Player player = players.get(i);
				if (player.getChannel().equals(channel)) {
					return player;
				}
			}
			throw new InvalidInputException("This Discord channel is not linked to a player.");
		} else {
			throw new InvalidInputException("Discord channel ID must be an 18 digit number.");
		}
	}
	
	public Player findRoomPlayer(Room room, String inputString) throws InvalidInputException {
		return findRoomPlayer(room, inputString, true);
	}
	
	public Player findRoomPlayer(Room room, String inputString, Boolean includeHidden) throws InvalidInputException {
		String[] inputSplit = inputString.toLowerCase().split("#");
		List<Player> players = room.getPlayers(includeHidden);
		if (inputSplit.length >= 1 && inputSplit.length <= 2) {
			List<Player> matchingPlayers = new ArrayList<Player>();
			if (InputChecker.validName(inputSplit[0])) {
				for (int i = 0; i < players.size(); i++) {
					Player player = players.get(i);
					if (player.getName().toLowerCase().equals(inputSplit[0])) {
						matchingPlayers.add(player);
					}
				}
			} else {
				throw new InvalidInputException("Name must be 40 characters at most, and may use only letters, numbers, hyphens and underscores.");
			}
			int num = 1;
			if (inputSplit.length == 2) {
				try {
					if (Integer.parseInt(inputSplit[1]) >= 1) {
						num = Integer.parseInt(inputSplit[1]);
					} else {
						throw new InvalidInputException("Player number must be at least 1.");
					}
				} catch (NumberFormatException e) {
					throw new InvalidInputException("Player number must be an integer.");
				}
			}
			if (num <= matchingPlayers.size()) {
				return matchingPlayers.get(num-1);
			} else {
				throw new InvalidInputException("Player not found. There are " + matchingPlayers.size() + " matching players.");
			}
		} else {
			throw new InvalidInputException("Player must be formatted as name[#num].");
		}
	}
	
	public Item findItem(String inputString) throws InvalidInputException {
		String[] inputSplit = inputString.toLowerCase().split("/");
		if (inputSplit.length == 3) {
			Inventory inventory = findInventory(inputSplit[0] + "/" + inputSplit[1]);
			return findInvItem(inventory, inputSplit[2]);
		} else {
			throw new InvalidInputException("Inventory must be formatted as inventory/item. (Inventory is formatted as type/location).");
		}
	}
	
	public Item findInvItem(Inventory inventory, String inputString) throws InvalidInputException {
		String[] itemSplit = inputString.toLowerCase().split("#");
		if (itemSplit.length >= 1 && itemSplit.length <= 2) {
			List<Item> matchingItems = new ArrayList<Item>();
			if (InputChecker.validName(itemSplit[0])) {
				for (int i = 0; i < inventory.getItems().size(); i++) {
					Item item = inventory.getItems().get(i);
					if (item.getName().toLowerCase().equals(itemSplit[0])) {
						matchingItems.add(item);
					}
				}
			} else {
				throw new InvalidInputException("Name must be 40 characters at most, and may use only letters, numbers, hyphens and underscores.");
			}
			int num = 1;
			if (itemSplit.length == 2) {
				try {
					if (Integer.parseInt(itemSplit[1]) >= 1) {
						num = Integer.parseInt(itemSplit[1]);
					} else {
						throw new InvalidInputException("Item number must be at least 1.");
					}
				} catch (NumberFormatException e) {
					throw new InvalidInputException("Item number must be an integer.");
				}
			}
			if (num <= matchingItems.size()) {
				return matchingItems.get(num-1);
			} else {
				throw new InvalidInputException("Item not found. There are " + matchingItems.size() + " matching items.");
			}
		} else {
			throw new InvalidInputException("Item must be formatted as name[#num].");
		}
	}
	
	public Inventory findInventory(String inputString) throws InvalidInputException {
		String[] inputSplit = inputString.toLowerCase().split("/");
		if (inputSplit.length == 2) {
			switch (inputSplit[0]) {
			case "items":
				return findPlayer(inputSplit[1]).getInv();
			case "clothes":
				return findPlayer(inputSplit[1]).getClothes();
			case "room":
				return findRoom(inputSplit[1]).getInv();
			default: 
				throw new InvalidInputException("Inventory type must be items, clothes or room.");
			}
		} else {
			throw new InvalidInputException("Inventory must be formatted as type/location.");
		}
	}
	
	public Inventory findInventoryByItemString(String inputString) throws InvalidInputException {
		String[] inputSplit = inputString.toLowerCase().split("/");
		if (inputSplit.length == 3) {
			return findInventory(inputSplit[0] + "/" + inputSplit[1]);
		} else {
			throw new InvalidInputException("Inventory must be formatted as inventory/item. (Inventory is formatted as type/location).");
		}
	}
	
	public Door findDoor(String inputString) throws InvalidInputException {
		String[] inputSplit = inputString.toLowerCase().split("/");
		if (inputSplit.length == 2) {
			Room room = findRoom(inputSplit[0]);
			return findRoomDoor(room, inputSplit[1]);
		} else {
			throw new InvalidInputException("Door must be formatted as room1/room2.");
		}
	}
	
	public Door findRoomDoor(Room room, String inputString) throws InvalidInputException {
		String doorSplit[] = inputString.toLowerCase().split("#");
		String otherRoomName = doorSplit[0];
		List<Door> matchingDoors = new ArrayList<Door>();
		for (int i = 0; i < room.getDoors().size(); i++) {
			Door door = room.getDoors().get(i);
			if (door.getRoom1().getName().toLowerCase().equals(otherRoomName) || door.getRoom2().getName().toLowerCase().equals(otherRoomName)) {
				matchingDoors.add(door);
			}
		}
		int num = 1;
		if (doorSplit.length == 2) {
			try {
				if (Integer.parseInt(doorSplit[2]) >= 1) {
					num = Integer.parseInt(doorSplit[2]);
				} else {
					throw new InvalidInputException("Door number must be at least 1.");
				}
			} catch (NumberFormatException e) {
				throw new InvalidInputException("Door number must be an integer.");
			}
		}
		if (num <= matchingDoors.size()) {
			return matchingDoors.get(num-1);
		} else {
			throw new InvalidInputException("Door not found. There are " + matchingDoors.size() + " matching doors.");
		}
	}
	
	public Door findSpecificDoor(String inputString, boolean locked, boolean includeHidden) throws InvalidInputException {
		String[] inputSplit = inputString.toLowerCase().split("/");
		if (inputSplit.length == 2) {
			Room room = findRoom(inputSplit[0]);
			return findSpecificRoomDoor(room, inputSplit[1], locked, includeHidden);
		} else {
			throw new InvalidInputException("Door must be formatted as room1/room2.");
		}
	}
	
	public Door findSpecificRoomDoor(Room room, String inputString, boolean locked, boolean includeHidden) throws InvalidInputException {
		String doorSplit[] = inputString.toLowerCase().split("#");
		String otherRoomName = doorSplit[0];
		List<Door> matchingDoors = new ArrayList<Door>();
		for (int i = 0; i < room.getSpecificDoors(locked, includeHidden).size(); i++) {
			Door door = room.getSpecificDoors(locked, includeHidden).get(i);
			if (door.getRoom1().getName().toLowerCase().equals(otherRoomName) || door.getRoom2().getName().toLowerCase().equals(otherRoomName)) {
				matchingDoors.add(door);
			}
		}
		int num = 1;
		if (doorSplit.length == 2) {
			try {
				if (Integer.parseInt(doorSplit[2]) >= 1) {
					num = Integer.parseInt(doorSplit[2]);
				} else {
					throw new InvalidInputException("Door number must be at least 1.");
				}
			} catch (NumberFormatException e) {
				throw new InvalidInputException("Door number must be an integer.");
			}
		}
		if (num <= matchingDoors.size()) {
			return matchingDoors.get(num-1);
		} else {
			throw new InvalidInputException("Door not found. There are " + matchingDoors.size() + " matching doors.");
		}
	}

}

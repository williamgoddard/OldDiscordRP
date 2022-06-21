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
			throw new InvalidInputException("Name must be 32 characters at most, and may use only letters, numbers, hyphens and underscores.");
		}
		if (!InputChecker.validDescription(description)) {
			throw new InvalidInputException("Description must be at most 1500 characters.");
		}
		this.name = name;
		this.description = description;
		this.inv = inv;
		this.players = new ArrayList<Player>();
		this.doors = new ArrayList<Door>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws InvalidInputException {
		if (!InputChecker.validName(name)) {
			throw new InvalidInputException("Name must be 32 characters at most, and may use only letters, numbers, hyphens and underscores.");
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
		List<Player> resultPlayers = new ArrayList<Player>();
		for (int i = 0; i < players.size(); i++) {
			if (includeHidden || !players.get(i).isHidden()) {
				resultPlayers.add(players.get(i));
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
		List<Door> resultDoors = new ArrayList<Door>();
		for (int i = 0; i < doors.size(); i++) {
			if (includeHidden || !doors.get(i).isHidden()) {
				resultDoors.add(doors.get(i));
			}
		}
		return resultDoors;
	}
	
	public List<Door> getSpecificDoors(boolean locked) {
		List<Door> resultDoors = new ArrayList<Door>();
		for (int i = 0; i < doors.size(); i++) {
			if (locked == doors.get(i).isLocked()) {
				resultDoors.add(doors.get(i));
			}
		}
		return resultDoors;
	}
	
	public List<Door> getSpecificDoors(boolean locked, boolean includeHidden) {
		List<Door> resultDoors = new ArrayList<Door>();
		for (int i = 0; i < doors.size(); i++) {
			if ((includeHidden || !doors.get(i).isHidden()) && (locked == doors.get(i).isLocked())) {
				resultDoors.add(doors.get(i));
			}
		}
		return resultDoors;
	}

	public void setDoors(List<Door> doors) {
		this.doors = doors;
	}

}

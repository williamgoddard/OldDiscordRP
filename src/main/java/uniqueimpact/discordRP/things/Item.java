package uniqueimpact.discordRP.things;

import java.io.Serializable;

import uniqueimpact.discordRP.utils.InputChecker;
import uniqueimpact.discordRP.utils.InvalidInputException;

public class Item implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String description;
	private double weight;
	private boolean takeable;
	private boolean wearable;
	private boolean infinite;
	private String key;
	
	public Item(String name, String description, double weight, boolean takeable, boolean wearable, boolean infinite, String key) throws InvalidInputException {
		if (!InputChecker.validName(name)) {
			throw new InvalidInputException("Name must be 32 characters at most, and may use only letters, numbers, hyphens and underscores.");
		}
		if (!InputChecker.validDescription(description)) {
			throw new InvalidInputException("Description must be 1500 characters at most, and may not use backslashes or quotation marks.");
		}
		if (!InputChecker.validKey(key)) {
			throw new InvalidInputException("Key must be 32 characters at most, and may use only letters, numbers, hyphens and underscores.");
		}
		this.name = name;
		this.description = description;
		this.weight = weight;
		this.takeable = takeable;
		this.wearable = wearable;
		this.infinite = infinite;
		this.key = key;
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

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public boolean isTakeable() {
		return takeable;
	}

	public void setTakeable(boolean takeable) {
		this.takeable = takeable;
	}

	public boolean isWearable() {
		return wearable;
	}

	public void setWearable(boolean wearable) {
		this.wearable = wearable;
	}

	public boolean isInfinite() {
		return infinite;
	}

	public void setInfinite(boolean infinite) {
		this.infinite = infinite;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) throws InvalidInputException {
		if (!InputChecker.validKey(key)) {
			throw new InvalidInputException("Key must be 32 characters at most, and may use only letters, numbers, hyphens and underscores.");
		}
		this.key = key;
	}
	
	public Item getCopy() throws InvalidInputException {
		return new Item(name, description, weight, takeable, wearable, infinite, key);
	}

}

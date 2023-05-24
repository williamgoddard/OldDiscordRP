package uniqueimpact.discordRP.things;

import java.io.Serializable;

import uniqueimpact.discordRP.utils.InvalidInputException;

public class Item implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String description;
	private double weight;
	private int quantity;
	private boolean takeable;
	private boolean wearable;
	private boolean infinite;
	private String keyword;
	
	public Item(String name, String description, double weight, int quantity, boolean takeable, boolean wearable, boolean infinite, String keyword) throws InvalidInputException {

		if (name.length() < 1 || name.length() > 32) {
			throw new InvalidInputException("Item name must be between 1 and 32 characters.");
		}

		if (description.length() < 1 || description.length() > 1500) {
			throw new InvalidInputException("Description name must be between 1 and 1500 characters.");
		}

		if ((keyword != null) && (keyword.length() < 1 || keyword.length() > 32)) {
			throw new InvalidInputException("Keyword must be between 1 and 32 characters.");
		}

		if (weight < 0 || weight > 1000000) {
			throw new InvalidInputException("Item weight must be between 0 and 1000000.");
		}

		this.name = name;
		this.description = description;
		this.weight = weight;
		this.quantity = quantity;
		this.quantity = quantity;
		this.takeable = takeable;
		this.wearable = wearable;
		this.infinite = infinite;
		this.keyword = keyword == null || keyword.equalsIgnoreCase("none") ? null : keyword;

	}

	// Get the item's name
	public String getName() {
		return name;
	}

	// Set the item's name
	public void setName(String name) throws InvalidInputException {

		if (name == null) {
			return;
		}

		if (name.length() < 1 || name.length() > 32) {
			throw new InvalidInputException("Item name must be between 1 and 32 characters.");
		}

		this.name = name;

	}

	// Get the item's description
	public String getDescription() {
		return description;
	}

	// Set the item's description
	public void setDescription(String description) throws InvalidInputException {

		if (description == null) {
			return;
		}

		if (description.length() < 1 || description.length() > 1500) {
			throw new InvalidInputException("Description name must be between 1 and 1500 characters.");
		}

		this.description = description;

	}

	// Get the item's weight
	public double getWeight() {
		return weight;
	}

	// Set the item's weight
	public void setWeight(Double weight) throws InvalidInputException {

		if (weight == null) {
			return;
		}

		if (weight < 0 || weight > 1000000) {
			throw new InvalidInputException("Item weight must be between 0 and 1000000.");
		}

		this.weight = weight;

	}

	// Get the item's quantity
	public int getQuantity() {
		return quantity;
	}

	// Set the item's quantity
	public void setQuantity(Integer quantity) throws InvalidInputException {

		if (quantity == null) {
			return;
		}

		if (quantity < 0 || quantity > 1000) {
			throw new InvalidInputException("Item quantity must be between 0 and 1000.");
		}

		this.quantity = quantity;

	}

 	// Get whether the item is takeable
	public boolean isTakeable() {
		return takeable;
	}

	// Set whether the item is takeable
	public void setTakeable(Boolean takeable) {
		if (takeable == null) {
			return;
		}

		this.takeable = takeable;
	}

	// Get whether the item is wearable
	public boolean isWearable() {
		return wearable;
	}

	// Set whether the item is wearable
	public void setWearable(Boolean wearable) {
		if (wearable == null) {
			return;
		}

		this.wearable = wearable;
	}

	// Get whether the item is infinite
	public boolean isInfinite() {
		return infinite;
	}

	// Set whether the item is infinite
	public void setInfinite(Boolean infinite) {
		if (infinite == null) {
			return;
		}

		this.infinite = infinite;
	}

	// Get the item's keyword, or null if none is assigned
	public String getKeyword() {
		return keyword;
	}

	// Set the item's keyword. Setting to "none" will clear it
	public void setKeyword(String keyword) throws InvalidInputException {

		if (keyword == null) {
			return;
		}

		if (keyword.length() < 1 || keyword.length() > 32) {
			throw new InvalidInputException("Keyword must be between 1 and 32 characters.");
		}

		this.keyword = keyword.equalsIgnoreCase("none") ? null : keyword;

	}

	// Get a copy of the item
	public Item getCopy() throws InvalidInputException {
		return new Item(name, description, weight, quantity, takeable, wearable, infinite, keyword);
	}

	// Get a finite copy of the item
	public Item getSingleCopy() throws InvalidInputException {
		return new Item(name, description, weight, 1, takeable, wearable, false, keyword);
	}

}

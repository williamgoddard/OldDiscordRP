package uniqueimpact.discordRP.things;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import uniqueimpact.discordRP.utils.InputChecker;
import uniqueimpact.discordRP.utils.InvalidInputException;

public class Inventory implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private double capacity;
	private List<Item> items;
	
	public Inventory(double capacity) throws InvalidInputException {

		if (capacity < 0) {
			throw new InvalidInputException("Inventory capacity must be at least 0.");
		}

		if (capacity > 1000000) {
			throw new InvalidInputException("Inventory capacity must be at most 1000000.");
		}

		this.capacity = capacity;
		this.items = new ArrayList<Item>();

	}

	// Get the inventory's capacity
	public double getCapacity() {
		return capacity;
	}

	// Set the inventory's capacity
	public void setCapacity(Double capacity) throws InvalidInputException {

		if (capacity == null) {
			return;
		}

		if (capacity < 0) {
			throw new InvalidInputException("Inventory capacity must be at least 0.");
		}

		if (capacity > 1000000) {
			throw new InvalidInputException("Inventory capacity must be at most 1000000.");
		}

		this.capacity = capacity;
	}

	public List<Item> getItems() {
		return items;
	}

	// Find an item from its name and number, filtering by takeable, wearable, and infinite
	public Item findItem(String name, int num, Boolean takeble, Boolean wearable, Boolean infinite) throws InvalidInputException {

		if (name == null) {
			throw new InvalidInputException("Item name must be assigned.");
		}

		if (name.length() < 1 || name.length() > 32) {
			throw new InvalidInputException("Item name must be between 1 and 32 characters.");
		}

		if (num < 1) {
			throw new InvalidInputException("Item number must be at least 1.");
		}

		List<Item> matchingItems = new ArrayList<>();

		for (Item item : items) {
			if (item.getName().equalsIgnoreCase(name)) {
				if ((takeble == null || item.isTakeable() == takeble) && (wearable == null || item.isWearable() == wearable) && (infinite == null || item.isInfinite() == infinite)) {
					matchingItems.add(item);
				}
			}
		}

		if (num > matchingItems.size()) {
			throw new InvalidInputException("Item not found. There are " + matchingItems.size() + " matching items.");
		}

		return matchingItems.get(num-1);

	}

	// Find an item in the inventory from its name and number
	public Item findItem(String name, int num) throws InvalidInputException {

		return findItem(name, num, null, null, null);

	}

	// Find the first matching item in an inventory from its name, filtering by takeable, wearable and infinite
	public Item findItem(String name, Boolean takeble, Boolean wearable, Boolean infinite) throws InvalidInputException {
		return findItem(name, 1, takeble, wearable, infinite);
	}

	// Find the first matching item in an inventory from its name
	public Item findItem(String name) throws InvalidInputException {
		return findItem(name, 1, null, null, null);
	}

	// Get the remaining capacity of the inventory
	public double getRemainingCapacity() {

		double remaining_capacity = capacity;
		for (Item item : items) {
			remaining_capacity -= item.getWeight();
		}

		return remaining_capacity;

	}

	// Get whether this inventory has enough capacity for an item
	public boolean canFitItem(Item item) {
		return (item.getWeight()) <= this.getRemainingCapacity();
	}

}

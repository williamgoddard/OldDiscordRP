package uniqueimpact.discordRP.things;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

	// Get a list of the items in the room
	public List<Item> getItems() {
		return items;
	}

	// Get a list of the items in the room, filtering by name, takeable, wearable and infinite
	public List<Item> getItems(String name, Boolean takeable, Boolean wearable, Boolean infinite) {
		ArrayList<Item> returnItems = new ArrayList<>();

		for (Item item : items) {
			if ((name == null || item.getName().equalsIgnoreCase(name)) && (takeable == null || item.isTakeable() == takeable) && (wearable == null || item.isWearable() == wearable) && (infinite == null || item.isInfinite() == infinite)) {
				returnItems.add(item);
			}
		}

		return returnItems;
	}

	// Get a list of the items in the room, filtering by name
	public List<Item> getItems(String name) {
		ArrayList<Item> returnItems = new ArrayList<>();

		for (Item item : items) {
			if (name == null || item.getName().equalsIgnoreCase(name)) {
				returnItems.add(item);
			}
		}

		return returnItems;
	}

	// Get a list of the items in the room, filtering by takeable, wearable and infinite
	public List<Item> getItems(Boolean takeable, Boolean wearable, Boolean infinite) {
		ArrayList<Item> returnItems = new ArrayList<>();

		for (Item item : items) {
			if ((takeable == null || item.isTakeable() == takeable) && (wearable == null || item.isWearable() == wearable) && (infinite == null || item.isInfinite() == infinite)) {
				returnItems.add(item);
			}
		}

		return returnItems;
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

	// Add an item to the inventory
	public void addItem(Item item) {

		if (item == null) {
			return;
		}

		int p1 = 0;
		int p2 = items.size();
		while (p1 < p2) {
			int midpoint = (p1 + p2) / 2;
			Item currentItem = items.get(midpoint);
			if (item.compareTo(currentItem) > 0) {
				p1 = midpoint + 1;
			} else {
				p2 = midpoint;
			}
		}

		items.add(p1, item);

	}

	// Remove an item from the inventory
	public void delItem(Item item) {

		if (item == null) {
			return;
		}

		items.remove(item);

	}

	// Get the remaining capacity of the inventory
	public double getRemainingCapacity() {

		double remaining_capacity = capacity;
		for (Item item : items) {
			remaining_capacity -= (item.getWeight() * (item.isInfinite() ? 1 : item.getQuantity()));
		}

		return remaining_capacity;

	}

}

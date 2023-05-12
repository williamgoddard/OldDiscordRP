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
	
	public Inventory(Double capacity) throws InvalidInputException {

		if (capacity == null) {
			capacity = 0.0;
		}

		if (capacity < 0) {
			throw new InvalidInputException("Inventory capacity must be at least 0.");
		}

		this.capacity = capacity;
		this.items = new ArrayList<Item>();

	}
	
	public double getCapacity() {
		return capacity;
	}
	
	public void setCapacity(double capacity) throws InvalidInputException {
		if (capacity < 0) {
			throw new InvalidInputException("Inventory capacity must be at least 0.");
		}
		this.capacity = capacity;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	public double getRemainingCapacity() {
		double remainingCapacity = capacity;
		for (int i = 0; i < items.size(); i++) {
			remainingCapacity -= items.get(i).getWeight();
		}
		return remainingCapacity;
	}

	public Item findItem(String name, int num) throws InvalidInputException {

		if (!InputChecker.validName(name)) {
			throw new InvalidInputException("Name must be 32 characters at most, and may use only letters, numbers, hyphens and underscores.");
		}

		if (num < 1) {
			throw new InvalidInputException("Item number must be at least 1.");
		}

		List<Item> matchingItems = new ArrayList<>();

		for (Item item : items) {
			if (item.getName().equalsIgnoreCase(name)) {
				matchingItems.add(item);
			}
		}

		if (num > matchingItems.size()) {
			throw new InvalidInputException("Item not found. There are " + matchingItems.size() + " matching items.");
		}

		return matchingItems.get(num-1);

	}

	public Item findItem(String name) throws InvalidInputException {
		return findItem(name, 1);
	}

}

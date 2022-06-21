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

}

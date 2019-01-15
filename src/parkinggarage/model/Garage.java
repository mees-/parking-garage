package parkinggarage.model;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Garage {
	private List<Spot> spots = new ArrayList<>();
	private int totalSpots;
	
	Garage(int floors, int rows, int places, int subscriberPlaces) {
		if (subscriberPlaces > floors * rows * places) {
			throw new IllegalArgumentException("Cannot have more subscribers than there are spots");
		}
		totalSpots = floors * rows * places;
		
		// generate all the spots
		for (int z = 0; z < floors; z++) {
			for (int x = 0; x < rows; x++) {
				for (int y = 0; y < places; y++) {
					spots.add(new Spot(z, x, y, CarType.SUBSCRIBER));
				}
			}
		}
	}
	
	public List<Spot> getSpots() {
		return spots;
	}
	
	public List<Spot> getFilteredSpots(Predicate<Spot> predicate) {
		// convert list to stream - filter stream - convert stream back to list
		return spots.stream().filter(predicate).collect(Collectors.toList());
	}
	
	public int getFreeSpots() {
		int freeSpots = 0;
		for (Spot spot : spots) {
			if (spot.isEmpty()) {
				freeSpots++;
			}
		}
		return freeSpots;
	}
	
	public int getOccupiedSpots() {
		return totalSpots - getFreeSpots();
	}
	
	/**
	 * @param subscriber whether the spot should be a subscriber only spot
	 * @return a free spot or if none are available null
	 */
	public Spot getFreeSpot(CarType type) {
		for (Spot spot : getFilteredSpots(s -> s.getType() == type)) {
			if (spot.isEmpty()) {
				return spot;
			}
		}
		return null;
	}

	/**
	 * @return a free non-subscriber spot or null if none are available
	 */
	public Spot getFreeSpot() {
		return getFreeSpot(CarType.UNPLANNED);
	}
	
	public void placeCar(Car car) {
		getFreeSpot(car.getType()).setCar(car);
	}
}

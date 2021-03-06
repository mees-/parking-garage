/**
 * @author Mees van Dijk
 */
package parkinggarage.model;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Predicate;

public class Garage {
	private Spot[] spots;
	private int totalSpots;
	private int subscriberPlaces;
	
	private Random random;
	
	Garage(int floors, int rows, int places, int subscriberPlaces, Random random) {
		if (subscriberPlaces > floors * rows * places) {
			throw new IllegalArgumentException("Cannot have more subscribers than there are spots");
		}
		totalSpots = floors * rows * places;
		this.subscriberPlaces = subscriberPlaces;
		
		spots = new Spot[floors * rows * places];
		// generate all the spots
		for (int z = 0; z < floors; z++) {
			for (int x = 0; x < rows; x++) {
				for (int y = 0; y < places; y++) {
					// Calculate the current car spot
					// Calculate the first spot of the floor; Add the first spot of the row; Add the spot in the row
					int i = (z * rows * places) + (x * places) + y;
					if(i < subscriberPlaces)
						
						spots[i] = new Spot(z, x, y, CarType.SUBSCRIBER);
					else
						spots[i] = new Spot(z, x, y, CarType.UNPLANNED);
				}
			}
		}
		
		this.random = random;
	}

	public Spot[] getSpots() {
		return spots;
	}
	
	public int getNumberOfFreeSpots() {
		int freeSpots = 0;
		for (Spot spot : spots) {
			if (spot.isEmpty()) {
				freeSpots++;
			}
		}
		return freeSpots;
	}

	public int getNumberOfFreeSpots(CarType type) {
		int freeSpots = 0;
		for (Spot spot : getFilteredSpots(Spot.isType.apply(type))) {
			if (spot.isEmpty()) {
				freeSpots++;
			}
		}
		return freeSpots;

	}
	
	public ArrayList<Spot> getFilteredSpots(Predicate<Spot> predicate) {
		ArrayList<Spot> result = new ArrayList<>();
		for (Spot spot : spots) {
			if (predicate.test(spot)) {
				result.add(spot);
			}
		}
		return result;
	}
	
	public int getNumberOfOccupiedSpots() {
		return totalSpots - getNumberOfFreeSpots();
	}
	
	/**
	 * @param type what type the spot should be
	 * @return a free spot or if none are available null
	 */
	public Spot getFreeSpot(CarType type) {
		ArrayList<Spot> s = getFilteredSpots(Spot.isFree.and(Spot.isType.apply(type)));
		if (s.size() > 0) {
			return s.get(random.nextInt(s.size()));
		} else {
			return null;
		}
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

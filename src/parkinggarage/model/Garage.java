package parkinggarage.model;

public class Garage {
	private Spot[] spots;
	private int totalSpots;
	private int subscriberPlaces;
	
	Garage(int floors, int rows, int places, int subscriberPlaces) {
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
	}
	
	public Spot[] getSpots() {
		return spots;
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
		if(type == CarType.SUBSCRIBER) {
			for (int i = 0; i < subscriberPlaces; i++) {
				if(spots[i].isEmpty())
					return spots[i];
			}
		}
		else {
			for (int i = subscriberPlaces; i < totalSpots; i++) {
				if(spots[i].isEmpty())
					return spots[i];
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

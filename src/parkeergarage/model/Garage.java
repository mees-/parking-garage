package parkeergarage.model;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Garage {
	private List<Spot> spots;
	
	Garage(int floors, int rows, int places) {
		spots = new ArrayList<Spot>();
		
		// generate all the spots
		for (int z = 0; z < floors; z++) {
			for (int x = 0; x < rows; x++) {
				for (int y = 0; y < places; y++) {
					spots.add(new Spot(z, x, y));
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
}

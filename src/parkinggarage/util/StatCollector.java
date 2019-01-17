package parkinggarage.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import parkinggarage.model.CarType;
import parkinggarage.model.Simulator;
import parkinggarage.model.Spot;

public class StatCollector {
	private static final int defaultHistoryLength = 1000;
	
	private int historyLength;
	private Map<CarType, List<Integer>> spotsFree = new HashMap<>();
	
	private List<List<Integer>> entrances = new ArrayList<>();
	private List<Integer> payment = new ArrayList<>();
	private List<Integer> exit = new ArrayList<>();
	
	private Simulator sim;
	
	public StatCollector(int historyLength, Simulator sim) {
		this.historyLength = historyLength;
		this.sim = sim;
		for (CarType type : CarType.values()) {
			spotsFree.put(type, new ArrayList<>());
		}
		for (int i = 0; i < 2; i++) {
			entrances.add(new ArrayList<>());
		}
	}
	
	public StatCollector(Simulator sim) {
		this(defaultHistoryLength, sim);
	}
	
	public void tick() {
		for (Map.Entry<CarType, List<Integer>> entry : spotsFree.entrySet()) {
			entry.getValue().add(
					sim
						.getGarage()
						.getFilterdSpots(
								Spot
									.isFree
									.and(
											Spot.isType.apply(entry.getKey())
									)
						)
						.size()
					);
		}
		// entrances
		entrances.get(0).add(sim.getSubscriberEntrance().size());
		entrances.get(1).add(sim.getUnplannedEntrance().size());
		
		payment.add(sim.getPayment().size());
		exit.add(sim.getExit().size());
		
		// check all lists to see if they exceed max size
		for (List<Integer> list : spotsFree.values()) {
			ensureHistoryLength(list);
		}
		
		for (List<Integer> list : entrances) {
			ensureHistoryLength(list);
		}
		
		ensureHistoryLength(payment);
		ensureHistoryLength(exit);
	}
		
	private void ensureHistoryLength(List<Integer> list) {
		while (list.size() > historyLength) {
			list.remove(0);
		}
	}
	
	private int getAverage(List<Integer> list) {
		int total = 0;
		for (Integer i : list) {
			total += i;
		}
		
		return total / list.size();
	}

	/**
	 * @return the historyLength
	 */
	public int getHistoryLength() {
		return historyLength;
	}

	/**
	 * @param historyLength the historyLength to set
	 */
	public void setHistoryLength(int historyLength) {
		this.historyLength = historyLength;
	}
	
	public List<Integer> getSpotsFree(CarType type) {
		return spotsFree.get(type);
	}
	
	public List<Integer> getSpotsOccupied(CarType type) {
		return spotsFree.get(type).parallelStream().map(spots -> sim.getSettings().getSpots(type) - spots).collect(Collectors.toList());
	}

	/**
	 * @return the entrances
	 */
	public List<List<Integer>> getEntrances() {
		return entrances;
	}

	/**
	 * @return the payment
	 */
	public List<Integer> getPayment() {
		return payment;
	}

	/**
	 * @return the exit
	 */
	public List<Integer> getExit() {
		return exit;
	}

	/**
	 * @return the sim
	 */
	public Simulator getSim() {
		return sim;
	}
	
	// statistical stuff
	public int getAvgSpotsFree(CarType type) {
		return getAverage(getSpotsFree(type));
	}
	
	public int getAvgSpotsFree() {
		int total = 0;
		for (Map.Entry<CarType, List<Integer>> entry : spotsFree.entrySet()) {
			total += getAvgSpotsFree(entry.getKey());
		}
		return total;
	}
	
	public int getAvgSpotsOccupied(CarType type) {
		return getAverage(getSpotsOccupied(type));
	}
	
	public int getAvgSpotsOccupied() {
		int total = 0;
		for (Map.Entry<CarType, List<Integer>> entry : spotsFree.entrySet()) {
			total += getAvgSpotsOccupied(entry.getKey());
		}
		return total;
	}
	
	public int getAvgEntrance(int index) {
		return getAverage(entrances.get(index));
	}
	
	public int getAvgEntrance() {
		int total = 0;
		for (List<Integer> entrance : entrances) {
			total += getAverage(entrance);
		}
		return total;
	}
	
	public int getAvgPayment() {
		return getAverage(payment);
	}
	
	public int getAvgExit() {
		return getAverage(exit);
	}
	
	public String toString() {
		return super.toString() + " " +
				"avg spots free: " + getAvgSpotsFree() + " " +
				"avg sub spots free: " + getAvgSpotsFree(CarType.SUBSCRIBER) + " " +
				"avg unpl spots free: " + getAvgSpotsFree(CarType.UNPLANNED) + " " +
				"avg entraces: " + getAvgEntrance() + " " +
				"avg payment: " + getAvgPayment() + " " +
				"avg exit: " + getAvgExit();
	}
}

package parkinggarage.util;

import java.util.*;
import java.util.stream.Collectors;

import parkinggarage.model.CarType;
import parkinggarage.model.Simulator;
import parkinggarage.model.Spot;

public class StatCollector implements Ticker {
	private static final int defaultHistoryLength = 1000;
	
	private static double getAverage(List<Integer> list) {
		if (list.size() == 0) {
			return 0;
		}
		double total = 0;
		for (Integer i : list) {
			total += i;
		}
		
		return (total / list.size());
	}
	
	public static List<Integer> getReducedList(List<Integer> list, int amount) {
		List<Integer> reducedAmounts = new ArrayList<>();
		ListIterator<Integer> iter = list.listIterator();
		while (iter.nextIndex() + amount - 1 < list.size()) {
			int periodTotal = 0;
			for (int i = 0; i < amount; i++) {
				periodTotal += iter.next();
			}
			reducedAmounts.add(periodTotal);
		}
		return reducedAmounts;
	}
	
	private int historyLength;
	private Map<CarType, List<Integer>> spotsFree = new HashMap<>();
	
	private List<List<Integer>> entrances = new ArrayList<>();
	private List<Integer> payment = new ArrayList<>();
	private List<Integer> exit = new ArrayList<>();
	
	public Map<CarType, List<Integer>> arrivingCars = new HashMap<>();
	public Map<CarType, List<Integer>> queueLeavingCars = new HashMap<>();
	
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
		for (CarType type : CarType.values()) {
			arrivingCars.put(type, new ArrayList<>());
			queueLeavingCars.put(type, new ArrayList<>());
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
						.getFilteredSpots(
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
		
		for (Map.Entry<CarType, List<Integer>> entry : arrivingCars.entrySet()) {
			entry.getValue().add(sim.getCarsArrivedLastTick(entry.getKey()));
		}
		
		for (Map.Entry<CarType, List<Integer>> entry : queueLeavingCars.entrySet()) {
			entry.getValue().add(sim.getCarsLeftQueueLastTick(entry.getKey()));
		}
		
		// check all lists to see if they exceed max size
		ensureHistoryLengthLists(spotsFree.values());
		ensureHistoryLengthLists(entrances);
		
		ensureHistoryLength(payment);
		ensureHistoryLength(exit);
		
		ensureHistoryLengthLists(arrivingCars.values());
	}
		
	private void ensureHistoryLength(List<Integer> list) {
		while (list.size() > historyLength) {
			list.remove(0);
		}
	}
	
	private void ensureHistoryLengthLists(Collection<List<Integer>> listOfLists) {
		for (List<Integer> list : listOfLists) {
			ensureHistoryLength(list);
		}
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
	public double getAvgSpotsFree(CarType type) {
		return getAverage(getSpotsFree(type));
	}
	
	public int getAvgSpotsFree() {
		int total = 0;
		for (Map.Entry<CarType, List<Integer>> entry : spotsFree.entrySet()) {
			total += getAvgSpotsFree(entry.getKey());
		}
		return total;
	}
	
	public double getAvgSpotsOccupied(CarType type) {
		return getAverage(getSpotsOccupied(type));
	}
	
	public int getAvgSpotsOccupied() {
		int total = 0;
		for (Map.Entry<CarType, List<Integer>> entry : spotsFree.entrySet()) {
			total += getAvgSpotsOccupied(entry.getKey());
		}
		return total;
	}
	
	public double getAvgEntrance(int index) {
		return getAverage(entrances.get(index));
	}
	
	public int getAvgEntrance() {
		int total = 0;
		for (List<Integer> entrance : entrances) {
			total += getAverage(entrance);
		}
		return total;
	}
	
	public double getAvgPayment() {
		return getAverage(payment);
	}
	
	public double getAvgExit() {
		return getAverage(exit);
	}
	
	public double getAvgArriving(CarType type) {
		return getAverage(arrivingCars.get(type));
	}
	
	public double getAvgArrivingPerTime(CarType type, int mins) {
		return getAverage(getReducedList(arrivingCars.get(type), mins));
	}
	
	public double getAvgLeavingQueue(CarType type) {
		return getAverage(queueLeavingCars.get(type));
	}
	
	public String toString() {
		return "Stats: " +
				"avg spots free: " + getAvgSpotsFree() + "\n" +
				"avg sub spots free: " + getAvgSpotsFree(CarType.SUBSCRIBER) + "\n" +
				"avg upl spots free: " + getAvgSpotsFree(CarType.UNPLANNED) + "\n" +
				"avg sub entrance: " + getAvgEntrance(0) + "\n" +
				"avg upl entrance: " + getAvgEntrance(1) + "\n" +
				"avg payment: " + getAvgPayment() + "\n" +
				"avg exit: " + getAvgExit() + "\n" +
				"avg sub arriving: " + getAvgArrivingPerTime(CarType.SUBSCRIBER, 60) + "\n" +
				"avg upl arriving: " + getAvgArrivingPerTime(CarType.UNPLANNED, 60) + "\n" +
				"avg sub leaving queue: " + getAvgLeavingQueue(CarType.SUBSCRIBER) + "\n" +
				"avg unp leaving queue: " + getAvgLeavingQueue(CarType.UNPLANNED);
	}
}

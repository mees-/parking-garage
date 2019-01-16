package parkinggarage.model;

import java.util.ArrayList;
import java.util.LinkedList;

public class CarQueue extends LinkedList<Car> {
	private static final long serialVersionUID = 7155566713851229669L;

	public ArrayList<Car> removeAmount(int amount) {
		ArrayList<Car> toRemove = new ArrayList<>();
		
		int i = 0;
		while (!super.isEmpty() && i < amount) {
			amount--;
			toRemove.add(super.poll());
		}
		return toRemove;
	}
}

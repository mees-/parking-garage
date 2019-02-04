/**
 * @author Mees van Dijk
 */
package parkinggarage.model;

import java.util.ArrayList;
import java.util.LinkedList;

public class CarQueue extends LinkedList<Car> {
	/**
	 * this is just to prevent some warning
	 * java is bad (imo)
	 */
	private static final long serialVersionUID = 1L;

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

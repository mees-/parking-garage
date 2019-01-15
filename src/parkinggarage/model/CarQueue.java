package parkinggarage.model;

import java.util.Iterator;
import java.util.LinkedList;

public class CarQueue {
	private LinkedList<Car> queue = new LinkedList<>();
	private boolean subscribersFirst;
	
	public CarQueue(boolean subscribersFirst) {
		this.subscribersFirst = subscribersFirst;
	}
	
	public void push(Car car) {
		if (subscribersFirst) {
			int idx = 0;
			Iterator<Car> iter = queue.iterator();
			while (iter.hasNext() && iter.next().getType() == CarType.SUBSCRIBER) {
				idx++;
			}
			queue.add(idx, car);
		} else {
			queue.add(car);
		}
	}
	
	public Car getFirst() {
		return queue.poll();
	}
	
	public Car peekFirst() {
		return queue.peek();
	}
}

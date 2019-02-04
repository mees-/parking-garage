/**
 * @author Mees van Dijk
 */
package parkinggarage.util;

public enum WeekDay {
	MONDAY(0),
	TUESDAY(1),
	WEDNESDAY(2),
	THURSDAY(3),
	FRIDAY(4),
	SATURDAY(5),
	SUNDAY(6);
	
	private int key;
	
	WeekDay(int key) {
		this.key = key;
	}
	
	/**
	 * @param key the number representation of the weekday (0-6)
	 * @return a WeekDay enum calculated using day % 7
	 */
	static WeekDay getWeekDay(int key) {
		switch (key % 7) {
			case 0: return MONDAY;
			case 1: return TUESDAY;
			case 2: return WEDNESDAY;
			case 3: return THURSDAY;
			case 4: return FRIDAY;
			case 5: return SATURDAY;
			case 6: return SUNDAY;
			default: {
				return null;
			}
		}
	}
	
	public String toString() {
	switch (key % 7) {
		case 0: return "Monday";
		case 1: return "Tuesday";
		case 2: return "Wednesday";
		case 3: return "Thursday";
		case 4: return "Friday";
		case 5: return "Saturday";
		case 6: return "Sunday";
		default: {
			return null;
		}
	}
	}
	
	int getValue() {
		return key;
	}
}
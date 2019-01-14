package parkeergarage.shared;
import parkeergarage.shared.WeekDay;

public class Time {
	private int day;
	private int hour;
	private int minute;
	private int second;
	
	Time(int day, int hour, int minute, int second) {
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}
	
	/**
	 * @return a WeekDay enum calculated using day % 7
	 */
	public WeekDay getWeekDay() {
		return WeekDay.getWeekDay(day);
	}


	/**
	 * @return the day
	 */
	public int getDay() {
		return day;
	}


	/**
	 * @return the hour
	 */
	public int getHour() {
		return hour;
	}


	/**
	 * @return the minute
	 */
	public int getMinute() {
		return minute;
	}


	/**
	 * @return the second
	 */
	public int getSecond() {
		return second;
	}
	
	/**
	 * @return a string representation of the time
	 */
	public String toString() {
		return "Day " + day + ", " + getWeekDay().toString() + " " + hour + ":" + minute + ":" + second;
	}
}

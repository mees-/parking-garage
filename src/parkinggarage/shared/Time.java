package parkinggarage.shared;
import parkinggarage.shared.WeekDay;

public class Time {
	private int day;
	private int hour;
	private int minute;
	
	public Time(int day, int hour, int minute) {
		this.day = day;
		this.hour = hour;
		this.minute = minute;
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
	 * @return returns the total amount of minutes passed for this time
	 */
	public int getTotalMinutes() {
		return day * 24 * 60 + hour * 60 + minute;
	}
	
	public boolean isWeekDay() {
		return (day % 7) < 5;
	}
	
	public boolean isWeekend() {
		return (day % 7) > 4;
	}

	public static Time fromMinutes(int min) {
		int day = 0;
		int hour = 0;
		int minute = min;
		while (minute >= 60) {
			hour += 1;
			minute -= 60;
		}
		while (hour >= 24) {
			day += 1;
			hour -= 24;
		}
		return new Time(day, hour, minute);
	}
	
	/**
	 * @return a string representation of the time
	 */
	public String toString() {
		return day + ":" + hour + ":" + minute;
	}
	
	/**
	 * @return a nicer string representation including the day of the week
	 */
	public String toDateString() {
		return "Day " + day + ", " + getWeekDay().toString() + " " + hour + ":" + minute;
	}
	
	// arithmetic and comparison
	public Time add(Time subject) {
		return Time.fromMinutes(this.getTotalMinutes() + subject.getTotalMinutes());
	}
	
	public Time addMinutes(int mins) {
		return Time.fromMinutes(this.getTotalMinutes() + mins);
	}
	
	public Time substract(Time subject) {
		return Time.fromMinutes(this.getTotalMinutes() - subject.getTotalMinutes());
	}
	
	public boolean greaterThan(Time subject) {
		return this.getTotalMinutes() > subject.getTotalMinutes();
	}
	
	public boolean smallerThan(Time subject) {
		return this.getTotalMinutes() < subject.getTotalMinutes();
	}
	
	public boolean equalTo(Time subject) {
		return this.getTotalMinutes() == subject.getTotalMinutes();
	}
}

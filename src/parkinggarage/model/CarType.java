/**
 * @author Mees van Dijk
 */
package parkinggarage.model;

public enum CarType {
	SUBSCRIBER,
	RESERVATION,
	UNPLANNED;
	
	public String toString() {
		switch (this) {
		case SUBSCRIBER:
			return "subscriber";
		case RESERVATION:
			return "reservation";
		case UNPLANNED:
			return "unplanned";
		default:
			return "";
		}
	}
}

package parkinggarage.model;

public enum CarType {
	SUBSCRIBER,
	// RESERVATION,
	UNPLANNED;
	
	public String toString() {
		switch (this) {
		case SUBSCRIBER:
			return "subscriber";
		case UNPLANNED:
			return "unplanned";
		default:
			return "";
		}
	}
}

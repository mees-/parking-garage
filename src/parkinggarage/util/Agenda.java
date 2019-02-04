/**
 * @author Mees van dijk
 */
package parkinggarage.util;

import java.util.HashMap;

/**
 * This is an agenda class for getting a modifier based on events.
 * The way it works is the modifier for the current time will be the modifier
 * specified in the last time that is smaller than or equal to the current time
 * see getModifier for clarification
 */
public class Agenda {
  private HashMap<Time, Double> changes = new HashMap<>();

  public double getModifier(Time time) {
    Time changeTime = getLastTimeBefore(time);
    if (changeTime != null) {
      return changes.get(changeTime);
    } else {
      return 1;
    }
  }

  public void putChange(Time time, Double modifier) {
    this.changes.put(time, modifier);
  }

  public Time getLastTimeBefore(Time limitTime) {
    Time latestTime = null;
    for (Time time : changes.keySet()) {
      if (time.smallerThanOrEquals(limitTime) &&
          (latestTime == null || time.greaterThan(latestTime))) {
        latestTime = time;
      }
    }
    return latestTime;
  }
}
package parkinggarage.model;

import parkinggarage.util.Time;
import parkinggarage.util.Settings;

class ReservationCar extends Car {
  private Time time;
  private Spot spot;
  private Time arrivalTime;

  private Settings settings;

  public ReservationCar(Time time, Settings settings) {
    super(CarType.RESERVATION, settings.getRandom());
    this.time = time;
    this.settings = settings;

    if (settings.getRandom().nextDouble() <= settings.getReservationShowChance()) {
      int minutesAfterStart = (int) Math.round(
        settings.getRandom().nextDouble()
          * (settings.getReservationTimeBefore()
              + settings.getReservationTimeAfter()
            )
        );
      arrivalTime = time.addMinutes(-1 * settings.getReservationTimeBefore())
                      .addMinutes(minutesAfterStart);
    }
  }

  public Time getTime() {
    return this.time;
  }

  public Spot getSpot() {
    return this.spot;
  }

  public void setSpot(Spot spot) {
    this.spot = spot;
  }

  public Time getStartTime() {
    return time.addMinutes(-1 * settings.getReservationTimeBefore());
  }
  public Time getEndTime() {
    return time.addMinutes(settings.getReservationTimeAfter());
  }

  /**
   * @return the arrivalTime
   */
  public Time getArrivalTime() {
    return arrivalTime;
  }
}
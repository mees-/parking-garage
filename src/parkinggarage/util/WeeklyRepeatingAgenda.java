/**
 * @author Mees van Dijk
 */
package parkinggarage.util;

/**
 * WeeklyRepeatingAgenda is a version of agenda that differs only in that all 
 * time arguments specified to it's methods are taken modulus new Time(7, 0, 0)
 * which results in all time arguments being the same weekday and time as the
 * original but all in the range 0:0:0 to 6:23:59
 */
public class WeeklyRepeatingAgenda extends Agenda {
   /**
   * getDefault returns a basic default agenda. it only fills in the first week
   * @return
   */
  public static Agenda getDefault() {
    Agenda agenda = new Agenda();
    // the weekdays
    for (int i = 0; i < 5; i++) {
      agenda.putChange(new Time(i, 0, 0), 0.3);
      agenda.putChange(new Time(i, 7, 0), 0.7);
      agenda.putChange(new Time(i, 11, 0), 1.0);
      agenda.putChange(new Time(i, 15, 0), 1.5);
      agenda.putChange(new Time(i, 19, 0), 1.0);
      agenda.putChange(new Time(i, 22, 0), 0.6);
    }

    // the weekends
    for (int i = 5; i < 7; i++) {
      agenda.putChange(new Time(i, 0, 0), 0.3);
      agenda.putChange(new Time(i, 9, 0), 1.0);
      agenda.putChange(new Time(i, 12, 0), 1.3);
      agenda.putChange(new Time(i, 16, 0), 1.5);
      agenda.putChange(new Time(i, 19, 0), 1.0);
      agenda.putChange(new Time(i, 23, 0), 0.7);
    }

    return agenda;
  }

  @Override
  public double getModifier(Time time) {
    return super.getModifier(time.modulus(new Time(7, 0, 0)));
  }

  @Override
  public void putChange(Time time, Double modifier) {
    super.putChange(time.modulus(new Time(7, 0, 0)), modifier);
  }

  @Override
  public Time getLastTimeBefore(Time limitTime) {
    return super.getLastTimeBefore(limitTime.modulus(new Time(7, 0, 0)));
  }
}
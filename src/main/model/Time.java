package model;

// Represents a time XX:XX
public class Time {
    public static final int HOURSINADAY = 24;
    public static final int MINUTESINANHOUR = 60;
    public static final int DAYSINAWEEK = 7;
    private int hour;
    private int minute;

    /*
     * REQUIRES: 0 <= hour < 24; 0 <= minute <= 60
     * EFFECTS: Time object XX:YY is created with values hour:minute
     */
    public Time(int hour, int minute) {
        if (hour < 0 || hour >= 24) {
            throw new IllegalTimeException("Invalid hour.");
        }
        if (minute < 0 || minute >= 60) {
            throw new IllegalTimeException("Invalid minute.");
        }
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    /*
     * allows for printing as a String
     */
    public String toString() {
        return ((this.hour < 10 ? "0" : "") + this.hour + (this.hour == 0 ? "0" : "")) + ":"
                + (((this.minute < 10 && this.minute > 0) ? "0" : "") + this.minute + (this.minute == 0 ? "0" : ""));
    }

    /*
     * REQUIRES: valid String
     * EFFECTS: converts a String in format XX:XX to corresponding time
     */
    public static Time fromString(String timeString) {
        int hour;
        int minute;
        String[] timeArray = timeString.split(":");
        try {
            hour = Integer.parseInt(timeArray[0]);
            minute = Integer.parseInt(timeArray[1]);
        } catch (NumberFormatException e) {
            return null;
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
        return new Time(hour, minute);
    }

    /*
     * REQUIRES: returns minute representation of hours and minutes
     */
    public int toMinutes() {
        return (this.hour * MINUTESINANHOUR) + this.minute;
    }

    /*
     * REQUIRES: other is an instance of Time
     * EFFECTS: determines if two times are the same
     */
    public boolean sameTime(Time other) {
        return (this.hour == other.getHour() && this.minute == other.getMinute());
    }

    /*
     * REQUIRES: other is a valid Time
     * EFFECTS: determines if this is earlier than other
     */
    public boolean earlier(Time other) {
        if (this.hour < other.getHour()) {
            return true;
        } else if (this.hour > other.getHour()) {
            return false;
        } else {
            return this.minute < other.getMinute();
        }
    }

    /*
     * REQUIRES: other is a valid Time
     * EFFECTS: determines if this is later than other
     */
    public boolean later(Time other) {
        return !sameTime(other) && !earlier(other);
    }
}

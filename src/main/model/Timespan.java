package model;

import org.json.JSONObject;

// Represents a timespan from XX:XX to XX:XX
public class Timespan {
    private Time start;
    private Time end;
    private int duration;

    /*
     * REQUIRES: 0 < duration < 24 hours (1440 minutes)
     * EFFECTS: timespan created from start to end
     */
    public Timespan(Time start, Time end) {
        if (start.sameTime(end)) {
            throw new IllegalTimespanException("Invalid timespan.");
        }
        this.start = start;
        this.end = end;
        this.duration = duration();
    }

    public Time getStart() {
        return start;
    }

    public Time getEnd() {
        return end;
    }

    public int getDuration() {
        return duration;
    }

    /*
     * REQUIRES: 0 < new duration < 1440
     * MODIFIES: this
     * EFFECTS: start set to new start; end set to new end; duration updated
     */
    public void edit(Time start, Time end) {
        if (start.sameTime(end)) {
            throw new IllegalTimespanException("Invalid timespan.");
        }
        this.start = start;
        this.end = end;
        this.duration = duration();
    }

    /*
     * REQUIRES: other is a valid Timespan
     * EFFECTS: determines whether this overlaps other;
     *          returns new Timespan of overlapping time
     */
    public Timespan overlaps(Timespan other) {
        Timespan overlap;
        if ((this.end.later(this.start)
                && !this.start.later(other.getStart()) && this.end.later(other.getStart()))
                || ((this.end.earlier(this.start) && (!this.start.later(other.getStart())
                || this.end.later(other.getStart()))))) {
            overlap = new Timespan(other.getStart(), this.end);
            if (other.getDuration() < overlap.getDuration()) {
                overlap = other;
            }
        } else if ((this.end.later(this.start)
                && this.start.earlier(other.getEnd()) && !this.end.earlier(other.getEnd()))
                || ((this.end.earlier(this.start) && (this.start.earlier(other.getEnd())
                || !this.end.earlier(other.getEnd()))))) {
            overlap = new Timespan(this.start, other.getEnd());
            if (other.getDuration() < overlap.getDuration()) {
                overlap = other;
            }
        } else {
            overlap = null;
        }
        return overlap;
    }

    /*
     * EFFECTS: allows for printing as a string
     */
    @Override
    public String toString() {
        return start.toString() + "-" + end.toString();
    }

    /*
     * EFFECTS: creates corresponding JSONObject
     */
    public JSONObject toJson() {
        JSONObject timespanJson = new JSONObject();
        timespanJson.put("start", this.start.toString());
        timespanJson.put("end", this.end.toString());

        return timespanJson;
    }

    /*
     * EFFECTS: returns duration from start to end in minutes
     */
    private int duration() {
        int hours = this.end.getHour() - this.start.getHour();
        int minutes = this.end.getMinute() - this.start.getMinute();
        if (minutes < 0) {
            hours -= 1;
            minutes += Time.MINUTESINANHOUR;
        }
        if (hours < 0) {
            hours += Time.HOURSINADAY;
        }
        return (hours * Time.MINUTESINANHOUR) + minutes;
    }
}

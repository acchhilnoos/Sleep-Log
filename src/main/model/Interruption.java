package model;

// Represents a single interruption from XX:XX to XX:XX
public class Interruption extends Timespan {

    /*
     * EFFECTS: interruption created from start to end
     */
    public Interruption(Time start, Time end) {
        super(start, end);
    }

    /*
     * EFFECTS: interruption created given timespan
     */
    public Interruption(Timespan timespan) {
        super(timespan.getStart(), timespan.getEnd());
    }
}

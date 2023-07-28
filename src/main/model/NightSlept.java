package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// Represents a single night slept from XX:XX to XX:XX with a list of interruptions
public class NightSlept extends Timespan {
    private List<Interruption> interruptions;

    /*
     * EFFECTS: night slept created from start to end with an empty list of Interruptions
     */
    public NightSlept(Time start, Time end) {
        super(start, end);
        this.interruptions = new ArrayList<>();
    }

    /*
     * EFFECTS: night slept created given timespan
     */
    public NightSlept(Timespan timespan) {
        super(timespan.getStart(), timespan.getEnd());
        this.interruptions = new ArrayList<>();
    }

    /*
     * EFFECTS: returns duration - duration of interruptions
     */
    @Override
    public int getDuration() {
        int inter = 0;
        for (Interruption i : interruptions) {
            inter += i.getDuration();
        }
        return super.getDuration() - inter;
    }

    /*
     * REQUIRES: interruptions not empty
     * EFFECTS: returns interruptions
     */
    public List<Interruption> getInterruptions() {
        return interruptions;
    }

    /*
     * EFFECTS: returns the number of interruptions
     */
    public int getNumInterruptions() {
        return interruptions.size();
    }

    /*
     * REQUIRES: interruption overlaps sleep; interruption does not overlap existing interruption
     * MODIFIES: this
     * EFFECTS: if interruption overlaps sleep:
     *          if interruption not between sleep start and end, shortens sleep and does not add an interruption
     *          else adds given interruption
     */
    public boolean addInterruption(Interruption interruption) {
        Timespan overlap = this.overlaps(interruption);
        if (overlap != null) {
            for (Interruption i : interruptions) {
                if (interruption.overlaps(i) != null) {
                    return false;
                }
            }
            if (overlap.getStart().sameTime(this.getStart())) {
                this.edit(overlap.getEnd(), this.getEnd());
            } else if (overlap.getEnd().sameTime(this.getEnd())) {
                this.edit(this.getStart(), overlap.getStart());
            } else {
                interruptions.add(interruption);
                EventLog.getInstance().logEvent(new Event("Interruption added to " + this.toString() + "."));
            }
            return true;
        }
        return false;
    }

    /*
     * REQUIRES: interruptions not empty; index < size
     * MODIFIES: this
     * EFFECTS: removes and returns last entry from interruptions
     */
    public Interruption removeLastInterruption() {
        if (this.interruptions.size() == 0) {
            return null;
        }
        EventLog.getInstance().logEvent(new Event("Interruption removed from " + this.toString() + "."));
        return this.interruptions.remove(interruptions.size() - 1);
    }

    /*
     * EFFECTS: allows for printing as a string
     */
    @Override
    public String toString() {
        return super.toString() + " (" + interruptions.size() + ")";
    }

    /*
     * EFFECTS: converts NightSlept to JSONObject
     */
    @Override
    public JSONObject toJson() {
        JSONObject nightJson = super.toJson();
        nightJson.put("interruptions", interruptionsToJson());
        return nightJson;
    }

    /*
     * MODIFIES: this
     * EFFECTS: removes all interruptions upon editing
     */
    @Override
    public void edit(Time start, Time end) {
        super.edit(start, end);
        interruptions.clear();
        EventLog.getInstance().logEvent(new Event("Night edited"));
    }

    /*
     * EFFECTS: adds interruptions to JSONObject
     */
    private JSONArray interruptionsToJson() {
        JSONArray interruptionJson = new JSONArray();

        for (Interruption i : interruptions) {
            interruptionJson.put(i.toJson());
        }

        return interruptionJson;
    }
}

package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// Represents a sleep log
public class SleepLog {
    private String username;
    private List<NightSlept> log;

    /*
     * MODIFIES: this
     * EFFECTS: creates a sleep log with given name and empty array of nights
     */
    public SleepLog(String name) {
        this.username = name;
        this.log = new ArrayList<>();
        EventLog.getInstance();
    }

    public List<NightSlept> getLog() {
        return log;
    }

    public String getUsername() {
        return username;
    }

    public int size() {
        return log.size();
    }

    /*
     * REQUIRES: valid index
     * EFFECTS: returns NightSlept at given index
     */
    public NightSlept get(int index) {
        return log.get(index);
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds a NightSlept to log
     */
    public boolean add(NightSlept night) {
        EventLog.getInstance().logEvent(new Event("Night added to sleep log."));
        return log.add(night);
    }

    /*
     * MODIFIES: this
     * EFFECTS: removes last NightSlept from log
     */
    public NightSlept remove(int index) {
        EventLog.getInstance().logEvent(new Event("Night removed from sleep log."));
        return log.remove(index);
    }

    /*
     * EFFECTS: returns index of specified NightSlept in log
     */
    public int indexOf(NightSlept night) {
        return log.indexOf(night);
    }

    /*
     * EFFECTS: creates a corresponding JSONObject
     */
    public JSONObject toJson() {
        JSONObject logJson = new JSONObject();
        logJson.put("name", username);
        logJson.put("nights", nightsToJson());

        return logJson;
    }

    /*
     * EFFECTS: adds nights as JSONObjects to corresponding log JSON object
     */
    private JSONArray nightsToJson() {
        JSONArray nightsJson = new JSONArray();

        for (NightSlept n : log) {
            nightsJson.put(n.toJson());
        }

        return nightsJson;
    }

}

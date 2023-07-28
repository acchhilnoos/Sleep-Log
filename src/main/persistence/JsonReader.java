package persistence;

import model.Interruption;
import model.NightSlept;
import model.SleepLog;
import model.Time;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;

// Reads from file
public class JsonReader {
    private String filename;

    /*
     * MODIFIES: this
     * EFFECTS: specifies filename
     */
    public JsonReader(String filename) {
        this.filename = filename;
    }

    /*
     * MODIFIES: SleepLog object
     * EFFECTS: creates SleepLog from file
     */
    public SleepLog read() {
        String fileString = fileToString(filename);
        if (fileString != null) {
            JSONObject fileJson = new JSONObject(fileString);

            return jsonToLog(fileJson);
        }
        return null;
    }

    /*
     * REQUIRES: valid filename
     * EFFECTS: converts file to string
     */
    public String fileToString(String filename) {
        try {
            return new String(Files.readAllBytes(Paths.get(filename)));
        } catch (Exception e) {
            return null;
        }
    }

    /*
     * MODIFIES: SleepLog object
     * EFFECTS: creates SleepLog with name from file
     */
    private SleepLog jsonToLog(JSONObject fileJson) {
        SleepLog log = new SleepLog(fileJson.getString("name"));
        jsonToNights(log, fileJson);
        return log;
    }

    /*
     * MODIIFES: SleepLog object
     * EFFECTS: adds nights to log from file
     */
    private void jsonToNights(SleepLog log, JSONObject fileJson) {
        JSONArray nightsJson = fileJson.getJSONArray("nights");
        for (Object nightObject : nightsJson) {
            JSONObject nightJson = (JSONObject) nightObject;
            NightSlept night = new NightSlept(Time.fromString(nightJson.getString("start")),
                    Time.fromString(nightJson.getString("end")));
            jsonToInterruptions(night, nightJson);
            log.add(night);
        }
    }

    /*
     * MODIFIES: current SleepLog object
     * EFFECTS: adds interruptions to night from file
     */
    private void jsonToInterruptions(NightSlept night, JSONObject nightJson) {
        JSONArray interArray = nightJson.getJSONArray("interruptions");
        for (Object interObject : interArray) {
            JSONObject interJson = (JSONObject) interObject;
            Interruption interruption = new Interruption(Time.fromString(interJson.getString("start")),
                    Time.fromString(interJson.getString("end")));
            night.addInterruption(interruption);
        }
    }
}

package persistence;

import model.SleepLog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Writes to file
public class JsonWriter {
    private static final int INDENT = 4;
    private PrintWriter writer;
    private String filename;

    /*
     * MODIFIES: this
     * EFFECTS: specifies filename
     */
    public JsonWriter(String filename) {
        this.filename = filename;
    }

    /*
     * REQUIRES: valid filename
     * MODIFIES: this
     * EFFECTS: creates writer object
     */
    public void open() throws FileNotFoundException {
        File f = new File(filename);
        if (f.exists()) {
            writer = new PrintWriter(f);
        } else {
            throw new FileNotFoundException();
        }
    }

    /*
     * MODIFIIES: log file
     * EFFECTS: writes SleepLog to file
     */
    public void write(SleepLog log) {
        writer.print(log.toJson().toString(INDENT));
    }

    /*
     * EFFECTS: closes file
     */
    public void close() {
        writer.close();
    }

}

package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.*;

public class SleepLogApp extends JPanel implements ActionListener {
    //region VARIABLES
    private static final String FILENAME = "./data/sleeplog.json";

    private static final int SMALL_BUTTON_WIDTH = 80;
    private static final int BUTTON_WIDTH = 150;
    private static final int BUTTON_HEIGHT = 24;
    private static final int ROW_HEIGHT = 60;
    private static final int SECTION_HEIGHT = 120;
    private static final Dimension SECTION_PANEL_SIZE = new Dimension(700, SECTION_HEIGHT);

    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel bottomPanel;

    private JPanel bottomPanel1;
    private JPanel bottomPanel2;
    private JPanel bottomPanel3;
    private JPanel bottomPanel4;

    private JTable guideTable;
    private JTable sleepTable;

    private JTextField limiterField;
    private JButton day1Button;
    private JButton day2Button;
    private JButton day3Button;
    private JButton day4Button;
    private JButton day5Button;
    private JButton day6Button;
    private JButton day7Button;
    private JTextField timespanStart;
    private JLabel dashLabel;
    private JTextField timespanEnd;
    private JButton set;

    private JLabel sleepyLabel;

    private JButton saveButton;
    private JButton loadButton;
    private JButton backwardButton;
    private JButton forwardButton;

    private JButton addNightButton;
    private JButton removeNightButton;
    private JComboBox<String> viewModeBox;

    private JButton viewNightButton;
    private JButton addInterruptionButton;
    private JButton removeInterruptionButton;
    private JButton editNightButton;

    private Timespan timespanInput;

    private JsonReader reader;
    private JsonWriter writer;

    private SleepLog log;
    private java.util.List<NightSlept> viewLog;

    private String name = "[USERNAME]";
    private int page = 0;
    private int setCmd;
    private int dayCmd;
    private int buttonPressed;
    private int viewmode = 1;
    private int limiter = 420;
    //endregion

    /*
     * EFFECTS: starts application
     */
    public SleepLogApp() {
        initComponents();
        initFunctions();
    }

    /*
     * MODIFIES: this
     * EFFECTS: initializes reader, writer, log
     */
    private void initFunctions() {
        reader = new JsonReader(FILENAME);
        writer = new JsonWriter(FILENAME);

        log = new SleepLog(name);
    }

    /*
     * MODIFIES: this
     * EFFECTS: initializes components on the frame
     */
    private void initComponents() {
        this.setLayout(new FlowLayout());
        this.setPreferredSize(new Dimension(700, 380));

        initSectionPanels();

        initTables();

        initMiddlePanel();

        initBottomPanel1();

        initBottomPanel2();

        initBottomPanel3();

        initBottomPanel4();
    }

    /*
     * MODIFIES: this
     * EFFECTS: initializes section panels on the frame
     */
    private void initSectionPanels() {
        topPanel = new JPanel();
        middlePanel = new JPanel();
        bottomPanel = new JPanel();

        for (JPanel p : new JPanel[]{topPanel, middlePanel, bottomPanel}) {
            p.setPreferredSize(SECTION_PANEL_SIZE);
            p.setLayout(null);
            this.add(p);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: initializes tables on the frame
     */
    private void initTables() {
        guideTable = new JTable(2, 1);
        guideTable.setValueAt("Time slept:", 0, 0);
        guideTable.setValueAt("# of interruptions:", 1, 0);

        sleepTable = new JTable(2, Time.DAYSINAWEEK);

        int i = 0;
        for (JTable t : new JTable[]{guideTable, sleepTable}) {
            t.setBounds(10 + i * 120, 0, 120 + i++ * 440, SECTION_HEIGHT);
            t.setRowHeight(ROW_HEIGHT);
            t.setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
            t.setGridColor(Color.black);
            t.setEnabled(false);
            topPanel.add(t);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: initializes middle components of the frame
     */
    private void initMiddlePanel() {
        initDayButtons();

        limiterField = new JTextField("duration XX:XX");
        set = new JButton("Set");
        dashLabel = new JLabel("-");
        timespanStart = new JTextField("start XX:XX");
        timespanEnd = new JTextField("end XX:XX");
        sleepyLabel = new JLabel(new ImageIcon("./data/sleep-tired.gif"));

        set.setBounds(300, 87, 100, BUTTON_HEIGHT);
        set.setActionCommand("Set");
        set.addActionListener(this);

        limiterField.setBounds(25, 25, 100, BUTTON_HEIGHT);
        dashLabel.setBounds(348, 50, 100, BUTTON_HEIGHT);
        timespanStart.setBounds(225, 50, 100, BUTTON_HEIGHT);
        timespanEnd.setBounds(375, 50, 100, BUTTON_HEIGHT);
        sleepyLabel.setBounds(575, 25, 100, 100);

        middlePanel.add(limiterField);
        middlePanel.add(set);
        middlePanel.add(dashLabel);
        middlePanel.add(timespanStart);
        middlePanel.add(timespanEnd);
        middlePanel.add(sleepyLabel);

        enableMiddleComponents(false);
    }

    /*
     * MODIFIES: this
     * EFFECTS: initializes day buttons
     */
    private void initDayButtons() {
        day1Button = new JButton("^");
        day2Button = new JButton("^");
        day3Button = new JButton("^");
        day4Button = new JButton("^");
        day5Button = new JButton("^");
        day6Button = new JButton("^");
        day7Button = new JButton("^");

        int i = 0;
        for (JButton b : new JButton[]{day1Button, day2Button, 
                day3Button, day4Button, day5Button, day6Button, day7Button}) {
            b.setBounds(130 + i++ * 80, 5, SMALL_BUTTON_WIDTH, BUTTON_HEIGHT);
            b.setActionCommand(b.getText());
            b.addActionListener(this);
            middlePanel.add(b);
        }

        enableDayButtons(false);
    }

    /*
     * MODIFIES: this
     * EFFECTS: enables and shows / disables and hides middle components
     */
    private void enableMiddleComponents(boolean enable) {
        for (JComponent c : new JComponent[]{set, dashLabel, timespanStart, timespanEnd}) {
            c.setEnabled(enable);
            c.setVisible(enable);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: enables and shows / disables and hides day buttons
     */
    private void enableDayButtons(boolean enable) {
        for (JButton b : new JButton[]{day1Button, day2Button, 
                day3Button, day4Button, day5Button, day6Button, day7Button}) {
            b.setEnabled(enable);
            b.setVisible(enable);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: initializes bottom left components on the frame
     */
    private void initBottomPanel1() {
        bottomPanel1 = new JPanel();
        backwardButton = new JButton("<-");
        forwardButton = new JButton("->");
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");

        bottomPanel1.setBounds(0, 0, 100, SECTION_HEIGHT);
        bottomPanel1.setLayout(null);
        bottomPanel.add(bottomPanel1);

        int i = 0;
        for (JButton b : new JButton[]{backwardButton, forwardButton, saveButton, loadButton}) {
            b.setBounds(10, (4 + i++ * 28), SMALL_BUTTON_WIDTH, BUTTON_HEIGHT);
            bottomPanel1.add(b);
            b.setActionCommand(b.getText());
            b.addActionListener(this);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: initializes bottom center-left components on the frame
     */
    private void initBottomPanel2() {
        bottomPanel2 = new JPanel();
        addNightButton = new JButton("Add Night");
        removeNightButton = new JButton("Remove Night");
        viewModeBox = new JComboBox<>(new String[] {"View Mode", "All", "Over", "Under"});

        bottomPanel2.setBounds(100, 0, 200, SECTION_HEIGHT);
        bottomPanel2.setLayout(null);
        bottomPanel.add(bottomPanel2);

        int i = 0;
        for (JComponent c : new JComponent[]{addNightButton, removeNightButton, viewModeBox}) {
            c.setBounds(new Rectangle(25, (12 + i++ * 36), BUTTON_WIDTH, BUTTON_HEIGHT));
            if (c instanceof JButton) {
                ((JButton) c).setActionCommand(((JButton) c).getText());
                ((JButton) c).addActionListener(this);
            } else if (c instanceof JComboBox) {
                ((JComboBox) c).setActionCommand(((JComboBox<String>) c).getItemAt(0));
                ((JComboBox) c).addActionListener(this);
            }
            bottomPanel2.add(c);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: initializes bottom center-right components on the frame
     */
    private void initBottomPanel3() {
        bottomPanel3 = new JPanel();
        viewNightButton = new JButton("View Night");
        editNightButton = new JButton("Edit Night");

        bottomPanel3.setBounds(300, 0, 200, SECTION_HEIGHT);
        bottomPanel3.setLayout(null);
        bottomPanel.add(bottomPanel3);

        int i = 0;
        for (JButton b : new JButton[]{viewNightButton, editNightButton}) {
            b.setBounds(25, (24 + i++ * 48), BUTTON_WIDTH, BUTTON_HEIGHT);
            b.setActionCommand(b.getText());
            b.addActionListener(this);
            bottomPanel3.add(b);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: initializes bottom right components on the frame
     */
    private void initBottomPanel4() {
        bottomPanel4 = new JPanel();
        addInterruptionButton = new JButton("Add Interruption");
        removeInterruptionButton = new JButton("Remove Interruption");

        bottomPanel4.setBounds(500, 0, 200, SECTION_HEIGHT);
        bottomPanel4.setLayout(null);
        bottomPanel.add(bottomPanel4);

        int i = 0;
        for (JButton b : new JButton[]{addInterruptionButton, removeInterruptionButton}) {
            b.setBounds(25, (24 + i++ * 48), BUTTON_WIDTH, BUTTON_HEIGHT);
            b.setActionCommand(b.getText());
            b.addActionListener(this);
            bottomPanel4.add(b);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: processes user interaction
     */
    @SuppressWarnings("methodlength")
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "<-":
                backward();
                break;
            case "->":
                forward();
                break;
            case "Save":
                save();
                break;
            case "Load":
                load();
                break;
            case "Add Night":
                enableMiddleComponents(true);
                setCmd = 1;
                break;
            case "Remove Night":
                if (log.size() > 0) {
                    removeNight();
                }
                break;
            case "View Mode":
                viewmode = viewModeBox.getSelectedIndex();
                break;
            case "View Night":
                dayCmd = 1;
                if (viewmode != 4) {
                    viewmode = 4;
                    enableDayButtons(true);
                    editNightButton.setEnabled(false);
                    editNightButton.setVisible(false);
                    addInterruptionButton.setEnabled(false);
                    addInterruptionButton.setVisible(false);
                    removeInterruptionButton.setEnabled(false);
                    removeInterruptionButton.setVisible(false);
                } else {
                    viewmode = 1;
                    editNightButton.setEnabled(true);
                    editNightButton.setVisible(true);
                    addInterruptionButton.setEnabled(true);
                    addInterruptionButton.setVisible(true);
                    removeInterruptionButton.setEnabled(true);
                    removeInterruptionButton.setVisible(true);
                }
                break;
            case "Edit Night":
                dayCmd = 2;
                enableDayButtons(true);
                break;
            case "Add Interruption":
                dayCmd = 3;
                enableDayButtons(true);
                break;
            case "Remove Interruption":
                dayCmd = 4;
                enableDayButtons(true);
                break;
            case "^":
                enableDayButtons(false);
                day((JButton) e.getSource());
            case "Set":
                set();
                break;
            default:
                break;
        }
        update();
    }

    /*
     * MODIFIES: this
     * EFFECTS: reduces page
     */
    private void backward() {
        page--;
    }

    /*
     * MODIFIES: this
     * EFFECTS: increases page
     */
    private void forward() {
        page++;
    }

    /*
     * MODIFIES: out file
     * EFFECTS: writes current SleepLog to file
     */
    private void save() {
        try {
            writer.open();
            writer.write(log);
            writer.close();
        } catch (FileNotFoundException e) {
            System.exit(0);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: converts file to SleepLog, overwrites current data
     */
    private void load() {
        if (reader.read() != null) {
            log = reader.read();
        } else {
            System.exit(0);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: removes last night from SleepLog
     */
    private void removeNight() {
        log.remove(log.size() - 1);
    }

    /*
     * MODIFIES: this
     * EFFECTS: macro view of specified night
     */
    private void viewNight(JButton source) {
        JButton[] days = {day1Button, day2Button, day3Button, day4Button, day5Button, day6Button, day7Button};
        buttonPressed = Arrays.asList(days).indexOf(source);
        if (buttonPressed + page * Time.DAYSINAWEEK < viewLog.size()) {
            NightSlept night = viewLog.get(buttonPressed + page * Time.DAYSINAWEEK);

            guideTable.setValueAt(night.getStart().toString(), 0, 0);
            guideTable.setValueAt(night.getEnd().toString(), 1, 0);
            page = 0;

            for (int i = 0; i < 7; i++) {
                if (i + page * 7 < night.getNumInterruptions()) {
                    sleepTable.setValueAt(night.getInterruptions().get(i + page * 7).getStart(), 0, i);
                    sleepTable.setValueAt(night.getInterruptions().get(i + page * 7).getEnd(), 1, i);
                } else {
                    sleepTable.setValueAt("", 0, i);
                    sleepTable.setValueAt("", 1, i);
                }
            }
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: edits specified night to user input
     */
    private void editNight(JButton source) {
        JButton[] days = {day1Button, day2Button, day3Button, day4Button, day5Button, day6Button, day7Button};
        buttonPressed = Arrays.asList(days).indexOf(source);
        if (buttonPressed + page * Time.DAYSINAWEEK < viewLog.size()) {
            enableMiddleComponents(true);
            setCmd = 2;
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: finalizes edit
     */
    private void editNight(int buttonPressed) {
        NightSlept night = viewLog.get(buttonPressed + page * Time.DAYSINAWEEK);

        night.edit(timespanInput.getStart(), timespanInput.getEnd());
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds interruption to specified night according to user input
     */
    private void addInterruption(JButton source) {
        JButton[] days = {day1Button, day2Button, day3Button, day4Button, day5Button, day6Button, day7Button};
        buttonPressed = Arrays.asList(days).indexOf(source);
        if (buttonPressed + page * Time.DAYSINAWEEK < viewLog.size()) {
            enableMiddleComponents(true);
            setCmd = 3;
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: finalizes interruption addition
     */
    private void addInterruption(int buttonPressed) {
        NightSlept night = viewLog.get(buttonPressed + page * Time.DAYSINAWEEK);

        night.addInterruption(new Interruption(timespanInput));
    }

    /*
     * MODIFIES: this
     * EFFECTS: removes last interruption from specified night
     */
    private void removeInterruption(JButton source) {
        JButton[] days = {day1Button, day2Button, day3Button, day4Button, day5Button, day6Button, day7Button};
        buttonPressed = Arrays.asList(days).indexOf(source);

        if (buttonPressed + page * Time.DAYSINAWEEK < viewLog.size()) {
            NightSlept night = viewLog.get(buttonPressed + page * Time.DAYSINAWEEK);

            night.removeLastInterruption();
        }
    }

    /*
     * EFFECTS: auxiliary function for processing dayButton related functions
     */
    private void day(JButton source) {
        switch (dayCmd) {
            case 1:
                viewNight(source);
                break;
            case 2:
                editNight(source);
                break;
            case 3:
                addInterruption(source);
                break;
            case 4:
                removeInterruption(source);
                break;
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: auxiliary function for processing setButton related functions
     */
    private void set() {
        Time start;
        Time end;
        timespanInput = null;
        try {
            start = Time.fromString(timespanStart.getText());
            end = Time.fromString(timespanEnd.getText());
            if (start != null && end != null) {
                timespanInput = new Timespan(start, end);
            } else {
                return;
            }
            setSwitch();
            enableMiddleComponents(false);
            timespanStart.setText("start XX:XX");
            timespanEnd.setText("end XX:XX");
        } catch (IllegalTimeException e) {
            return;
        } catch (IllegalTimespanException e) {
            return;
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: auxiliary function for processing setButton related functions
     */
    private void setSwitch() {
        switch (setCmd) {
            case 1:
                log.add(new NightSlept(timespanInput));
                break;
            case 2:
                editNight(buttonPressed);
                break;
            case 3:
                addInterruption(buttonPressed);
                break;
        }
    }

    private void parseLimiter() {
        Time limitTime = Time.fromString(limiterField.getText());
        if (limitTime != null) {
            limiter = limitTime.toMinutes();
        } else {
            limiter = 420;
            limiterField.setText("duration XX:XX");
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: updates the tables and updates limiting duration
     */
    private void update() {
        if (viewmode != 4) {
            viewLog = new ArrayList<>();
            guideTable.setValueAt("Time slept:", 0, 0);
            guideTable.setValueAt("# of interruptions:", 1, 0);
            updateViewLog();
            for (int i = 0; i < 7; i++) {
                if (i + page * Time.DAYSINAWEEK < viewLog.size()) {
                    sleepTable.setValueAt(
                            viewLog.get(i + page * Time.DAYSINAWEEK).getDuration() / Time.MINUTESINANHOUR
                                    + "h"
                                    + viewLog.get(i + page * Time.DAYSINAWEEK).getDuration() % Time.MINUTESINANHOUR
                                    + "m", 0, i);
                    sleepTable.setValueAt(viewLog.get(i + page * Time.DAYSINAWEEK).getNumInterruptions(), 1, i);
                } else {
                    sleepTable.setValueAt("", 0, i);
                    sleepTable.setValueAt("", 1, i);
                }
            }
        }
        parseLimiter();
    }

    /*
     * MODIFIES: this
     * EFFECTS: updates currently visible values
     */
    private void updateViewLog() {
        switch (viewmode) {
            case 1:
                for (NightSlept n : log.getLog()) {
                    viewLog.add(n);
                }
                break;
            case 2:
                System.out.println("over");
                for (NightSlept n : log.getLog()) {
                    if (n.getDuration() > limiter) {
                        viewLog.add(n);
                    }
                }
                break;
            case 3:
                for (NightSlept n : log.getLog()) {
                    if (n.getDuration() < limiter) {
                        viewLog.add(n);
                    }
                }
                break;
        }
    }
}

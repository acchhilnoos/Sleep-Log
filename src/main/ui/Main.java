package ui;

import model.Event;
import model.EventLog;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        SleepLogApp sleepLogApp = new SleepLogApp();

        frame.setTitle("Sleep Log");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });
        frame.add(sleepLogApp);
        frame.pack();
    }

    private static void exit() {
        Iterator<Event> events = EventLog.getInstance().iterator();
        while (events.hasNext()) {
            System.out.println(events.next());
        }
        System.exit(0);
    }
}

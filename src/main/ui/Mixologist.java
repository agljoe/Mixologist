package ui;

import model.Event;
import model.EventLog;
import ui.view.ContentView;

// Repesents the Mixologist game
public class Mixologist {  

    // EFFECTS: creates a new instance of the mixologist game
    public Mixologist() {
        new ContentView();
    }

    // EFFECTS: prints out all the events in event log to console
    public static void printLog(EventLog eventLog) {
        for (Event e : eventLog) {
            System.out.println(e.toString());
        }
    }
}
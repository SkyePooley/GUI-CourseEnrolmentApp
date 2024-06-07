package GUI;

import Model.CalendarEvent;
import Model.DBModel;
import Model.Enrolment;
import Model.Timetable;

import javax.swing.*;
import java.awt.*;

/**
 * Displays the student's schedule on the view.
 * Makes used of a JTable to display the schedule on a grid
 * @author Skye Pooley
 */
public class SchedulePanel extends JPanel implements DisplayPanel{
    private final JTable scheduleTable;

    public SchedulePanel() {
        BorderLayout layout = new BorderLayout(5, 5);
        this.setLayout(layout);

        scheduleTable = tableSetup();
        add(new JScrollPane(scheduleTable), BorderLayout.CENTER);
    }

    /**
     * Creates a new JTable and initialises its values to show the days of the week and hours of the day.
     * @return JTable formatted for schedule display
     * @author Skye Pooley
     */
    private JTable tableSetup() {
        String[] columnLabels = {"Time", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        String[][] startingData = new String[16][6];
        JTable table = new JTable(startingData, columnLabels);
        table.setRowHeight(30);
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowSelectionAllowed(false);
        table.getColumnModel().getColumn(0).setPreferredWidth(10);

        for (int i=0; i<table.getRowCount(); i++) {
            String value = i+8 + ":00";
            table.setValueAt(value, i, 0);
        }

        return table;
    }

    /**
     * Clear the current schedule display and replace it with the latest info from the model
     * @param model DBModel to retrieve schedule info
     * @author Skye Pooley
     */
    public void update(DBModel model){
        clearEvents();
        for (Enrolment enrolment : model.getCurrentSemesterEnrolments()) {
            addTimetable(enrolment.getCourse(), enrolment.getTimetable());
        }
        for (Enrolment enrolment : model.getTempSemesterEnrolments()) {
            addTimetable("*"+enrolment.getCourse(), enrolment.getTimetable());
        }
    }

    /**
     * Adds a single timetable onto the table.
     * @param title Course code or name to display on events from this timetable
     * @param timetable Timetable to find event times
     * @author Skye Pooley
     */
    private void addTimetable(String title, Timetable timetable) {
        addEvent(
                title,
                "Lecture",
                timetable.getLecture()
        );
        if (timetable.hasTutorial()) {
            addEvent(
                    title,
                    "Tutorial",
                    timetable.getTutorial()
            );
        }
        if (timetable.hasLab()) {
            addEvent(
                    title,
                    "Lab",
                    timetable.getLab()
            );
        }
    }

    /**
     * Add an event to the schedule view
     * @param title Title of the event, typically course code
     * @param subtitle Subtitle, typically event type
     * @param event event for timings
     * @author Skye Pooley
     */
    private void addEvent(String title, String subtitle, CalendarEvent event) {
        String label = title + " - " + subtitle;
        for (int i= event.getEventHour()-8; i<event.getEndHour()-7; i++) {
            // this is gross but a better way was not obvious
            switch (event.getEventDay()) {
                case MONDAY:
                    scheduleTable.setValueAt(label, i, 1);
                    break;
                case TUESDAY:
                    scheduleTable.setValueAt(label, i, 2);
                    break;
                case WEDNESDAY:
                    scheduleTable.setValueAt(label, i, 3);
                    break;
                case THURSDAY:
                    scheduleTable.setValueAt(label, i, 4);
                    break;
                case FRIDAY:
                    scheduleTable.setValueAt(label, i, 5);
                    break;
            }
        }
    }

    /**
     * Set all the event cells on the table blank.
     * @author Skye Pooley
     */
    private void clearEvents() {
        for (int column=1; column<scheduleTable.getColumnCount(); column++ ) {
            for (int row = 0; row < scheduleTable.getRowCount(); row++) {
                scheduleTable.setValueAt("", row, column);
            }
        }
    }
}

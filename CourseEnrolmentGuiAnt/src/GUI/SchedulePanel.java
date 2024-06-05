package GUI;

import Model.CalendarEvent;
import Model.DBModel;
import Model.Enrolment;
import Model.Timetable;

import javax.swing.*;
import java.awt.*;

public class SchedulePanel extends JPanel implements DisplayPanel{
    private JTable scheduleTable;

    public SchedulePanel() {
        BorderLayout layout = new BorderLayout(5, 5);
        this.setLayout(layout);

        scheduleTable = tableSetup();
        add(new JScrollPane(scheduleTable), BorderLayout.CENTER);
    }

    private JTable tableSetup() {
        String[] columnLabels = {"Time", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        String[][] startingData = new String[16][6];
        JTable table = new JTable(startingData, columnLabels);
        table.setRowHeight(30);

        for (int i=0; i<table.getRowCount(); i++) {
            String value = i+8 + ":00";
            table.setValueAt(value, i, 0);
        }

        return table;
    }

    public void update(DBModel model){
        clearEvents();
        for (Enrolment enrolment : model.getCurrentEnrolments()) {
            addTimetable(enrolment.getCourse(), enrolment.getTimetable(), false);
        }
        for (Enrolment enrolment : model.getTempEnrolments()) {
            addTimetable(enrolment.getCourse(), enrolment.getTimetable(), true);
        }
    }

    private void addTimetable(String title, Timetable timetable, boolean temporary) {
        addEvent(
                title,
                "Lecture",
                timetable.getLecture(),
                temporary
        );
        if (timetable.hasTutorial()) {
            addEvent(
                    title,
                    "Tutorial",
                    timetable.getTutorial(),
                    temporary
            );
        }
        if (timetable.hasLab()) {
            addEvent(
                    title,
                    "Lab",
                    timetable.getLab(),
                    temporary
            );
        }
    }

    private void addEvent(String title, String subtitle, CalendarEvent event, boolean temp) {
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

    private void clearEvents() {
        for (int column=1; column<scheduleTable.getColumnCount(); column++ ) {
            for (int row = 0; row < scheduleTable.getRowCount(); row++) {
                scheduleTable.setValueAt("", row, column);
            }
        }
    }
}

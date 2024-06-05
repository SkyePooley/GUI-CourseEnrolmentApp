package GUI;

import Model.DBModel;

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

    }
}

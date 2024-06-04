/**
 * @author Clayton
 */
package GUI;

import Model.DBModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class StreamSelectionPanel extends JPanel implements InteractivePanel {
    private JComboBox<String> streamComboBox;
    private JButton addToScheduleButton;
    private JLabel clashWarningLabel;
    
    public StreamSelectionPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel streamLabel = new JLabel("Select Stream");
        streamComboBox = new JComboBox<>(new String[]{"Stream 1", "Stream 2"});
        streamComboBox.setActionCommand("Stream selected");

        addToScheduleButton = new JButton("Add to Schedule");
        clashWarningLabel = new JLabel("No Clash");

        add(streamLabel);
        add(streamComboBox);
        add(addToScheduleButton);
        add(clashWarningLabel);
    }

    public void addActionListener(ActionListener listener) {
        streamComboBox.addActionListener(listener);
        addToScheduleButton.addActionListener(listener);
    }

    /**
     * Update whether the selected stream clashes
     * @param model
     */
    public void update(DBModel model) {
        if (model.streamClash) {
            clashWarningLabel.setForeground(Color.red);
            clashWarningLabel.setText("Clash");
            addToScheduleButton.setEnabled(false);
        }
        else {
            clashWarningLabel.setForeground(Color.green);
            clashWarningLabel.setText("No Clash");
            addToScheduleButton.setEnabled(true);
        }
        repaint();
    }

    /**
     * Refresh stream options
     * @param model Reference to find out how many stream options there are
     */
    public void refresh(DBModel model) {
        streamComboBox.removeAllItems();
        for (int i=1; i<=model.getNumberOfStreams(); i++) {
            streamComboBox.addItem(i+"");
        }
    }
}

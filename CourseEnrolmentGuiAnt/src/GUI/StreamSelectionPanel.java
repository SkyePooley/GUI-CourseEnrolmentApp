/**
 * @author Clayton
 */
package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class StreamSelectionPanel extends JPanel implements InteractivePanel {
    public JComboBox<String> streamComboBox;
    private JButton addToScheduleButton;
    
    public StreamSelectionPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel streamLabel = new JLabel("Select Stream");
        streamComboBox = new JComboBox<>(new String[]{"Stream 1", "Stream 2"});
        streamComboBox.setActionCommand("Stream selected");
        addToScheduleButton = new JButton("Add to Schedule");
        add(streamLabel);
        add(streamComboBox);
        add(addToScheduleButton);
    }

    public void addActionListener(ActionListener listener) {
        streamComboBox.addActionListener(listener);
        addToScheduleButton.addActionListener(listener);
    }
}

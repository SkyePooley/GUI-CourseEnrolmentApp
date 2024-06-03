/**
 * @author Clayton
 */
package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class StreamSelectionPanel extends JPanel {
    public JComboBox<String> streamComboBox;
    
    public StreamSelectionPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel streamLabel = new JLabel("Select Stream");
        streamComboBox = new JComboBox<>(new String[]{"Stream 1", "Stream 2"});
        add(streamLabel);
        add(streamComboBox);
    }

    public void addActionListener(ActionListener listener) {
        streamComboBox.addActionListener(listener);
    }
}

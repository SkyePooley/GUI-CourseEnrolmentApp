/**
 * @author Clayton
 */
package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class BottomPanel extends JPanel implements InteractivePanel {
    public JButton revertChangesButton;
    public JButton confirmAndSaveButton;
    
    public BottomPanel() {
        setLayout(new FlowLayout(FlowLayout.RIGHT));
        revertChangesButton = new JButton("Revert Changes");
        confirmAndSaveButton = new JButton("Confirm and Save");
        add(revertChangesButton);
        add(confirmAndSaveButton);
    }

    @Override
    public void addActionListener(ActionListener listener) {
        revertChangesButton.addActionListener(listener);
        confirmAndSaveButton.addActionListener(listener);
    }
}
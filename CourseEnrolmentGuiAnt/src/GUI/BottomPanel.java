/**
 * @author Clayton
 */
package GUI;

import javax.swing.*;
import java.awt.*;

public class BottomPanel extends JPanel {
    public JButton revertChangesButton;
    public JButton confirmAndSaveButton;
    
    public BottomPanel() {
        setLayout(new FlowLayout(FlowLayout.RIGHT));
        revertChangesButton = new JButton("Revert Changes");
        confirmAndSaveButton = new JButton("Confirm and Save");
        add(revertChangesButton);
        add(confirmAndSaveButton);
    }
}
/**
 * @author Clayton Roberts
 */
package GUI;

import Model.DBModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SaveRevertPanel extends JPanel implements InteractivePanel, DisplayPanel {
    private final JButton revertChangesButton;
    private final JButton confirmAndSaveButton;
    private final JLabel userFullName;

    /**
     * SaveRevertPanel class creates a user interface component with options to revert changes or confirm and save them.
     * @author Clayton Roberts
     */
    public SaveRevertPanel() {
        setLayout(new FlowLayout(FlowLayout.RIGHT));
        userFullName = new JLabel();
        revertChangesButton = new JButton("Revert Changes");
        confirmAndSaveButton = new JButton("Confirm and Save");
        add(userFullName);
        add(revertChangesButton);
        add(confirmAndSaveButton);
    }

    /**
     * Adds action listeners to revert and save buttons
     * @param listener
     * @author Skye Pooley
     */
    @Override
    public void addActionListener(ActionListener listener) {
        revertChangesButton.addActionListener(listener);
        confirmAndSaveButton.addActionListener(listener);
    }

    /**
     * Called after a login to show who is logged in
     * @param model Retrieve user name
     * @author Skye Pooley
     */
    public void update(DBModel model) {
        userFullName.setText(model.getUserFullName());
    }
}
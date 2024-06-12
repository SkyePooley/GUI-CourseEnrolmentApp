/**
 * @author Clayton
 */
package GUI;

import Model.DBModel;
import Model.Timetable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * The RemovePanel class provides a user interface for removing course enrolments.
 */
public class RemovePanel extends JPanel implements InteractivePanel, DisplayPanel
{
    // combo box for selecting an elrolment to remove
    private final JComboBox<String> enrolmentComboBox;
    // Button to trigger the removal of the selected enrolment
    private final JButton removeButton;

    /**
     * Constructor for RemovePanel and initializes the layout and components.
     */
    public RemovePanel()
    {
        JLabel enrolmentLabel = new JLabel("Select Enrolment to remove");
        enrolmentComboBox = new JComboBox<>();
        enrolmentComboBox.setActionCommand("Remove Course Selected");

        // initialize the remove button and sets its action command
        removeButton = new JButton("Remove");
        removeButton.setActionCommand("Remove Enrolment");

        // Add components to the panel
        add(enrolmentLabel);
        add(enrolmentComboBox);
        add(removeButton);

        // Set the layout of the panel to align components to the left
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
    }

    /**
     * Updates the combo box with current semester enrolments
     * @param model
     */
    @Override
    public void update(DBModel model) {
        enrolmentComboBox.removeAllItems();
        HashMap<String, Timetable> enrolments = model.getCurrentSemesterEnrolments();
        for (String courseCode : enrolments.keySet()) {
            enrolmentComboBox.addItem(courseCode);
        }
    }

    /**
     * Adds action listeners to the remove button and combo box
     * @param l
     */
    @Override
    public void addActionListener(ActionListener l) {
        removeButton.addActionListener(l);
        enrolmentComboBox.addActionListener(l);
    }

    /**
     * Returns the corse code of selected enrolments
     * @return
     */
    public String getSelectedCourse() {
        return (String) enrolmentComboBox.getSelectedItem();
    }
}

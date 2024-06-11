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
 * The RemovePanel class provides a user interface for removing course enrolments
 * @author Clayton
 */
public class RemovePanel extends JPanel implements InteractivePanel, DisplayPanel
{
    // combo box for selecting an enrolment to remove
    private final JComboBox<String> enrolmentComboBox;
    // button to trigger and remove the selected enrolment
    private final JButton removeButton;

    /**
     * Constructor for RemovePanel.
     * @author Clayton
     */
    public RemovePanel()
    {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        //JLabel for enrolment selection combo box
        JLabel enrolmentLabel = new JLabel("Select Enrolment to remove");
        enrolmentComboBox = new JComboBox<>();
        enrolmentComboBox.setActionCommand("Remove Course Selected");

        // Initialize the remove button
        removeButton = new JButton("Remove Enrolment");

        // Add component to panel
        add(enrolmentLabel);
        add(enrolmentComboBox);
        add(removeButton);
    }

    /**
     * Updates the combo box with the current sem enrolments form model
     * @param model
     * @author Clayton
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
     * adds action listeners to the remove button and combo box.
     * @param l
     * @author Clayton
     *
     */
    @Override
    public void addActionListener(ActionListener l) {
        removeButton.addActionListener(l);
        enrolmentComboBox.addActionListener(l);
    }

    /**
     * Returns the course code of the selected enrolments
     * @return
     * @author Clayton
     */
    public String getSelectedCourse() {
        return (String) enrolmentComboBox.getSelectedItem();
    }
}

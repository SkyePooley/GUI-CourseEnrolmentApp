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

public class RemovePanel extends JPanel implements InteractivePanel, DisplayPanel
{
    private final JComboBox<String> enrolmentComboBox;
    private final JButton removeButton;

    public RemovePanel()
    {
        JLabel enrolmentLabel = new JLabel("Select Enrolment to remove");
        enrolmentComboBox = new JComboBox<>();
        enrolmentComboBox.setActionCommand("Remove Course Selected");
        removeButton = new JButton("Remove Enrolment");

        add(enrolmentLabel);
        add(enrolmentComboBox);
        add(removeButton);

        this.setLayout(new FlowLayout(FlowLayout.LEFT));
    }

    @Override
    public void update(DBModel model) {
        enrolmentComboBox.removeAllItems();
        HashMap<String, Timetable> enrolments = model.getCurrentSemesterEnrolments();
        for (String courseCode : enrolments.keySet()) {
            enrolmentComboBox.addItem(courseCode);
        }
    }

    @Override
    public void addActionListener(ActionListener l) {
        removeButton.addActionListener(l);
        enrolmentComboBox.addActionListener(l);
    }

    public String getSelectedCourse() {
        return (String) enrolmentComboBox.getSelectedItem();
    }
}

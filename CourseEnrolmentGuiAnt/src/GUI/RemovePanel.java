/**
 * @author Clayton
 */
package GUI;

import Model.DBModel;
import Model.Enrolment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class RemovePanel extends JPanel implements InteractivePanel, DisplayPanel
{

    private final JComboBox<String> enrolmentComboBox;
    private final JButton removeButton;

    public RemovePanel()
    {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel enrolmentLabel = new JLabel("Select Enrolment to remove");
        enrolmentComboBox = new JComboBox<>();
        removeButton = new JButton("Remove Enrolment");

        add(enrolmentLabel);
        add(enrolmentComboBox);
        add(removeButton);
    }

    @Override
    public void update(DBModel model) {
        enrolmentComboBox.removeAllItems();
        for (Enrolment enrolment : model.getCurrentSemesterEnrolments()){
            enrolmentComboBox.addItem(enrolment.getCourse());
        }
    }

    @Override
    public void addActionListener(ActionListener l) {
        removeButton.addActionListener(l);
    }
}

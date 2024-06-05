/**
 * @author Clayton
 */
package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class RemovePanel extends JPanel implements InteractivePanel
{

    private JComboBox<String> enrolmentComboBox;
    private JButton removeButton;

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

    public void updateEnrolments(List<String> enrolments){
        enrolmentComboBox.removeAllItems();
        for (String enrolment : enrolments){
            enrolmentComboBox.addItem(enrolment);
        }
    }

    @Override
    public void addActionListener(ActionListener l) {
        removeButton.addActionListener(l);
    }
}

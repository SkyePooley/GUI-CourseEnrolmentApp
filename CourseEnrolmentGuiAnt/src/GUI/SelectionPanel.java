/**
 * @author Clayton Roberts
 */
package GUI;

import Model.DBModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class SelectionPanel extends JPanel implements InteractivePanel, DisplayPanel {
    private final JComboBox<String> courseComboBox;

    /**
     * The SelectionPanel class provides a user interface component for selecting a course.
     * @author Clayton Roberts
     */
    public SelectionPanel() {
        setLayout(new GridLayout(2, 1));

        JPanel coursePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel courseLabel = new JLabel("Select Course");
        courseComboBox = new JComboBox<>(new String[]{"CourseCode 1", "CourseCode 2"});
        courseComboBox.setActionCommand("Course Selected");
        coursePanel.add(courseLabel);
        coursePanel.add(courseComboBox);

        add(coursePanel);
    }

    @Override
    public void addActionListener(ActionListener listener) {
        courseComboBox.addActionListener(listener);
    }

    /**
     * Refresh all the courses in the course selection dropdown menu.
     * @param model to retrieve list of course codes in the selected semester
     * @author Skye Pooley
     */
    public void update(DBModel model) {
        LinkedList<String> courseCodes = model.eligibleCourseCodes;
        courseComboBox.removeAllItems();
        for (String code : courseCodes) {
            courseComboBox.addItem(code);
        }
    }
}

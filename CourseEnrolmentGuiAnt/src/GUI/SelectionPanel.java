/**
 * @author Clayton
 */
package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class SelectionPanel extends JPanel{
    public JComboBox<String> semesterComboBox;
    public JComboBox<String> courseComboBox;

    public SelectionPanel() {
        setLayout(new GridLayout(2, 1));
        JPanel semesterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel semesterLabel = new JLabel("Select Semester");
        semesterComboBox = new JComboBox<String>(new String[]{"1", "2"});
        semesterComboBox.setActionCommand("Semester Selected");
        semesterPanel.add(semesterLabel);
        semesterPanel.add(semesterComboBox);

        JPanel coursePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel courseLabel = new JLabel("Select Course");
        courseComboBox = new JComboBox<>(new String[]{"CourseCode 1", "CourseCode 2"});
        courseComboBox.setActionCommand("Course Selected");
        coursePanel.add(courseLabel);
        coursePanel.add(courseComboBox);

        add(semesterPanel);
        add(coursePanel);
    }

    public void addActionListener(ActionListener listener) {
        semesterComboBox.addActionListener(listener);
        courseComboBox.addActionListener(listener);
    }

    /**
     * Refresh all the courses in the course selection dropdown menu.
     * @param courseCodes List of course codes in the selected semester
     * @author Skye Pooley
     */
    public void update(LinkedList<String> courseCodes) {
        courseComboBox.removeAllItems();
        for (String code : courseCodes) {
            courseComboBox.addItem(code);
        }
    }
}

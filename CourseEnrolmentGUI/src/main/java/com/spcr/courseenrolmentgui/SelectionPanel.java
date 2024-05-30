/**
 * @author Clayton
 */
package com.spcr.courseenrolmentgui;

import javax.swing.*;
import java.awt.*;

public class SelectionPanel extends JPanel{
    public JComboBox<String> semesterComboBox;
    public JComboBox<String> courseComboBox;

    public SelectionPanel() {
        setLayout(new GridLayout(2, 1));
        JPanel semesterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel semesterLabel = new JLabel("Select Semester");
        semesterComboBox = new JComboBox<>(new String[]{"Semester 1", "Semester 2"});
        semesterPanel.add(semesterLabel);
        semesterPanel.add(semesterComboBox);

        JPanel coursePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel courseLabel = new JLabel("Select Course");
        courseComboBox = new JComboBox<>(new String[]{"CourseCode 1", "CourseCode 2"});
        coursePanel.add(courseLabel);
        coursePanel.add(courseComboBox);

        add(semesterPanel);
        add(coursePanel);
    }
}

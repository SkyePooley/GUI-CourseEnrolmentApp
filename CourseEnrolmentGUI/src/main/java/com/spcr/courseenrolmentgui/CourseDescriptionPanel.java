/**
 * @author Clayton
 */
package com.spcr.courseenrolmentgui;

import javax.swing.*;
import java.awt.*;

public class CourseDescriptionPanel extends JPanel {
    public JTextArea courseDescriptionArea;

    public CourseDescriptionPanel() {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Selected Course");
        courseDescriptionArea = new JTextArea(5, 20);
        courseDescriptionArea.setText("Course Description...");
        courseDescriptionArea.setLineWrap(true);
        courseDescriptionArea.setWrapStyleWord(true);
        courseDescriptionArea.setEditable(false);

        add(label, BorderLayout.NORTH);
        add(new JScrollPane(courseDescriptionArea), BorderLayout.CENTER);
    }
}

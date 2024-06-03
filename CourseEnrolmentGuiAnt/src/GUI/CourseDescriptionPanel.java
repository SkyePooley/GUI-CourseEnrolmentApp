/**
 * @author Clayton
 */
package GUI;

import Model.Course;

import javax.swing.*;
import java.awt.*;

public class CourseDescriptionPanel extends JPanel {
    private JLabel title;
    private JTextArea courseDescriptionArea;

    public CourseDescriptionPanel() {
        setLayout(new BorderLayout());
        title = new JLabel("Selected Course");
        courseDescriptionArea = new JTextArea(5, 20);
        courseDescriptionArea.setText("Course Description...");
        courseDescriptionArea.setLineWrap(true);
        courseDescriptionArea.setWrapStyleWord(true);
        courseDescriptionArea.setEditable(false);

        add(title, BorderLayout.NORTH);
        add(new JScrollPane(courseDescriptionArea), BorderLayout.CENTER);
    }

    /**
     * Update the course description with a new course
     * @param course Course to show
     * @author Skye Pooley
     */
    public void update(Course course) {
        title.setText(course.getCode());
        courseDescriptionArea.setText(course.toString());
    }
}

/**
 * @author Clayton
 */
package GUI;

import Model.Course;
import Model.DBModel;

import javax.swing.*;
import java.awt.*;

public class CourseDescriptionPanel extends JPanel implements DisplayPanel{
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
     * @param model to retrieve current course
     * @author Skye Pooley
     */
    public void update(DBModel model) {
        Course course = model.getSelectedCourse();
        title.setText(course.getCode());
        courseDescriptionArea.setText(course.toString());
    }
}

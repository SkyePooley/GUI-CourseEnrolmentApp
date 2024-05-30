/**
 *
 * @author Clayton
 */
package GUI;

import javax.swing.*;
import java.awt.*;

public class CourseScheduler extends JFrame{
        private JTable scheduleTable;

    public CourseScheduler() {
        setTitle("Course Scheduler");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        leftPanel.add(new AddRemovePanel());
        leftPanel.add(new SelectionPanel());
        leftPanel.add(new CourseDescriptionPanel());
//        leftPanel.add(new StreamSelectionPanel());
        leftPanel.add(createAddToScheduleButton());

        add(leftPanel, BorderLayout.WEST);

        scheduleTable = new JTable(15, 5); // 15 rows for time slots, 5 columns for days
        JScrollPane scrollPane = new JScrollPane(scheduleTable);
        add(scrollPane, BorderLayout.CENTER);

//        add(new BottomPanel(), BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createAddToScheduleButton() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addToScheduleButton = new JButton("Add to Schedule");
        panel.add(addToScheduleButton);

        addToScheduleButton.addActionListener(e -> {
            // Implement the action to add the course to the schedule
            JOptionPane.showMessageDialog(CourseScheduler.this, "Course added to the schedule!");
        });

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CourseScheduler::new);
    }
}

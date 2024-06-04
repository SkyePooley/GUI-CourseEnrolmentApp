/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 * @author Skye Pooley and Clayton Roberts
 */

package GUI;

import Model.DBModel;
import Model.UpdateFlags;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class View extends JFrame implements Observer {
    private final double defaultScreenPortionW = 0.5;
    private final double defaultScreenPortionH = 0.7;

    // keep the GUI objects here
    private JComboBox<String> SemesterComboBox;
    private JComboBox<String> courseComboBox;
    private JComboBox<String> streamComboBox;
    private JTextArea courseDescriptionArea;
    private JTable scheduleTable;
    private JButton addToScheduleButton;
    private JButton revertChangesButton;
    private JButton confirmAndSaveButton;
    
    public View() {
        
        System.out.println("View initialised");
        
        // initialise GUI elements
        setTitle("Course Enrolment Application");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        setLocation(100, 100);
        setSize((int) (screenSize.getWidth() * defaultScreenPortionW), (int) (screenSize.getHeight() * defaultScreenPortionH));
        
        //adding components
        JPanel leftPanel = new JPanel();
        
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        
        AddRemovePanel addRemovePanel = new AddRemovePanel();
        SelectionPanel selectionPanel = new SelectionPanel();
        CourseDescriptionPanel courseDescriptionPanel = new CourseDescriptionPanel();
        StreamSelectionPanel streamSelectionPanel = new StreamSelectionPanel();
        leftPanel.add(addRemovePanel);
        leftPanel.add(selectionPanel);
        leftPanel.add(courseDescriptionPanel);
        leftPanel.add(streamSelectionPanel);
        leftPanel.add(createAddToScheduleButton());
        
        add(leftPanel, BorderLayout.WEST);
        
        scheduleTable = new JTable(15, 5); // 15 time slots, 5 days for columns
        JScrollPane scrollPane = new JScrollPane(scheduleTable);
        add(scrollPane, BorderLayout.CENTER);
        
        add(createBottomPanel(), BorderLayout.SOUTH);
        
        setVisible(true);
    }
    
    private JPanel createAddToScheduleButton() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addToScheduleButton = new JButton("Add to Schedule");
        return panel;
    }
    
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        revertChangesButton = new JButton("Revert Changes");
        confirmAndSaveButton = new JButton("Confirm and save");
        panel.add(getRevertChangesButton());
        panel.add(getConfirmAndSaveButton());
        return panel;
    }

    /**
     * Take actions to update the GUI when the model notifies us of changes
     * @param obs       DBModel
     * @param obj       Object which was passed with this update, usually UpdateFlags instance
     * @author Skye Pooley
     */
    @Override
    public void update(Observable obs, Object obj) {
        // update the data in the gui based on model data and passed flags.
        if (!(obs instanceof DBModel)) { return; } // exit early if this was updated from an object we don't recognise
        if (obj instanceof UpdateFlags) {
            UpdateFlags flags = (UpdateFlags) obj;
            DBModel model = (DBModel) obs;

            if (flags.fullReset)            { this.reset(model); }
            if (flags.loginFail)            { this.loginFail(); }
            if (flags.loginSuccess)         { this.loginSuccess(model); }
            if (flags.scheduleUpdate)       { this.updateSchedule(model); }
            if (flags.courseDropdownUpdate) { this.updateCourseDropdown(model); }
            if (flags.courseSelected)       { this.updateCourseDetails(model); }
        }
    }

    private void reset(DBModel model) {
        // changes have been discarded, reload all panels using model data
    }

    private void loginFail() {
        // ID given was not valid, show a login error message
    }

    private void loginSuccess(DBModel model) {
        // login was successful and the model has data on the user now.
        // close the login popup and show the main interface.
        // The model will also have data on the schedule but this will be a separate method.
    }

    private void updateSchedule(DBModel model) {
        // the model has new data on the schedule, refresh the panel.
        // this will be called after a course is added, course confirmed, or user logged in.
    }

    private void updateCourseDropdown(DBModel model) {
        // Model has found all the courses in the selected semester, use this to fill the course selection dropdown
    }

    private void updateCourseDetails(DBModel model) {
        // After a course was selected from the dropdown menu the model has prepared into on it
        // Update the course info panel
    }

    /**
     * @return the SemesterComboBox
     */
    public JComboBox<String> getSemesterComboBox() {
        return SemesterComboBox;
    }

    /**
     * @return the courseComboBox
     */
    public JComboBox<String> getCourseComboBox() {
        return courseComboBox;
    }

    /**
     * @return the streamComboBox
     */
    public JComboBox<String> getStreamComboBox() {
        return streamComboBox;
    }

    /**
     * @return the courseDescriptionArea
     */
    public JTextArea getCourseDescriptionArea() {
        return courseDescriptionArea;
    }

    /**
     * @return the addToScheduleButton
     */
    public JButton getAddToScheduleButton() {
        return addToScheduleButton;
    }

    /**
     * @return the revertChangesButton
     */
    public JButton getRevertChangesButton() {
        return revertChangesButton;
    }

    /**
     * @return the confirmAndSaveButton
     */
    public JButton getConfirmAndSaveButton() {
        return confirmAndSaveButton;
    }
}


        /*
        System.out.println("View initialised");
        // initialise GUI elements

        JFrame frame = new JFrame("Course Enrolment Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        frame.setLocation(100, 100);
        frame.setSize((int) (screenSize.getWidth() * defaultScreenPortionW),
                (int) (screenSize.getHeight() * defaultScreenPortionH));
        frame.setVisible(true);
        */

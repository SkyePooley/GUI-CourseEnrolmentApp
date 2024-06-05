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
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class View extends JFrame implements Observer {
    private final double defaultScreenPortionW = 0.4;
    private final double defaultScreenPortionH = 0.5;

    // keep references to panels here
    private AddRemovePanel addRemovePanel;
    private SelectionPanel selectionPanel;
    private CourseDescriptionPanel courseDescriptionPanel;
    private StreamSelectionPanel streamSelectionPanel;
    private BottomPanel saveRevertPanel;

    // keep the GUI objects here
    private JTable scheduleTable;
    
    public View() {
        // initialise GUI elements
        setTitle("Course Enrolment Application");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        setLocation(100, 100);
        setSize((int) (screenSize.getWidth() * defaultScreenPortionW), (int) (screenSize.getHeight() * defaultScreenPortionH));
        
        //adding components
        JPanel leftPanel = new JPanel();
        
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        
        this.addRemovePanel = new AddRemovePanel();
        this.selectionPanel = new SelectionPanel();
        this.courseDescriptionPanel = new CourseDescriptionPanel();
        this.streamSelectionPanel = new StreamSelectionPanel();
        leftPanel.add(addRemovePanel);
        leftPanel.add(selectionPanel);
        leftPanel.add(courseDescriptionPanel);
        leftPanel.add(streamSelectionPanel);
        
        add(leftPanel, BorderLayout.WEST);
        
        scheduleTable = new JTable(15, 5); // 15 time slots, 5 days for columns
        JScrollPane scrollPane = new JScrollPane(scheduleTable);
        add(scrollPane, BorderLayout.CENTER);

        saveRevertPanel = new BottomPanel();
        add(saveRevertPanel, BorderLayout.SOUTH);
        
        setVisible(true);
    }

    //

    /**
     * Adds the given action listener to all gui elements of this panel
     * and to all contained panels.
     * @param listener ActionListener object, preferably the Controller
     * @author Skye Pooley
     */
    public void addActionListener(ActionListener listener) {
        // add to interactives in panels
        selectionPanel.addActionListener(listener);
        streamSelectionPanel.addActionListener(listener);
        saveRevertPanel.addActionListener(listener);
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
            if (flags.streamClashUpdate)    { this.streamSelectionPanel.update(model); }
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
        selectionPanel.update(model.eligibleCourseCodes);
    }

    private void updateCourseDetails(DBModel model) {
        // After a course was selected from the dropdown menu the model has prepared into on it
        // Update the course info panel
        courseDescriptionPanel.update(model.getSelectedCourse());
        // show the stream options for this course
        streamSelectionPanel.refresh(model);
    }
}


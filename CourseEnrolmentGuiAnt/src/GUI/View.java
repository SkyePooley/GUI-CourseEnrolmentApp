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
    private final double defaultScreenPortionW = 0.7;
    private final double defaultScreenPortionH = 0.7;

    // keep references to panels here
    private final RemovePanel removeCoursePanel;
    private final JPanel addCoursePanel;
    private final SelectionPanel selectionPanel;
    private final CourseDescriptionPanel courseDescriptionPanel;
    private final StreamSelectionPanel streamSelectionPanel;
    private final SchedulePanel schedulePanel;
    private final SaveRevertPanel saveRevertPanel;
    private final JTabbedPane tabbedPane;

    // Popup windows
    final LoginView loginPopup;
    
    public View() {
        // initialise window
        setTitle("Course Enrolment Application");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        setLocation(100, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize((int) (screenSize.getWidth() * defaultScreenPortionW), (int) (screenSize.getHeight() * defaultScreenPortionH));
        
        //Components for adding new enrolments
        this.addCoursePanel = new JPanel();
        addCoursePanel.setLayout(new BoxLayout(addCoursePanel, BoxLayout.Y_AXIS));
        addCoursePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.selectionPanel = new SelectionPanel();
        this.courseDescriptionPanel = new CourseDescriptionPanel();
        this.streamSelectionPanel = new StreamSelectionPanel();
        addCoursePanel.add(selectionPanel);
        addCoursePanel.add(courseDescriptionPanel);
        addCoursePanel.add(streamSelectionPanel);
        addCoursePanel.add(Box.createVerticalStrut(10));
        addCoursePanel.add(Box.createVerticalStrut(10));

        // Components for removing enrolments
        this.removeCoursePanel = new RemovePanel();
        this.removeCoursePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Tabs to switch between adding and removing enrolments
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Add Enrolment", addCoursePanel);
        tabbedPane.addTab("Remove Enrolment", removeCoursePanel);
        Dimension maxSize = new Dimension((int) (this.getWidth() * 0.2), this.getHeight());
        tabbedPane.setPreferredSize(maxSize);
        add(tabbedPane, BorderLayout.WEST);

        // Calendar view for visualising enrolments
        this.schedulePanel = new SchedulePanel();
        add(schedulePanel, BorderLayout.CENTER);

        // Buttons to either revert changes or save to db
        saveRevertPanel = new SaveRevertPanel();
        saveRevertPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(saveRevertPanel, BorderLayout.SOUTH);

        this.loginPopup = new LoginView(this);
    }


    /**
     * Adds the given action listener to all gui elements of this panel
     * and to all contained panels.
     * @param listener ActionListener object, preferably the Controller
     * @author Skye Pooley
     */
    public void addActionListener(ActionListener listener) {
        selectionPanel.addActionListener(listener);
        streamSelectionPanel.addActionListener(listener);
        saveRevertPanel.addActionListener(listener);
        removeCoursePanel.addActionListener(listener);
        loginPopup.addActionListener(listener);
        schedulePanel.addActionListener(listener);
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

            if (flags.loginSuccess)         { this.loginSuccess(model); }
            if (flags.scheduleUpdate)       { this.updateEnrolments(model); }
            if (flags.courseDropdownUpdate) { this.selectionPanel.update(model); }
            if (flags.courseSelected)       { this.updateCourseDetails(model); }
            if (flags.streamClashUpdate)    { this.streamSelectionPanel.update(model); }
        }
    }

    public void openLoginPopup() {
        loginPopup.setVisible(true);
    }

    /**
     * Called after a user successfully logs in.
     * Completes setup of the main window, disposes of the login popup, and shows the main window.
     * @param model Retrieve information for window setup
     * @author Skye Pooley
     */
    private void loginSuccess(DBModel model) {
        // login was successful and the model has data on the user now.
        // close the login popup and show the main interface.
        // The model will also have data on the schedule but this will be a separate method.
        System.out.println("login success");
        schedulePanel.update(model);    // display users enrolments on schedule
        selectionPanel.update(model);   // update the available course dropdown
        saveRevertPanel.update(model);  // display the user's name on the bottom row
        removeCoursePanel.update(model);// display user's current enrolments in dropdown
        loginPopup.dispose();
        this.setVisible(true);
    }


    private void updateCourseDetails(DBModel model) {
        // After a course was selected from the dropdown menu the model has prepared into on it
        // Update the course info panel
        courseDescriptionPanel.update(model);
        // show the stream options for this course
        streamSelectionPanel.refresh(model);
    }

    private void updateEnrolments(DBModel model) {
        schedulePanel.update(model);
        removeCoursePanel.update(model);
    }
}


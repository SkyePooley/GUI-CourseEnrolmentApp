/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import Model.DBModel;
import Model.UpdateFlags;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Skye Pooley and Clayton Roberts
 */
public class View implements Observer {
    private final double defaultScreenPortionW = 0.5;
    private final double defaultScreenPortionH = 0.7;

    // keep the GUI objects here

    public View() {
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
}

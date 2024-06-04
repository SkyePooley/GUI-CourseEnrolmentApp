package Model;

/**
 * Passed with observer update from the model.
 * Holds flags which tell the view which panels to update
 * @author Skye Pooley
 */
public class UpdateFlags {
    // The whole Gui needs to be updated
    public boolean fullReset = false;

    // Given login details did not match an existing student
    public boolean loginFail = false;
    // Login was successful, student data available
    public boolean loginSuccess = false;

    // refresh the schedule
    public boolean scheduleUpdate = false;

    // new courses available for course dropdown after semester selection
    public boolean courseDropdownUpdate = false;
    // into for the selected course is available for the selection panel
    public boolean courseSelected = false;

    public boolean streamClashUpdate = false;
}

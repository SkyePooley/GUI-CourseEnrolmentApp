package GUI;

import Model.DBModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Translation layer between the View and Model.
 * Catches ActionEvents from the View and calls corresponding method in model.
 * @author Skye Pooley & Clayton Roberts
 */
public class Controller implements ActionListener {
    DBModel model;
    View view;

    Controller() {
        System.out.println("Controller Constructed");
    }

    /**
     * Captures an event from the view(s) and then decides what to do with it based on ActionCommands
     * @param event the event to be processed
     * @author Skye Pooley
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        System.out.print("Action caught in controller: ");
        System.out.println(event.getActionCommand());

        switch (event.getActionCommand()) {
            case "Login":
                model.login(view.loginPopup.getUsername());
                break;
            case "Semester Selected":
                model.updateEligibleCourses(Integer.parseInt(getComboOption(event.getSource())));
                break;
            case "Course Selected":
                model.updateSelectedCourse(getComboOption(event.getSource()));
                break;
            case "Stream selected":
                String option = getComboOption(event.getSource());
                if (option != null)
                    model.updateSelectedStream(Integer.parseInt(option));
                break;
            case "Add to Schedule":
                model.addNewEnrolment();
                break;
            case "Remove Course Selected":
                model.updateEnrolmentToRemove(getComboOption(event.getSource()));
                break;
            case "Remove Enrolment":
                model.removeSelectedEnrolment();
                break;
            case "Revert Changes":
                model.revertChanges();
                break;
            case "Confirm and Save":
                model.confirmEnrolments();
                break;
            default:
                System.out.println("Captured ActionEvent has no corresponding method in model. Unfinished feature?");
        }
    }

    /**
     * Get the option selected from a combo box
     * @param eventSource ComboBox from the view
     * @return A string containing the selected option, empty string if the source was not a comboBox
     * @author Skye Pooley
     */
    private String getComboOption(Object eventSource) {
        if (!(eventSource instanceof JComboBox)) { return ""; }
        JComboBox<String> source = (JComboBox) eventSource;
        return (String) source.getSelectedItem();
    }

    public void addModel(DBModel model) {
        this.model = model;
        System.out.println("Model added to controller");
    }

    public void addView(View view) {
        this.view = view;
        view.addActionListener(this);
        System.out.println("View added to controller");
    }
}

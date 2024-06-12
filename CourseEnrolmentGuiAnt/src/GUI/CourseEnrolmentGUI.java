/**
 * Application for students to add and remove enrollments from their schedules.
 * @author Skye Pooley & Clayton Roberts
 */
package GUI;

import Model.DBModel;

public class CourseEnrolmentGUI {
    DBModel model;

    /**
     * Main to make our GUI run and execute
     * @author Clayton Roberts, Skye Pooley
     */
    public static void main(String[] args) {
        CourseEnrolmentGUI gui = new CourseEnrolmentGUI();
    }

    public CourseEnrolmentGUI() {
        model = new DBModel();

        View mainWindow = new View();
        model.addObserver(mainWindow);
        model.addObserver(mainWindow.loginPopup);

        Controller controller = new Controller();
        controller.addModel(model);
        controller.addView(mainWindow);

        mainWindow.openLoginPopup();
    }
}

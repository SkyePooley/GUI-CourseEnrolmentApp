/**
 * @author Skye Pooley & Clayton Roberts
 */
package GUI;

import Model.DBModel;

public class CourseEnrolmentGUI {
    DBModel model;

    /**
     * Main to make our GUI run and execute
     * @author Clayton Roberts
     */
    public static void main(String[] args) {
        CourseEnrolmentGUI gui = new CourseEnrolmentGUI();
    }

    public CourseEnrolmentGUI() {
        model = new DBModel();

        View mainWindow = new View();
        model.addObserver(mainWindow);

        Controller controller = new Controller();
        controller.addModel(model);
        controller.addView(mainWindow);
        controller.initModel();

        mainWindow.openLoginPopup();
    }
}

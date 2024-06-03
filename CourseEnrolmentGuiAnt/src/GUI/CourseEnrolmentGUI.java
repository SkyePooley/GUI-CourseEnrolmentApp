
package GUI;

import Model.DBModel;

/**
 *
 * @author Skye Pooley
 */
public class CourseEnrolmentGUI {
    DBModel model;

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
    }
}

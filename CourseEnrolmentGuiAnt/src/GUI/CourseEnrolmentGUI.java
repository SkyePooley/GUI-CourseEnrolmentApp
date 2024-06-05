/**
 * @author Skye Pooley & Clayton Roberts
 */
package GUI;

import Model.DBModel;

public class CourseEnrolmentGUI {
    DBModel model;

    public static void main(String[] args) {
        CourseEnrolmentGUI gui = new CourseEnrolmentGUI();
    }

    public CourseEnrolmentGUI() {
        LoginController loginController = new LoginController(null);
        loginController.showLoginDialog();

        if (loginController.isLoggedIn()) {
        model = new DBModel();
        View mainWindow = new View();
        model.addObserver(mainWindow);


        Controller controller = new Controller();
        controller.addModel(model);
        controller.addView(mainWindow);
        controller.initModel();
        } else {
            System.out.println("Auchentication failed");
            System.exit(0);
        }
    }
}

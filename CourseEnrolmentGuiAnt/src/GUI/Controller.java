package GUI;

import Model.DBModel;
import GUI.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Skye Pooley
 */
public class Controller implements ActionListener {
    DBModel model;
    View view;

    Controller() {
        System.out.println("Controller Constructed");
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        System.out.println("Action caught in controller");
        Object sourse = event.getSource();
        
        if (sourse == view.getAddToScheduleButton())
        {
        }else if (sourse == view.getRevertChangesButton())
        {
        } else if (sourse == view.getConfirmAndSaveButton()) {
            
        }
        // call something in the model
    }

    public void addModel(DBModel model) {
        this.model = model;
        System.out.println("Model added to controller");
    }

    public void addView(View view) {
        this.view = view;
        view.getAddToScheduleButton().addActionListener(this);
        view.getRevertChangesButton().addActionListener(this);
        view.getConfirmAndSaveButton().addActionListener(this);
        System.out.println("View added to controller");
    }

    public void initModel() {
        // set up some initial value in the model
    }
}

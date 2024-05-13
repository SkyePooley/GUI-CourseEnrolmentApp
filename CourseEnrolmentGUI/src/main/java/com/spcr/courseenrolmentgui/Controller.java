package com.spcr.courseenrolmentgui;

import com.spcr.courseenrolmentgui.model.DBModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author wmpoo
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
        // call something in the model
    }

    public void addModel(DBModel model) {
        this.model = model;
        System.out.println("Model added to controller");
    }

    public void addView(View view) {
        this.view = view;
        System.out.println("View added to controller");
    }

    public void initModel() {
        // set up some initial value in the model
    }
}

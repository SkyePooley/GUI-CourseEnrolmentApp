/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.spcr.courseenrolmentgui;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author wmpoo
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

    @Override
    public void update(Observable model, Object obj) {
        // update the data in the gui based on model data and passed object.
        // might be able to pass in different objects and detect them using instanceOf
    }


}

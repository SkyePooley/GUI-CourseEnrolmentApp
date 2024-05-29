/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Scanner;

/**
 *
 * @author Skye 
 */
public class DBModel extends Observable {
    private DBManager dbManager;
    private CourseCollectionManager courses;
    private Student student;
    
    public DBModel() {
        this.dbManager = DBManager.getDBManager();
        courses = new CourseCollectionManager(dbManager);
    }
    
    public static void main(String[] args) {
        DBModel model = new DBModel();
        System.out.println(model.courses);

        System.out.println(model.login("22179237"));
        System.out.println(model.student);

        //dbManager.closeConnections();
    }

    public void refresh() {
        courses = new CourseCollectionManager(dbManager);
        // refresh student
    }

    public boolean login(String studentId) {
        try {
            this.student = Student.getStudent(studentId, dbManager);
            return student != null;
        } catch (SQLException e) {
            System.out.println("Something went wrong while attempting student login with ID " + studentId);
            e.printStackTrace();
        }
        return false;
    }
}

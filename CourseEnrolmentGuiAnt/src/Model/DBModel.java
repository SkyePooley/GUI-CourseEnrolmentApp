/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Observable;
import java.util.Scanner;

/**
 *
 * @author Skye 
 */
public class DBModel extends Observable {
    private DBManager dbManager;
    private CourseCollectionManager courses;
    
    public DBModel() {
        this.dbManager = DBManager.getDBManager();
        courses = new CourseCollectionManager(dbManager);
    }
    
    public static void main(String[] args) {
        DBManager dbManager = DBManager.getDBManager();;

        CourseCollectionManager courses = new CourseCollectionManager(dbManager);
        System.out.println(courses);

        dbManager.closeConnections();
    }

    public void refresh() {
        courses = new CourseCollectionManager(dbManager);
        // refresh student
    }
}

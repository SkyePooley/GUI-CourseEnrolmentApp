/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Scanner;

/**
 *
 * @author Skye Pooley
 */
public class DBModel extends Observable {
    private DBManager dbManager;
    private CourseCollectionManager courses;
    private Student student;
    public LinkedList<String> eligibleCourseCodes;
    private int selectedSemester = 1;
    private Course selectedCourse;
    
    public DBModel() {
        this.dbManager = DBManager.getDBManager();
        courses = new CourseCollectionManager(dbManager);
        this.login("22179237");
    }
    
    public static void main(String[] args) {
        DBModel model = new DBModel();
        System.out.println(model.courses);

        model.login("22179237");
        System.out.println(model.student);

        //dbManager.closeConnections();
    }

    public void refresh() {
        courses = new CourseCollectionManager(dbManager);
        // refresh student
    }

    public void login(String studentId) {
        UpdateFlags update = new UpdateFlags();
        try {
            this.student = Student.getStudent(studentId, dbManager);
            if (student != null) { update.loginSuccess = true; } else { update.loginFail = true; }
            this.setChanged();
            notifyObservers(update);
        } catch (SQLException e) {
            System.out.println("Something went wrong while attempting student login with ID " + studentId);
            e.printStackTrace();
        }
    }

    public void updateEligibleCourses(int semester) {
        this.selectedSemester = semester;
        this.eligibleCourseCodes = courses.getEligibleCourseCodes(student, selectedSemester);
        UpdateFlags flags = new UpdateFlags();
        flags.courseDropdownUpdate = true;
        this.setChanged();
        notifyObservers(flags);
    }

    public void updateSelectedCourse(String courseCode) {
        this.selectedCourse = courses.getCourse(courseCode);
        if (selectedCourse == null) {
            System.out.println("There was an invalid course code selected. This really shouldn't happen, what did you do?");
            return;
        }

        UpdateFlags flags = new UpdateFlags();
        flags.courseSelected = true;
        this.setChanged();
        notifyObservers(flags);
    }

    public Course getSelectedCourse() {
        return this.selectedCourse;
    }
}

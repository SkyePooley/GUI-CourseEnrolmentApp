package com.spcr.courseenrolmentgui.model;

import java.util.ArrayList;

/**
 * Little javabean class to store all the information on a course
 * Data to be filled in by the database manager
 * Not to be used raw, wrap in CourseManager first.
 * @author Skye Pooley
 */
class Course {
    String code;
    String longName;
    String description;
    int level;
    int points;
    float efts;
    ArrayList<Timetable> timetables;

    public Course(String code) {
        this.code = code;
    }
}

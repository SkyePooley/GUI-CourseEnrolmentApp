package com.spcr.courseenrolmentgui.model;

import java.util.HashMap;

public class CourseCollectionManager {
    private final HashMap<String, CourseManager> courses;

    public CourseCollectionManager(HashMap<String, CourseManager> courses) {
        this.courses = courses;
    }

    public boolean containsCourse(String code) {
        return courses.containsKey(code);
    }

    public boolean containsCourse(CourseManager course) {
        return courses.containsValue(course);
    }

    public CourseManager getCourse(String code) {
        return courses.get(code);
    }

    // TODO get eligible courses
}

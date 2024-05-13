package com.spcr.courseenrolmentgui.model;


import java.util.ArrayList;

/**
 * Manages read-only access to a Course object
 * @author Skye Pooley
 */
public class CourseManager {
    private final Course course;

    public CourseManager(Course course) {
        this.course = course;
    }

    /**
     * Compare given object with instance.
     * @param o Any object, return will be more meaningful if types match.
     * @return True if given object is a CourseManager and has a course with the same code.
     * @author Skye Pooley
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) { return false; }
        if (!(o instanceof CourseManager)) { return false; }
        return this.getCode().equals( ((CourseManager) o).getCode() );
    }

    @Override
    public String toString() {
        return this.getCode();
    }

    public String getCode() {
        return course.code;
    }

    public String getName() {
        return course.longName;
    }

    public String getDescription() {
        return course.description;
    }

    public int getLevel() {
        return course.level;
    }

    public int getPoints() {
        return course.points;
    }

    public float getEfts() {
        return course.efts;
    }

    public ArrayList<Timetable> getTimetables() {
        return course.timetables;
    }

    public Timetable getTimetable(int index) {
        return course.timetables.get(index);
    }
}


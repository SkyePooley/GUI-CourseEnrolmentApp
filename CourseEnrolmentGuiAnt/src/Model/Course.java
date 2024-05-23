package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    private Course(String code) {
        this.code = code;
    }

    public static Course getCourse(ResultSet rs) throws SQLException {
        Course newCourse       = new Course(rs.getString("CODE"));
        newCourse.longName     = rs.getString("COURSENAME");
        newCourse.description  = rs.getString("DESCRIPTION");
        newCourse.level        = rs.getInt("LEVEL");
        newCourse.points       = rs.getInt("POINTS");
        newCourse.efts         = (float) rs.getDouble("EFTS");

        //TODO Add timetables, prerequisites

        return newCourse;
    }
}

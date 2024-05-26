package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Little pojo to store all the information on a course
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
    LinkedList<Timetable> timetables;

    private Course(String code) {
        this.code = code;
    }

    public static Course getCourse(ResultSet courseRS, ResultSet timetableRS) throws SQLException {
        if (!courseRS.next()) { return null; }

        Course newCourse       = new Course(courseRS.getString("CODE"));
        newCourse.longName     = courseRS.getString("COURSENAME");
        newCourse.description  = courseRS.getString("DESCRIPTION");
        newCourse.level        = courseRS.getInt("LEVEL");
        newCourse.points       = courseRS.getInt("POINTS");
        newCourse.efts         = (float) courseRS.getDouble("EFTS");

        //TODO Add prerequisites
        newCourse.timetables = new LinkedList<>();
        while (timetableRS.next()) {
            newCourse.timetables.add(new Timetable(timetableRS));
        }

        return newCourse;
    }
}

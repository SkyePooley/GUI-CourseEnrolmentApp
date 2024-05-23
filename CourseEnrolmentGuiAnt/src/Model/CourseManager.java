package Model;


import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Manages read-only access to a Course object
 * @author Skye Pooley
 */
public class CourseManager {
    private final Course course;

    /**
     * Construct a CourseManager from a pre-existing Course object
     * @param course Course to be wrapped
     */
    public CourseManager(Course course) {
        this.course = course;
    }

    /**
     * Construct a CourseManager by creating a new Course from given ResultSet
     * @param rs ResultSet containing one row from the Course DB table
     * @throws SQLException If the given resultset does not map correctly then an SQLException will be thrown.
     */
    public CourseManager(ResultSet rs) throws SQLException {
        this.course = Course.getCourse(rs);
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
        StringBuilder output = new StringBuilder();
        output.append(this.getCode());
        output.append(" - ");
        output.append(this.getName());
        return output.toString();
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


package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class CourseCollectionManager {
    private final HashMap<String, CourseManager> courses;

    /**
     * Construct a CourseCollectionManager from pre-existing course collection
     * @param courses Mapping of course codes to courseManagers
     */
    public CourseCollectionManager(HashMap<String, CourseManager> courses) {
        this.courses = courses;
    }

    /**
     * Construct a new CourseCollectionManager from a given database connection
     * @param dbManager Connection to EnrolmentDatabase
     */
    public CourseCollectionManager(DBManager dbManager) {
        HashMap<String, CourseManager> courses = new HashMap<>(30);

        try (ResultSet coursesDbSet = dbManager.query("SELECT * FROM COURSE")) {
            while (coursesDbSet.next()) {
                CourseManager course = new CourseManager(coursesDbSet);
                courses.put(course.getCode(), course);
            }

        } catch (SQLException e) {
            System.out.println("Reading in courses from DB failed");
            throw new RuntimeException(e);
        }

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

    /**
     * Get a string of all contained course codes and names, one course per line
     * @return Formatted string
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (CourseManager course : courses.values()) {
            output.append(course.toString());
            output.append("\n");
        }
        return output.toString();
    }

    // TODO get eligible courses
}

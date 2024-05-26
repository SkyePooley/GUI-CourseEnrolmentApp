package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashSet;

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
        LinkedHashSet<String> courseCodes = new LinkedHashSet<>();

        // Get a set containing all the course codes in the COURSE db table
        try (ResultSet courseCodesQuery = dbManager.query("SELECT \"CODE\" FROM COURSE")) {
            while (courseCodesQuery.next()) {
                courseCodes.add(courseCodesQuery.getString("CODE"));
            }
        } catch (SQLException e) {
            System.out.println("Attempting to read course codes from COURSE failed:");
            throw new RuntimeException(e);
        }

        // Iterate through course codes and construct a CourseManager for each
        for (String courseCode : courseCodes) {
            try {
                // Row matching course code
                ResultSet courseQuery = dbManager.query("SELECT * FROM COURSE " +
                        "WHERE \"CODE\" = '" + courseCode + "'");
                // Row(s) matching course code
                ResultSet timetableQuery = dbManager.query("SELECT * FROM TIMETABLE " +
                        "WHERE \"CourseCode\" = '" + courseCode + "'");

                courses.put(courseCode, new CourseManager(courseQuery, timetableQuery));

            } catch (SQLException e) {
                System.out.println("Reading in " + courseCode + " from DB failed");
                e.printStackTrace();
            }
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

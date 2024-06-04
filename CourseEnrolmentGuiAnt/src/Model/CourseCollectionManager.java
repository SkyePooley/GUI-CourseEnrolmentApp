package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;

/**
 * Holds a collection of all courses on the database, provides helper methods to sort through them all.
 * @author Skye Pooley
 */
public class CourseCollectionManager {
    private final HashMap<String, Course> allCourses;
    private final HashMap<String, Course> semOneCourses;
    private final HashMap<String, Course> semTwoCourses;
    DBManager dbManager;

    /**
     * Construct a new CourseCollectionManager from a given database connection
     * @param dbManager Connection to EnrolmentDatabase
     */
    public CourseCollectionManager(DBManager dbManager) {
        this.dbManager = dbManager;
        this.allCourses = getAllCourses();
        semOneCourses = new HashMap<>(allCourses.size());
        semTwoCourses = new HashMap<>(allCourses.size());

        for (Course course : allCourses.values()) {
            if (course.hasSemOne()) { semOneCourses.put(course.getCode(), course); }
            if (course.hasSemTwo()) { semTwoCourses.put(course.getCode(), course); }
        }
    }

    /**
     * Gets a list of course codes based on the given query condition.
     * @param queryCondition The 'WHERE' clause of an SQL query in form "WHERE condition [AND condition]"
     * @return Set containing course codes
     */
    private LinkedHashSet<String> getCourseCodesFromQuery(String queryCondition) {
        LinkedHashSet<String> courseCodes = new LinkedHashSet<>();

        // Get a set containing all the course codes matching the condition
        try (ResultSet courseCodesQuery = dbManager.query("SELECT \"CODE\" FROM COURSE " + queryCondition)) {
            while (courseCodesQuery.next()) {
                courseCodes.add(courseCodesQuery.getString("CODE"));
            }
        } catch (SQLException e) {
            System.out.println("Attempting to read course codes from COURSE with condition " + queryCondition + " failed:");
            e.printStackTrace();
        }

        return courseCodes;
    }

    /**
     * Constructs a mapping of courses which match the given SQL condition
     * @param queryCondition The 'WHERE' clause of an SQL query in form "WHERE condition [AND condition]"
     * @return Mapping of course codes to course objects
     */
    private HashMap<String, Course> getCoursesFromQuery(String queryCondition) {
        HashMap<String, Course> courses = new HashMap<>(30);
        LinkedHashSet<String> courseCodes = getCourseCodesFromQuery(queryCondition);

        // Iterate through course codes and construct a Course object for each
        for (String courseCode : courseCodes) {
            try {
                // Row matching course code
                ResultSet courseQuery = dbManager.query("SELECT * FROM COURSE " +
                        "WHERE \"CODE\" = '" + courseCode + "'");
                // Row(s) matching course code
                ResultSet timetableQuery = dbManager.query("SELECT * FROM TIMETABLE " +
                        "WHERE \"CourseCode\" = '" + courseCode + "'");
                ResultSet prerequisiteQuery = dbManager.query("SELECT * FROM PREREQUISITE " +
                        "WHERE \"Dependant\" = '" + courseCode + "'");

                courses.put(courseCode, new Course(courseQuery, timetableQuery, prerequisiteQuery));

            } catch (SQLException e) {
                System.out.println("Reading in " + courseCode + " from DB failed");
                e.printStackTrace();
            }
        }

        return courses;
    }

    /**
     * Get a mapping of all courses which exist on the database.
     * If the courses have previously been read from the DB then this will return the existing mapping O(1)
     * If this is the first time courses have been requested then they will need to be read in from DB O(n)
     * @return Mapping of course codes to course objects.
     */
    public HashMap<String, Course> getAllCourses() {
        if (allCourses != null) { return allCourses; }
        return getCoursesFromQuery("");
    }

    public boolean containsCourse(String code) {
        return allCourses.containsKey(code);
    }

    public boolean containsCourse(Course course) {
        return allCourses.containsValue(course);
    }

    /**
     * Get the course which has this code.
     * @param code 7-digit course code
     * @return Course object matching given code or null if there was no match.
     */
    public Course getCourse(String code) {
        return allCourses.get(code);
    }

    /**
     * Get a list of course codes for which the given student has met all prerequisites.
     * @param student To reference previous enrolments
     * @param semester Filtered by semester
     * @return LinkedList contaning 7-digit course codes. Returns null on illegal argument, empty list if no eligible courses
     */
    public LinkedList<String> getEligibleCourseCodes(Student student, int semester) {
        if (semester < 1 || 2 < semester) { return null; }
        LinkedList<String> eligibleCourseCodes = new LinkedList<>();

        // iterate all courses in given semester
        for (Course course : (semester==1 ? semOneCourses.values() : semTwoCourses.values()) ) {
            boolean prerequisitesMet = true;
            // check all prerequisites
            for (String prerequisite : course.getPrerequisiteCodes()) {
                if (!student.previousEnrolments.contains(prerequisite)) {
                    prerequisitesMet = false;
                    break;
                }
            }
            if (prerequisitesMet) {
                eligibleCourseCodes.add(course.getCode());
            }
        }

        return eligibleCourseCodes;
    }

    /**
     * Get a string of all contained course codes and names, one course per line
     * @return Formatted string
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (Course course : allCourses.values()) {
            output.append(course.toString());
            output.append("\n\n   *------*\n\n");
        }
        return output.toString();
    }

    public HashMap<String, Course> getSemOneCourses() {
        return semOneCourses;
    }

    public HashMap<String, Course> getSemTwoCourses() {
        return semTwoCourses;
    }

    // TODO get eligible courses
}

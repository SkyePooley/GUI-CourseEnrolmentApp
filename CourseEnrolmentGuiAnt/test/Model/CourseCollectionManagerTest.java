package Model;

import java.util.HashSet;
import java.util.LinkedList;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Skye Pooley
 */
public class CourseCollectionManagerTest {
    DBManager dbm;
    CourseCollectionManager ccm;
    Student testStudent;
    
    public CourseCollectionManagerTest() {
        dbm = DBManager.getDBManager();
        ccm = new CourseCollectionManager(dbm);
    }

    /**
     * Test of getAllCourses method, of class CourseCollectionManager.
     * Ensures that course collection manager reads in all the rows of the course
     * table error free.
     */
    @Test
    public void testGetAllCourses() {
        System.out.println("getAllCourses");
        int rowCount = 0;
        try {
            ResultSet courseRows = dbm.query("SELECT * FROM COURSE");
            while (courseRows.next()) {
                rowCount++;
            }
        }
        catch (SQLException e) {
            fail("SQL exception while attempting to count rows in course table");
        }
        
        assertEquals(rowCount, ccm.getAllCourses().size());
    }

    /**
     * Test of getCourse method, of class CourseCollectionManager.
     */
    @Test
    public void testGetCourse() {
        System.out.println("getCourse");
        String code = "COMP500";

        Course result = ccm.getCourse(code);
        assertEquals(code, result.getCode());
    }

    /**
     * Test of getEligibleCourseCodes method, of class CourseCollectionManager.
     */
    @Test
    public void testGetEligibleCourseCodes() {
        System.out.println("getEligibleCourseCodes");
        try {
            testStudent = Student.getStudent("admin", dbm);
        } catch (SQLException e) {
            fail("Unable to create test student");
        }

        HashSet<String> expected = new HashSet<>(6);
        expected.add("COMP719");
        expected.add("COMP500");
        expected.add("COMP501");
        expected.add("DIGD507");
        expected.add("MATH503");
        LinkedList<String> result = ccm.getEligibleCourseCodes(testStudent, 1);

        assertEquals("Expected eligible courses and resulting courses for " +
                "semester one differ in length", expected.size(), result.size());
        for (String courseCode  : result) {
            if (!expected.contains(courseCode)) {
                fail("Resulting course " + courseCode + " was not expected!");
            }
        }
    }
}

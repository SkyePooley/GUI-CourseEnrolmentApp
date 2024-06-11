package Model;

import org.junit.Test;

import java.sql.SQLException;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class StudentTest {
    DBManager database;
    CourseCollectionManager courses;
    Student student;
    public StudentTest() {
        database = DBManager.getDBManager();
        courses = new CourseCollectionManager(database);
        try {
            student = Student.getStudent("first", database);
        } catch (SQLException e) {
            fail("SQL Error while attempting to read student from database.");
        }
    }

    @Test
    public void getStudent() {
        try {
            Student testStudent = Student.getStudent("first", database);
            assertNotNull("Student not null", testStudent);
            assertEquals("Student loaded with correct name", testStudent.getFullName(), "First Year Demo");
        } catch (SQLException e) {
            fail("SQL Error while attempting to read test student from database.");
        }
    }

    @Test
    public void addTempEnrolment() {
        String testCode = "COMP508";
        Course newCourse = courses.getCourse(testCode);
        LinkedList<Timetable> timetables = newCourse.getSemOneTimetables();

        student.addTempEnrolment(newCourse.getCode(), timetables.get(1));
        assertTrue("Enrolment added", student.getEnrolmentsBySemester(1).containsKey(testCode));
    }

    @Test
    public void rollbackChanges() {
        String testCode = "COMP508";
        Course newCourse = courses.getCourse(testCode);
        LinkedList<Timetable> timetables = newCourse.getSemOneTimetables();

        student.addTempEnrolment(newCourse.getCode(), timetables.get(1));
        try {
            student.rollbackChanges(database);
            assertFalse("Enrolment Removed on rollback", student.getEnrolmentsBySemester(1).containsKey(testCode));
        } catch (SQLException e) {
            fail("SQL error while attempting to rollback changes using database");
        }
    }
}
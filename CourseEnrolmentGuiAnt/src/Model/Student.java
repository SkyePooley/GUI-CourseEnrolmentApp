package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.HashSet;

/**
 * Holds basic information on a student as well as all their enrolments.
 * Provides methods to modify enrolments
 * @author Skye Pooley
 */
class Student {
    private final String studentId;
    private final String firstName;
    private final String lastName;
    protected HashSet<String> previousEnrolments;
    protected HashSet<Enrolment> currentEnrolments;
    protected HashSet<Enrolment> tempEnrolments;

    /**
     * Construct a student from their row in the STUDENT table and a reference to the database
     * Database reference is used to fetch information on previous and current enrolments.
     * @param studentRow ResultSet containing one row from STUDENT table
     * @param dbManager instance of database manager
     * @throws SQLException This is most likely to happen if the database connection cannot be established or there is an error in the contained queries
     */
    private Student(ResultSet studentRow, DBManager dbManager) throws SQLException {
        // Get basic details
        this.studentId = studentRow.getString("StudentId");
        this.firstName = studentRow.getString("FirstName");
        this.lastName  = studentRow.getString("LastName");
        this.tempEnrolments     = new HashSet<>(4);
        this.previousEnrolments = new HashSet<>(20);
        this.currentEnrolments  = new HashSet<>(8);

        // get previous enrolments as course code strings
        ResultSet previousEnrolmentRows = dbManager.query("SELECT * FROM PAST_ENROLMENT WHERE \"StudentId\" = '" + this.getStudentId() + "'");
        while (previousEnrolmentRows.next()) {
            previousEnrolments.add(previousEnrolmentRows.getString("CourseCode"));
        }

        // get current enrolments as enrolment objects
        ResultSet currentEnrolmentRows = dbManager.query(
                "SELECT t.*\n" +
                "FROM CURRENT_ENROLMENT e, TIMETABLE t\n" +
                "WHERE e.\"StudentId\" = '22179237'\n" +
                "AND e.\"TimetableId\" = t.\"TimetableID\"");
        while (currentEnrolmentRows.next()) {
            Timetable timetable = new Timetable(currentEnrolmentRows);
            currentEnrolments.add(new Enrolment(currentEnrolmentRows.getString("CourseCode"), timetable));
        }
    }

    /**
     * Get a student object from a student ID and reference to the database.
     * Checks that the student exists on record and returns null if not.
     * @param studentId String 8 characters long
     * @param dbManager Ref to database manager
     * @return Student if exists, null otherwise
     * @throws SQLException This really shouldn't happen, what did you do? Maybe the ID was in the wrong format.
     */
    public static Student getStudent(String studentId, DBManager dbManager) throws SQLException {
        ResultSet studentRow = dbManager.query("SELECT * FROM STUDENT WHERE \"StudentId\" = '" + studentId + "'");
        if (!studentRow.next()) { return null; }
        return new Student(studentRow, dbManager);
    }

    /**
     * Shift all temporary enrolments into the current enrolments, clear temp enrolments.
     */
    public void confirmEnrolments() {
        currentEnrolments.addAll(tempEnrolments);
        tempEnrolments.clear();
    }

    /**
     * String containing name, ID, previous and current enrolments.
     * @return Formatted string.
     */
    @Override
    public String toString() {
        String output = this.getFirstName() + " " + this.getLastName() + " " + this.getStudentId();
        output += "\nPrevious Enrolments:";
        for (String prevEnrolment : previousEnrolments) {
            output += "\n" + prevEnrolment;
        }
        output += "\nCurrent Enrolments:\n";
        for (Enrolment currentEnrolment : currentEnrolments) {
            output += currentEnrolment.toString();
        }
        output += "\nTemp Enrolments:\n";
        for (Enrolment tempEnrolmenr : tempEnrolments) {
            output += tempEnrolmenr.toString();
        }

        return output;
    }

    /**
     * Check to see if a new timetable addition will clash with existing enrolments
     * @param timetable New timetable to check
     * @return True if there is a clash, false otherwise.
     * @author Skye Pooley
     */
    public boolean checkForClash(Timetable timetable) {
        for (Enrolment enrolment : currentEnrolments) {
            if (enrolment.getSemester() == timetable.getSemester())
                if (enrolment.getTimetable().checkForClash(timetable))
                    return true;
        }
        for (Enrolment enrolment : tempEnrolments) {
            if (enrolment.getSemester() == timetable.getSemester())
                if (enrolment.getTimetable().checkForClash(timetable))
                    return true;
        }
        return false;
    }

    /**
     *
     * @return First plus last name
     */
    public String getFullName() {
        return this.getFirstName() + " " + this.getLastName();
    }

    public String getStudentId() {
        return studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean addTempEnrolment(Enrolment newEnrolment) {
        return this.tempEnrolments.add(newEnrolment);
    }

    public boolean removeTempEnrolment(Enrolment enrolment) {
        return this.tempEnrolments.remove(enrolment);
    }

    public HashSet<Enrolment> getCurrentEnrolments() {
        return this.currentEnrolments;
    }

    public HashSet<Enrolment> getTempEnrolments() {
        return this.tempEnrolments;
    }
}

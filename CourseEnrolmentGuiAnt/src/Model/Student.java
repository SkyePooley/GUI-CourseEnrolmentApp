package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

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
    protected HashMap<String, Timetable> currentEnrolments;
    protected HashMap<String, Timetable> tempEnrolments;

    /**
     * Construct a student from their row in the STUDENT table and a reference to the database.
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
        this.tempEnrolments     = new HashMap<>(8);
        this.previousEnrolments = new HashSet<>(20);
        this.currentEnrolments  = new HashMap<>(8);

        // get previous enrolments as course code strings
        ResultSet previousEnrolmentRows = dbManager.query("SELECT * FROM PAST_ENROLMENT WHERE \"StudentId\" = '" + studentId + "'");
        while (previousEnrolmentRows.next()) {
            previousEnrolments.add(previousEnrolmentRows.getString("CourseCode"));
        }

        loadCurrentEnrolments(dbManager);
    }

    /**
     * Read in the student's confirmed enrolments from the database.
     * @param dbManager Connection to database
     * @throws SQLException Error while reading in enrolments.
     */
    public void loadCurrentEnrolments(DBManager dbManager) throws SQLException {
        // get current enrolments as enrolment objects
        ResultSet currentEnrolmentRows = dbManager.query(
                "SELECT t.*\n " +
                        "FROM CURRENT_ENROLMENT e, TIMETABLE t\n " +
                        "WHERE e.\"StudentId\" = '" + studentId + "' \n" +
                        "AND e.\"TimetableId\" = t.\"TimetableID\"");
        while (currentEnrolmentRows.next()) {
            Timetable timetable = new Timetable(currentEnrolmentRows);
            String courseCode = currentEnrolmentRows.getString("CourseCode");
            currentEnrolments.put(courseCode, timetable);
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
     * Save new list of enrolments to the database
     */
    public void confirmChanges(DBManager database) {
        for (String courseCode : tempEnrolments.keySet()) {
            currentEnrolments.put(courseCode, tempEnrolments.get(courseCode));
        }

        tempEnrolments.clear();
        // admin account stays blank
        if (!studentId.equals("admin"))
            saveEnrolments(database);
    }

    /**
     * Delete the students current enrolment entries on the database, then write out all the current enrolments from application.
     * @param database Database manager to execute updates.
     */
    private void saveEnrolments(DBManager database) {
        database.update("DELETE FROM \"CURRENT_ENROLMENT\" WHERE \"StudentId\" = '" + studentId + "'");
        LinkedList<String> commands = new LinkedList<>();
        for (String courseCode : currentEnrolments.keySet()) {
            commands.add("INSERT INTO \"CURRENT_ENROLMENT\" VALUES ('" + studentId + "', '" + courseCode + "', " + currentEnrolments.get(courseCode).tableIndex + ")");
        }
        database.updateBatch(commands);
    }

    /**
     * Rollback all changes made by the user in this session.
     * @param dbManager Database connection to reload confirmed enrolments.
     * @throws SQLException Error while reading in enrolments.
     */
    public void rollbackChanges(DBManager dbManager) throws SQLException {
        this.tempEnrolments.clear();
        this.currentEnrolments.clear();
        this.loadCurrentEnrolments(dbManager);
    }

    /**
     * Removes the enrolment matching the given course code.
     * @param courseCode Course code matching enrolment.
     */
    public void removeEnrolment(String courseCode) {
        if (tempEnrolments.remove(courseCode) != null) { return; }
        if (currentEnrolments.remove(courseCode) != null) { return; }
        System.out.println("Attempted to remove an enrolment which does not exist.");
    }

    /**
     * String containing name, ID, previous and current enrolments.
     * @return Formatted string.
     */
    @Override
    public String toString() {
        String output = firstName + " " + lastName + " " + studentId;
        output += "\nPrevious Enrolments:";
        for (String prevEnrolment : previousEnrolments) {
            output += "\n" + prevEnrolment;
        }
        output += "\nCurrent Enrolments:\n";
        for (String courseCode : currentEnrolments.keySet()) {
            output += courseCode;
        }
        output += "\nTemp Enrolments:\n";
        for (String courseCode : tempEnrolments.keySet()) {
            output += courseCode;
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
        // Loop through the existing enrolments and check whether any of their timetables conflict with the new timetable
        for (String courseCode : currentEnrolments.keySet()) {
            Timetable existingTimetable = currentEnrolments.get(courseCode);
            if (existingTimetable.getSemester() == timetable.getSemester())
                if (existingTimetable.checkForClash(timetable))
                    return true;
        }
        for (String courseCode : tempEnrolments.keySet()) {
            Timetable existingTimetable = tempEnrolments.get(courseCode);
            if (existingTimetable.getSemester() == timetable.getSemester())
                if (existingTimetable.checkForClash(timetable))
                    return true;
        }
        return false;
    }

    public void addTempEnrolment(String courseCode, Timetable timetable) {
        this.tempEnrolments.put(courseCode, timetable);
    }

    /**
     *
     * @return First plus last name
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    private HashMap<String, Timetable> getAllEnrolments() {
        HashMap<String, Timetable> collatedEnrolments = new HashMap<>();

        for (String courseCode : currentEnrolments.keySet()) {
            collatedEnrolments.put(courseCode, currentEnrolments.get(courseCode));
        }
        for (String courseCode : tempEnrolments.keySet()) {
            collatedEnrolments.put(courseCode, tempEnrolments.get(courseCode));
        }

        return collatedEnrolments;
    }

    /**
     * Get all the student's enrolments which have timetables in the given semester.
     * @param semester int 1 or 2
     * @return Mapping of course codes to timetables representing enrolments, or null if semester is invalid.
     */
    public HashMap<String, Timetable> getEnrolmentsBySemester(int semester) {
        if (semester < 1 || semester > 2) { return null; }
        HashMap<String, Timetable> allEnrolments = this.getAllEnrolments();
        HashMap<String, Timetable> filteredEnrolments = new HashMap<>();

        for (String courseCode : allEnrolments.keySet()) {
            Timetable timetable = allEnrolments.get(courseCode);
            if (timetable.getSemester() == semester)
                filteredEnrolments.put(courseCode, timetable);
        }

        return filteredEnrolments;
    }
}

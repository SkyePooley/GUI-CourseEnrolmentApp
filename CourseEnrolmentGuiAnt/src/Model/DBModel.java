/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Observable;

/**
 * Manages all data held by the application and provides methods for the controller to manipulate this data.
 * @author Skye Pooley
 */
public class DBModel extends Observable {
    private final DBManager dbManager;
    private final CourseCollectionManager courses;
    private Student student;

    // info for the view
    public LinkedList<String> eligibleCourseCodes;
    private int selectedSemester = 1;
    private Course selectedCourse;
    private int selectedStream;
    private Timetable selectedTimetable;
    public boolean streamClash = false;
    public String enrolmentToRemove;
    
    public DBModel() {
        this.dbManager = DBManager.getDBManager();
        courses = new CourseCollectionManager(dbManager);
    }

    /**
     * Attempt to log in a user with the given studentID
     * Updates View on whether login was successful.
     * @param studentId Student ID entered by user. Matched to user entry in database. Limited to 8 char length.
     * @author Skye Pooley
     */
    public void login(String studentId) {
        System.out.println("login attempt " + studentId);
        UpdateFlags update = new UpdateFlags();
        try {
            this.student = Student.getStudent(studentId, dbManager);
            if (student != null) {
                update.loginSuccess = true;
                updateEligibleCourses(1);
            }
            else {
                update.loginFail = true;
            }

            this.setChanged();
            notifyObservers(update);

        } catch (SQLException e) {
            System.out.println("Something went wrong while looking up student login with ID " + studentId);
            e.printStackTrace();
        }
    }

    /**
     * Called when a semester is selected on the GUI or a user logs in.
     * Updates which courses are available based on semester timetables and prerequisites met by student.
     * @param semester Integer value 1 or 2
     * @author Skye Pooley
     * @see Student
     * @see GUI.SchedulePanel
     */
    public void updateEligibleCourses(int semester) {
        this.selectedSemester = semester;
        this.eligibleCourseCodes = courses.getEligibleCourseCodes(student, selectedSemester);
        UpdateFlags flags = new UpdateFlags();
        flags.courseDropdownUpdate = true;
        flags.scheduleUpdate = true;
        this.setChanged();
        notifyObservers(flags);
    }

    /**
     * Called from the controller when a course is selected from the gui dropdown menu
     * Selects this course in the model and provides details to the view
     * @param courseCode 7-digit code corresponding to course object
     * @see GUI.SelectionPanel
     * @see Course
     * @see CourseCollectionManager
     */
    public void updateSelectedCourse(String courseCode) {
        this.selectedCourse = courses.getCourse(courseCode);
        if (selectedCourse == null) {
            System.out.println("\"" + courseCode + "\" is an invalid course code");
            return;
        }

        UpdateFlags flags = new UpdateFlags();
        flags.courseSelected = true;
        this.setChanged();
        notifyObservers(flags);
    }

    /**
     * Check whether the selected stream would cause a schedule clash
     * @param streamIndex Option from the stream dropdown menu.
     * @author Skye Pooley
     * @see Timetable
     */
    public void updateSelectedStream(int streamIndex) {
        selectedStream = streamIndex-1; // menu starts at one, data structure starts at zero
        LinkedList<Timetable> timetables = selectedSemester == 1 ? selectedCourse.getSemOneTimetables() : selectedCourse.getSemTwoTimetables();
        streamClash = student.checkForClash(timetables.get(selectedStream));
        selectedTimetable = timetables.get(selectedStream);
        System.out.println(selectedCourse.getCode() + " Stream " + streamIndex + " Clash " + streamClash);

        UpdateFlags flags = new UpdateFlags();
        flags.streamClashUpdate = true;
        this.setChanged();
        notifyObservers(flags);
    }


    /**
     * Take the selected enrolment and add it onto the students schedule as a temporary enrolment.
     * Prompts the view to update schedule.
     * @author Skye Pooley
     * @see GUI.SelectionPanel
     * @see GUI.StreamSelectionPanel
     * @see Student
     */
    public void addNewEnrolment() {
        if (streamClash) { return; }
        student.addTempEnrolment(selectedCourse.getCode(), selectedTimetable);
        System.out.println(student);

        this.setChanged();
        UpdateFlags flags = new UpdateFlags();
        flags.scheduleUpdate=true;
        notifyObservers(flags);
    }


    /**
     * Updated the model on which course code is selected in the enrolment removal dropdown menu.
     * @param courseCode Course code read from enrolment removal dropdown.
     * @author Skye Pooley
     * @see GUI.RemovePanel
     */
    public void updateEnrolmentToRemove(String courseCode) {
        this.enrolmentToRemove = courseCode;
    }

    /**
     * Removes enrolment matching the option selected in the enrolment removal dropdown menu.
     * Depends on updates from updateEnrolmentToRemove()
     * Prompts update to schedule view.
     * @author Skye Pooley
     * @see GUI.RemovePanel
     * @see Student
     */
    public void removeSelectedEnrolment() {
        student.removeEnrolment(enrolmentToRemove);

        this.setChanged();
        UpdateFlags flags = new UpdateFlags();
        flags.scheduleUpdate=true;
        notifyObservers(flags);
    }

    /**
     * Revert all changes made by the user since they loaded in or since they confirmed changes.
     * Deletes all temporary enrolments and reloads student data from the database.
     * @author Skye Pooley
     * @see GUI.SaveRevertPanel
     * @see Student
     */
    public void revertChanges() {
        try {
            student.rollbackChanges(dbManager);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.setChanged();
        UpdateFlags flags = new UpdateFlags();
        flags.scheduleUpdate=true;
        flags.courseDropdownUpdate=true;
        notifyObservers(flags);
    }

    /**
     * Save enrolment changes to the database.
     * Prompts schedule update.
     * @author Skye Pooley
     * @see GUI.SaveRevertPanel
     * @see Model.Student
     */
    public void confirmEnrolments() {
        student.confirmChanges(dbManager);
        UpdateFlags flags = new UpdateFlags();
        flags.scheduleUpdate = true;
        setChanged();
        notifyObservers(flags);
    }

    /**
     * Return the students current enrolments from the selected semester.
     * @return HashSet containing enrolments
     * @see GUI.SchedulePanel
     * @see GUI.RemovePanel
     */
    public HashMap<String, Timetable> getCurrentSemesterEnrolments() {
        return student.getEnrolmentsBySemester(selectedSemester);
    }

    /**
     * Get the course object matching the course selected in the enrolment add menu.
     * @return Course object containing description and timetables of chosen course
     * @see GUI.CourseDescriptionPanel
     */
    public Course getSelectedCourse() {
        return this.selectedCourse;
    }

    /**
     * Get the number of streams available for this course in this semester
     * @return int number of streams
     * @see GUI.StreamSelectionPanel
     */
    public int getNumberOfStreams() {
        if (selectedSemester == 1) {
            return selectedCourse.getSemOneTimetables().size();
        }
        return selectedCourse.getSemTwoTimetables().size();
    }

    /**
     * Get name of user.
     * @return First and Last name of logged-in user separated by space.
     * @see GUI.SaveRevertPanel
     */
    public String getUserFullName() {
        return student.getFullName();
    }
}

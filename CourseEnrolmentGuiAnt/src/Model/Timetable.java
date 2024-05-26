/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;

/**
 * Timetable of classes for a course, each course may have many of these.
 * All timetables must have a lecture session, labs and tutorials are optional.
 * @author Skye Pooley
 */
public class Timetable {
    private final CalendarEvent lecture;
    private CalendarEvent lab;
    private CalendarEvent tutorial;
    private final int semester;

    /**
     * Construct a course timetable from database query result
     * @param rs Resultset of TIMETABLE row
     * @throws SQLException if resultset cannot be mapped to timetable object
     */
    public Timetable(ResultSet rs) throws SQLException {
        this.semester = rs.getInt("Semester");
        this.lecture = CalendarEvent.getCalendarEvent(
                DayOfWeek.valueOf(rs.getString("LectureDay")),
                rs.getInt("LectureTime"),
                rs.getInt("LectureDuration")
        );
        // Not all courses will have labs or tutorials, only add if existing.
        if (rs.getString("LabDay") != null) {
            this.lab = CalendarEvent.getCalendarEvent(
                    DayOfWeek.valueOf(rs.getString("LabDay")),
                    rs.getInt("LabTime"),
                    rs.getInt("LabDuration")
            );
        }
        if (rs.getString("TutorialDay") != null) {
            this.tutorial = CalendarEvent.getCalendarEvent(
                    DayOfWeek.valueOf(rs.getString("TutorialDay")),
                    rs.getInt("TutorialTime"),
                    rs.getInt("TutorialDuration")
            );
        }
    }

    /**
     * Create a new set of timetable events for a course.
     * Lecture session is mandatory and set in constructor.
     * @param lectureDay String matching weekday in java.time.DayOfWeek
     * @param lectureHour int 0-23 representing the hour that the event starts in 24 hour time
     * @param lectureDuration int 1-23 representing the number of hours event runs for
     * @see java.time.DayOfWeek
     * @author Skye Pooley
     */
    public Timetable(String lectureDay, int lectureHour, int lectureDuration, int semester) {
        this.semester = semester;
        this.lecture = CalendarEvent.getCalendarEvent(DayOfWeek.valueOf(lectureDay), lectureHour, lectureDuration);
        if (this.getLecture() == null) {
            System.out.println("Invalid time or duration given" + this);
        }
    }

    /**
     * Set the lab time of this timetable.
     * Cannot overwrite an existing lab event, returns false if there was en existing lab event.
     * @param labDay String matching weekday in java.time.DayOfWeek
     * @param labHour int 0-23 representing the hour that the event starts in 24 hour time
     * @param labDuration int 1-23 representing the number of hours event runs for
     * @return true if the new event was successfully added
     */
    public boolean setLab(String labDay, int labHour, int labDuration) {
        if (this.lab != null) { return false; }
        this.lab = CalendarEvent.getCalendarEvent(DayOfWeek.valueOf(labDay), labHour, labDuration);
        return this.getLab() != null;
    }

    public boolean setTutorial(String tutorialDay, int tutorialHour, int tutorialDuration) {
        if (this.tutorial != null) { return false; }
        this.tutorial = CalendarEvent.getCalendarEvent(DayOfWeek.valueOf(tutorialDay), tutorialHour, tutorialDuration);
        return this.getTutorial() != null;
    }

    /**
     * Get string containing times of all weekly sessions in this timetable.
     * @return Formatted String
     */
    @Override
    public String toString() {
        String output = "";
        output +=  "Lecture: " + lecture.toString() + "\n";
        output += hasTutorial() ? "Tutorial: " + tutorial.toString() : "No Tutorial";
        output += "\n";
        output += hasLab()      ? "Lab: " + lab.toString() : "No Lab";
        return output;
    }

    public boolean hasTutorial() {
        return this.getTutorial() != null;
    }

    public boolean hasLab() {
        return this.getLab() != null;
    }

    public CalendarEvent getLecture() {
        return lecture;
    }

    public CalendarEvent getLab() {
        return lab;
    }

    public CalendarEvent getTutorial() {
        return tutorial;
    }

    public int getSemester() {
        return this.semester;
    }
}

package Model;

public class Enrolment {
    private final CourseManager course;
    private final Timetable timetable;

    public Enrolment(CourseManager course, Timetable timetable) {
        this.course = course;
        this.timetable = timetable;
    }

    public CourseManager getCourse() {
        return course;
    }

    public Timetable getTimetable() {
        return timetable;
    }

    public int getSemester() {
        return timetable.getSemester();
    }
}

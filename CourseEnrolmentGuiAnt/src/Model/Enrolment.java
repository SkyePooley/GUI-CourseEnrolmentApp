package Model;

public class Enrolment {
    private final Course course;
    private final Timetable timetable;

    public Enrolment(Course course, Timetable timetable) {
        this.course = course;
        this.timetable = timetable;
    }

    public Course getCourse() {
        return course;
    }

    public Timetable getTimetable() {
        return timetable;
    }

    public int getSemester() {
        return timetable.getSemester();
    }
}

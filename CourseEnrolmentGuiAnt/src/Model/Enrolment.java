package Model;

public class Enrolment {
    private final String course;
    private final Timetable timetable;

    public Enrolment(String course, Timetable timetable) {
        this.course = course;
        this.timetable = timetable;
    }

    @Override
    public String toString() {
        String output = course + "\n";
        output += timetable.toString();
        output += "\n";
        return output;
    }

    public String getCourse() {
        return course;
    }

    public Timetable getTimetable() {
        return timetable;
    }

    public int getSemester() {
        return timetable.getSemester();
    }
}

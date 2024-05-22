package Model;

import java.util.HashSet;

public class StudentManager {
    private final Student student;

    public StudentManager(Student student) {
        this.student = student;
    }

    public String getStudentId() {
        return student.studentId;
    }

    public String getFirstName() {
        return student.firstName;
    }

    public String getLastName() {
        return student.lastName;
    }

    public HashSet<Enrolment> getCurrentEnrolments() {
        return student.currentEnrolments;
    }

    public HashSet<Enrolment> getPreviousEnrolments() {
        return student.previousEnrolments;
    }

    public HashSet<Enrolment> getTempEnrolments() {
        return student.tempEnrolments;
    }

    public boolean addTempEnrolment(Enrolment newEnrolment) {
        return student.tempEnrolments.add(newEnrolment);
    }

    public void removeTempEnrolment(Enrolment enrolment)  {
        student.tempEnrolments.remove(enrolment);
    }

    public void clearTempEnrolments() {
        student.tempEnrolments.clear();
    }

    public void confirmTempEnrolments() {
        student.currentEnrolments.addAll(student.tempEnrolments);
        clearTempEnrolments();
    }
}

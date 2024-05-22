package Model;

import java.util.HashSet;

class Student {
    String studentId;
    String firstName;
    String lastName;
    HashSet<Enrolment> previousEnrolments;
    HashSet<Enrolment> currentEnrolments;
    HashSet<Enrolment> tempEnrolments;
}

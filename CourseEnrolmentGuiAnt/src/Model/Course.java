package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Holds basic info on a course as well as its available timetables and prerequisites.
 * @author Skye Pooley
 */
public class Course {
    private final String code;
    private final String longName;
    private final String description;
    private final int level;
    private final int points;
    private final float efts;
    private final LinkedList<Timetable> semOneTimetables;
    private final LinkedList<Timetable> semTwoTimetables;
    private boolean hasSemOne;
    private boolean hasSemTwo;
    private final LinkedList<String> prerequisiteCodes;

    public Course(ResultSet courseRS, ResultSet timetableRS, ResultSet prerequisiteRS) throws SQLException {
        if (!courseRS.next()) { throw new SQLException(); }

        // get basic info
        this.code         = courseRS.getString("CODE");
        this.longName     = courseRS.getString("COURSENAME");
        this.description  = courseRS.getString("DESCRIPTION");
        this.level        = courseRS.getInt("LEVEL");
        this.points       = courseRS.getInt("POINTS");
        this.efts         = (float) courseRS.getDouble("EFTS");
        hasSemOne = false;
        hasSemTwo = false;

        // get timetables
        this.semOneTimetables = new LinkedList<>();
        this.semTwoTimetables = new LinkedList<>();
        while (timetableRS.next()) {
            Timetable newTimetable = new Timetable(timetableRS);

            if (newTimetable.getSemester() == 1) {
                hasSemOne = true;
                this.getSemOneTimetables().add(newTimetable);
            }
            if (newTimetable.getSemester() == 2) {
                hasSemTwo = true;
                this.getSemTwoTimetables().add(newTimetable);
            }
        }

        // get prerequisites
        this.prerequisiteCodes = new LinkedList<>();
        while (prerequisiteRS.next()) {
            getPrerequisiteCodes().add(prerequisiteRS.getString("Dependency"));
        }
    }

    /**
     * Compare given object with instance.
     * @param o Any object, return will be more meaningful if given object is instance of Course.
     * @return True if given object is a Course with the same code.
     * @author Skye Pooley
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) { return false; }
        if (!(o instanceof Course)) { return false; }
        return this.getCode().equals( ((Course) o).getCode() );
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append(this.getName());
        output.append("\n\n");
        output.append(this.description);

        output.append("\n\n");

        if (this.getSemOneTimetables().size() > 0) {
            output.append("\nSemester One Timetables: ");
            for (Timetable t : this.getSemOneTimetables()) {
                output.append("\n");
                output.append(t.toString());
                output.append("\n");
            }
        }
        if (this.getSemTwoTimetables().size() > 0) {
            output.append("\n\nSemester Two Timetables: ");
            for (Timetable t : this.getSemTwoTimetables()) {
                output.append("\n");
                output.append(t.toString());
                output.append("\n");
            }
        }


        if (!this.getPrerequisiteCodes().isEmpty()) {
            output.append("\n\nPrerequisites: ");
            for (String code : getPrerequisiteCodes()) {
                output.append(code);
                output.append(", ");
            }
        }
        return output.toString();
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.longName;
    }

    public String getDescription() {
        return this.description;
    }

    public int getLevel() {
        return this.level;
    }

    public int getPoints() {
        return this.points;
    }

    public float getEfts() {
        return this.efts;
    }

    public boolean hasSemOne() {
        return hasSemOne;
    }

    public boolean hasSemTwo() {
        return hasSemTwo;
    }

    public LinkedList<String> getPrerequisiteCodes() {
        return prerequisiteCodes;
    }

    public LinkedList<Timetable> getSemOneTimetables() {
        return semOneTimetables;
    }

    public LinkedList<Timetable> getSemTwoTimetables() {
        return semTwoTimetables;
    }
}

package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author Skye Pooley
 */
class Course {
    private final String code;
    private final String longName;
    private final String description;
    private final int level;
    private final int points;
    private final float efts;
    private final LinkedList<Timetable> timetables;

    public Course(ResultSet courseRS, ResultSet timetableRS) throws SQLException {
        if (!courseRS.next()) { throw new SQLException(); }

        this.code         = courseRS.getString("CODE");
        this.longName     = courseRS.getString("COURSENAME");
        this.description  = courseRS.getString("DESCRIPTION");
        this.level        = courseRS.getInt("LEVEL");
        this.points       = courseRS.getInt("POINTS");
        this.efts         = (float) courseRS.getDouble("EFTS");

        //TODO Add prerequisites
        this.timetables = new LinkedList<>();
        while (timetableRS.next()) {
            this.timetables.add(new Timetable(timetableRS));
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
        output.append(this.getCode());
        output.append(" - ");
        output.append(this.getName());
        if (this.timetables.size() > 0) {
            output.append("\nAvailable Timetables: ");
            for (Timetable t : this.getTimetables()) {
                output.append("\n");
                output.append(t.toString());
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

    public LinkedList<Timetable> getTimetables() {
        return this.timetables;
    }

    public Timetable getTimetable(int index) {
        return this.timetables.get(index);
    }
}

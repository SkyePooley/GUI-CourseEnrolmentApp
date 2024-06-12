package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class to provide logging of changes by users.
 * This is mostly here to fulfil the requirement of three database writes.
 * @author Skye Pooley
 */
public class Logger {
    private final DBManager database;

    public Logger(DBManager database) {
        this.database = database;
    }

    /**
     * Log the change made by user to database table for reference.
     * Item is saved alongside date and time stamp
     * @param studentId Student ID to record which account changes were made to
     * @param change Summary of change made. Limit 255 chars
     */
    public void logChange(String studentId, String change) {
        if (change.length() > 255) { return; }
        LocalDateTime time = LocalDateTime.now();
        String formattedTime = time.format(DateTimeFormatter.ISO_DATE_TIME);
        database.update("INSERT INTO \"CHANGE_LOGS\" VALUES ('" + studentId + "', '" + formattedTime + "', '" + change + "')");
    }
}

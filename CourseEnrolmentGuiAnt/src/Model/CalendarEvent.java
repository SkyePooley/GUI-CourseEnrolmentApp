package Model;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * Event to be displayed on the GUI calendar.
 * Includes a day of the week, starting hour, and duration.
 * Intended to be used for events which repeat each week for a full semester.
 * @author Skye Pooley
 */
public class CalendarEvent {
    private final DayOfWeek eventDay;
    private final int eventHour;
    private final int eventDuration;

    /**
     * Get a new CalendarEvent. Checks that given hour is a valid time and the duration is less than 24 hours.
     * @param eventDay Day of the week event occurs (recurring)
     * @param eventHour Hour of the day event begins. Given in 24 hour time
     * @param eventDuration Number of hours event runs from beginning
     * @return CalendarEvent with given timings if times are valid, null otherwise.
     */
    public static CalendarEvent getCalendarEvent(DayOfWeek eventDay, int eventHour, int eventDuration) {
        if (eventHour > 22 || 0 > eventHour) { return null; }
        if (eventDuration < 1 || eventDuration > 8) { return null; }
        return new CalendarEvent(eventDay, eventHour, eventDuration);
    }

    /**
     * Create a new CalendarEvent
     * @param eventDay Day of the week event occurs (recurring)
     * @param eventHour Hour of the day event begins. Given in 24 hour time
     * @param eventDuration Number of hours event runs from beginning
     * @author Skye Pooley
     */
    private CalendarEvent(DayOfWeek eventDay, int eventHour, int eventDuration) {
        this.eventDay = eventDay;
        this.eventHour = eventHour;
        this.eventDuration = eventDuration;
    }

    @Override
    public String toString() {
        String output = eventDay.getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " ";
        output += eventHour + ":00 - ";
        output += eventHour+eventDuration + ":00";
        return output;
    }

    /**
     * Given a calendar event, check if the two events overlap.
     * @param event Calendar event to check against
     * @return True if the events overlap, false otherwise.
     */
    public boolean checkForClash(CalendarEvent event) {
        if (event == null) { return false; }
        // does the beginning of the given event fall inside this event
        boolean startClash = (event.getEventHour() >= this.eventHour && event.getEventHour() <= this.getEndHour());
        // does the ending of the given event fall inside this event
        boolean endClash   = (event.getEndHour() >= this.eventHour   && event.getEndHour() <= this.getEndHour());
        return startClash || endClash;
    }

    public int getEventDuration() {
        return eventDuration;
    }

    public int getEventHour() {
        return eventHour;
    }

    /**
     * Get the hour during which the event finishes.
     * If an event runs from 10am and goes for three hours then this will return 12.
     * @return Integer representing hour in 24 hour time.
     */
    public int getEndHour() {
        return eventHour + eventDuration - 1;
    }

    public DayOfWeek getEventDay() {
        return eventDay;
    }
}


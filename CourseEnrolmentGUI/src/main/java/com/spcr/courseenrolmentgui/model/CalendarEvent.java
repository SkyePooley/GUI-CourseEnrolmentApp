package com.spcr.courseenrolmentgui.model;

import java.time.DayOfWeek;

/**
 * Event to be displayed on the GUI calendar.
 * Includes a day of the week, starting hour, and duration.
 * Intended to be used for events which repeat each week for a full semester.
 */
public class CalendarEvent {
    private DayOfWeek eventDay;
    private int eventHour;
    private int eventDuration;

    /**
     * Get a new CalendarEvent. Checks that given hour is a valid time and the duration is less than 24 hours.
     * @param eventDay Day of the week event occurs (recurring)
     * @param eventHour Hour of the day event begins. Given in 24 hour time
     * @param eventDuration Number of hours event runs from beginning
     * @return CalendarEvent with given timings if times are valid, null otherwise.
     */
    public static CalendarEvent getCalendarEvent(DayOfWeek eventDay, int eventHour, int eventDuration) {
        if (eventHour > 23 || 0 > eventHour) { return null; }
        if (eventDuration < 1 || eventDuration > 24) { return null; }
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

    public int getEventDuration() {
        return eventDuration;
    }

    public int getEventHour() {
        return eventHour;
    }

    public DayOfWeek getEventDay() {
        return eventDay;
    }
}


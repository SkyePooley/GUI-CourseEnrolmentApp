/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.spcr.courseenrolmentgui.model;

import java.time.DayOfWeek;

/**
 * Timetable of classes for a course, each course may have many of these.
 * All timetables must have a lecture session, labs and tutorials are optional.
 * @author Skye Pooley
 */
class Timetable {
    private CalendarEvent lecture;
    private CalendarEvent lab;
    private CalendarEvent tutorial;
    private int semester;

    /**
     * Create a new set of timetable events for a course.
     * Lecture session is mandatory and set in constructor.
     * @param lectureDay String matching weekday in java.time.DayOfWeek
     * @param lectureHour int 0-23 representing the hour that the event starts in 24 hour time
     * @param lectureDuration int 1-23 representing the number of hours event runs for
     * @see java.time.DayOfWeek
     * @author Skye Pooley
     */
    public Timetable(String lectureDay, int lectureHour, int lectureDuration, int semester) {
        this.semester = semester;
        this.lecture = CalendarEvent.getCalendarEvent(DayOfWeek.valueOf(lectureDay), lectureHour, lectureDuration);
        if (this.getLecture() == null) {
            System.out.println("Invalid time or duration given" + this);
        }
    }

    public boolean setLab(String labDay, int labHour, int labDuration) {
        this.lab = CalendarEvent.getCalendarEvent(DayOfWeek.valueOf(labDay), labHour, labDuration);
        return this.getLab() != null;
    }

    public boolean setTutorial(String tutorialDay, int tutorialHour, int tutorialDuration) {
        this.tutorial = CalendarEvent.getCalendarEvent(DayOfWeek.valueOf(tutorialDay), tutorialHour, tutorialDuration);
        return this.getTutorial() != null;
    }

    public boolean hasTutorial() {
        return this.getTutorial() != null;
    }

    public boolean hasLab() {
        return this.getLab() != null;
    }

    public CalendarEvent getLecture() {
        return lecture;
    }

    public CalendarEvent getLab() {
        return lab;
    }

    public CalendarEvent getTutorial() {
        return tutorial;
    }

    public int getSemester() {
        return this.semester;
    }
}

package fr.unice.polytech.shop.timesheet;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

public class Timesheet {
    HashMap<DayOfWeek, DaySchedule> daySchedules;

    public Timesheet() {
        this.daySchedules = new HashMap<>();
    }

    public Timesheet(HashMap<DayOfWeek, DaySchedule> daySchedules) {
        this.daySchedules = daySchedules;
    }


    /**
     * Set the opening hours for a day
     * @param day
     * @param from
     * @param to
     */

    public void setDaySchedule(DayOfWeek day, LocalTime from, LocalTime to) {
        daySchedules.put(day, new DaySchedule(from, to));
    }

    /**
     * Set the opening hours for a list of days of the week
     * @param day
     * @param from
     * @param to
     */
    public void setDaysSchedule(List<DayOfWeek> days, LocalTime from, LocalTime to) {
        for (DayOfWeek value : days) {
            setDaySchedule(value, from, to);
        }
    }

    public DaySchedule getDaySchedule(DayOfWeek day) {
        if(daySchedules.containsKey(day)) return daySchedules.get(day);
        return null;
    }

    public HashMap<DayOfWeek, DaySchedule> getDaySchedules() {
        return new HashMap<>(daySchedules);
    }
}

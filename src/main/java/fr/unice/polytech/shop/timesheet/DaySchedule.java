package fr.unice.polytech.shop.timesheet;

import java.time.LocalTime;

public class DaySchedule {

    private LocalTime from;
    private LocalTime to;

    public DaySchedule(LocalTime from, LocalTime to) {
        this.from = from;
        this.to = to;
    }

    public LocalTime getFrom() {
        return from;
    }

    public LocalTime getTo() {
        return to;
    }
}

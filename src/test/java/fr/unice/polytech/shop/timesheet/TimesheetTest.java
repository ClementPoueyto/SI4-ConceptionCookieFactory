package fr.unice.polytech.shop.timesheet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TimesheetTest {

    Timesheet timesheet;

    @BeforeEach
    public void setUp() {
        timesheet = new Timesheet();
    }

    @Test
    public void setDayScheduleTest() {
        timesheet.setDaySchedule(DayOfWeek.MONDAY, LocalTime.of(9,0), LocalTime.of(18,0));
        assertEquals(LocalTime.of(9,0), timesheet.getDaySchedule(DayOfWeek.MONDAY).getFrom());
        assertEquals(LocalTime.of(18,0), timesheet.getDaySchedule(DayOfWeek.MONDAY).getTo());
    }

    @Test
    public void setDaysScheduleTest() {
        List<DayOfWeek> days = new ArrayList<>();
        days.add(DayOfWeek.TUESDAY);
        days.add(DayOfWeek.WEDNESDAY);
        days.add(DayOfWeek.THURSDAY);
        days.add(DayOfWeek.FRIDAY);
        timesheet.setDaysSchedule(days, LocalTime.of(10,0), LocalTime.of(19,0));
        for(DayOfWeek d : days) {
            assertEquals(LocalTime.of(10,0), timesheet.getDaySchedule(d).getFrom());
            assertEquals(LocalTime.of(19,0), timesheet.getDaySchedule(d).getTo());
        }
    }
}

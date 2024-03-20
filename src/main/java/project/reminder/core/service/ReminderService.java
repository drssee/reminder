package project.reminder.core.service;

import project.reminder.dto.ScheduleDto;

public interface ReminderService {

    ScheduleDto saveSchedule(ScheduleDto scheduleDto);
}

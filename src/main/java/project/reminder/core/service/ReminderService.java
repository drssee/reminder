package project.reminder.core.service;

import project.reminder.dto.ScheduleDto;
import project.reminder.dto.UsersDto;

import java.util.List;

public interface ReminderService {

    ScheduleDto saveSchedule(ScheduleDto scheduleDto);

    List<ScheduleDto> getSchedules(UsersDto usersDto);

    ScheduleDto getSchedule(Long id);
}

package project.reminder.core.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.reminder.core.dao.ScheduleRepository;
import project.reminder.core.service.ReminderService;
import project.reminder.dto.ScheduleDto;
import project.reminder.entity.Schedule;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReminderServiceImpl implements ReminderService {

    private final ScheduleRepository scheduleRepository;

    @Override
    public ScheduleDto saveSchedule(ScheduleDto scheduleDto) {

        return null;
    }

    private Schedule dtoToEntity(ScheduleDto scheduleDto) {

    }
}

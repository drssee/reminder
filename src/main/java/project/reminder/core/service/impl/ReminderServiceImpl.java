package project.reminder.core.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.reminder.common.util.TransUtil;
import project.reminder.core.dao.ScheduleRepository;
import project.reminder.core.service.ReminderService;
import project.reminder.dto.ScheduleDto;
import project.reminder.dto.UsersDto;
import project.reminder.entity.Schedule;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReminderServiceImpl implements ReminderService {

    private final ScheduleRepository scheduleRepository;

    private final TransUtil transUtil;

    @Override
    public ScheduleDto saveSchedule(ScheduleDto scheduleDto) {
        Schedule schedule = transUtil.dtoToEntity(scheduleDto, Schedule.class);
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return transUtil.entityToDto(savedSchedule, ScheduleDto.class);
    }

    @Override
    public List<ScheduleDto> getSchedules(UsersDto usersDto) {
        String userId = usersDto.getUserId();
        return scheduleRepository
                .findByUserId(userId)
                .stream()
                .map(e -> transUtil.entityToDto(e, ScheduleDto.class))
                .toList();
    }

    @Override
    public ScheduleDto getSchedule(Long id) {
        Schedule schedule = scheduleRepository
                .findById(id)
                .orElseThrow(RuntimeException::new);
        return transUtil.entityToDto(schedule, ScheduleDto.class);
    }
}

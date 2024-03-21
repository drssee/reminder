package project.reminder.core.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.reminder.common.annotation.AuthenticatedOnly;
import project.reminder.core.service.ReminderService;
import project.reminder.dto.ScheduleDto;
import project.reminder.dto.UsersDto;

import java.util.List;

@RestController
@RequestMapping("/reminder")
@RequiredArgsConstructor
public class ReminderController {

    // AuthenticationCheckInterceptor 검증 추가 수정

    // 0. 단위 테스트 작성하면서 하기!(Mockito 사용 + c,s,d 각각 따로)
    // 0-1. AuthenticationCheckInterceptor 테스트도 해야함
    // 1. 설정의 경우 따로 테이블을 만들어 일정별로 설정 저장하기
    // 2. 알리미 스케줄러 만들기
    // 2-1. 알리미 스케줄러는 별도의 큐테이블 만들어서 사용

    private final ReminderService reminderService;

    // 일정 저장
    @AuthenticatedOnly
    @PostMapping("/schedule")
    public ScheduleDto save(@RequestBody ScheduleDto scheduleDto) {
        return reminderService.saveSchedule(scheduleDto);
    }

    // 전체 일정 조회
    @AuthenticatedOnly
    @GetMapping("/schedules")
    public List<ScheduleDto> getList(@RequestBody UsersDto usersDto) {
        return reminderService.getSchedules(usersDto);
    }

    // 상세 일정 조회
    @AuthenticatedOnly
    @GetMapping("/schedules/{id}")
    public ScheduleDto getSchedule(@PathVariable Long id) {
        return reminderService.getSchedule(id);
    }
}

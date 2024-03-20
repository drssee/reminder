package project.reminder.core.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.reminder.common.annotation.AuthenticatedOnly;
import project.reminder.dto.ScheduleDto;

@Slf4j
@RestController
@RequestMapping("/reminder")
@RequiredArgsConstructor
public class ReminderController {
    // 단위 테스트 작성하면서 하기!(Mockito 사용 + c,s,d 각각 따로)

    // 1. 일정 저장시 schedule 형식으로 받아 저장하기
    // 2. 설정의 경우 따로 테이블을 만들어 일정별로 설정 저장하기
    // 3. 알리미 스케줄러 만들기

    // 일정 저장
    @AuthenticatedOnly
    @PostMapping("/save")
    public void save(@RequestBody ScheduleDto scheduleDto) {
        log.debug("scheduleDto={} ", scheduleDto);

    }

    // 전체 일정 조회

    // 상세 일정 조회
}

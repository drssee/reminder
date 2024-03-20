package project.reminder.core.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.reminder.common.annotation.AuthenticatedOnly;
import project.reminder.core.service.SendService;
import project.reminder.dto.SendMessageDto;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SendController {

    private final SendService sendService;

    @AuthenticatedOnly
    @GetMapping("/send-me")
    public ResponseEntity<HttpStatus> sendMe(@RequestBody SendMessageDto sendMessageDto) {
        sendService.sendMe(sendMessageDto.getText());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

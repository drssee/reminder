package project.reminder.core.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import project.reminder.common.annotation.AuthenticatedOnly;
import project.reminder.core.service.UserService;
import project.reminder.entity.User;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 유저 설정
    @AuthenticatedOnly
    @GetMapping("/profile")
    public User getProfile() {
        return userService.getUser();
    }
}

package project.reminder.login.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import project.reminder.common.annotation.AuthenticatedOnly;
import project.reminder.login.dto.SessionUserDto;
import project.reminder.login.service.LoginService;

/**
 * 로그인
 * 1. 카카오 로그인
 * 2. 성공시 콜백호출
 * 3. 콜백메서드 내부에서 token, user 저장
 * 4. 로그인 여부 체크는 token 사용
 */
@RestController
@RequiredArgsConstructor
//@Hidden
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public RedirectView login() {
        return loginService.login();
    }

    @GetMapping("/login-callback")
    public RedirectView loginCallback(@RequestParam("code") String code) {
        return loginService.loginCallback(code);
    }

    @GetMapping("/logout")
    public String logout() {
        return loginService.logout();
    }

    // 유저 프로필
    @AuthenticatedOnly()
    @GetMapping("/profile")
    public SessionUserDto getProfile() {
        return loginService.getUser();
    }
}

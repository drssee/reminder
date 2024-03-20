package project.reminder.login.service;

import org.springframework.web.servlet.view.RedirectView;
import project.reminder.login.dto.SessionUserDto;

public interface LoginService {
    RedirectView login();

    RedirectView loginCallback(String code);

    String logout();

    SessionUserDto getUser();
}

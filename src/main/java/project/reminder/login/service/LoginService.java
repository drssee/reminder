package project.reminder.login.service;

import org.springframework.web.servlet.view.RedirectView;

public interface LoginService {
    RedirectView login();

    RedirectView loginCallback(String code);

    String logout();
}

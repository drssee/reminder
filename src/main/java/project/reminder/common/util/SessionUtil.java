package project.reminder.common.util;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.reminder.login.dto.SessionUserDto;

@Component
@RequiredArgsConstructor
public class SessionUtil {

    private final HttpSession httpSession;

    public SessionUserDto getSessionUser() {
        return (SessionUserDto) httpSession.getAttribute("user");
    }

    public String getUserId() {
        return String.valueOf(getSessionUser().getId());
    }
}

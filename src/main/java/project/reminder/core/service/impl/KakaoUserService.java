package project.reminder.core.service.impl;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import project.reminder.common.enums.HttpConst;
import project.reminder.common.service.HttpCallService;
import project.reminder.common.transformer.Trans;
import project.reminder.core.service.UserService;
import project.reminder.entity.User;

@Service
@RequiredArgsConstructor
public class KakaoUserService implements UserService {

    private final HttpCallService httpCallService;

    private final HttpSession httpSession;

    @Value("${kakao.kakao-api-host}")
    private String KAKAO_API_HOST;

    @Override
    public User getUser() {
        if (isExistSessionUser()) {
            return (User) httpSession.getAttribute("user");
        }
        return getUserFromKakao();
    }

    private Boolean isExistSessionUser() {
        return httpSession.getAttribute("user") != null;
    }

    private User getUserFromKakao() {
        String uri = KAKAO_API_HOST + "/v2/user/me";
        String result = httpCallService.callWithToken(
                HttpConst.GET,
                uri,
                (String) httpSession.getAttribute("token")
        );
        return Trans.parseJson(result, User.class);
    }
}

package project.reminder.login.service.impl;

import com.google.gson.JsonParser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;
import project.reminder.common.enums.HttpConst;
import project.reminder.common.service.HttpCallService;
import project.reminder.common.transformer.Trans;
import project.reminder.core.service.UserService;
import project.reminder.login.service.LoginService;

@Service
@RequiredArgsConstructor
public class KakaoLoginService implements LoginService {

    private final HttpCallService httpCallService;

    private final HttpSession httpSession;

    private final UserService userService;

    @Value("${kakao.rest-api-key}")
    private String REST_API_KEY;

    @Value("${kakao.redirect-uri}")
    private String REDIRECT_URI;

    @Value("${kakao.after-login-uri}")
    private String AFTER_LOGIN_URI;

    @Value("${kakao.authorize-uri}")
    private String AUTHORIZE_URI;

    @Value("${kakao.token-uri}")
    public String TOKEN_URI;

    @Value("${kakao.client-secret}")
    private String CLIENT_SECRET;

    @Value("${kakao.kakao-api-host}")
    private String KAKAO_API_HOST;

    private String DEFAULT_SCOPE = "talk_message";

    @Override
    public RedirectView login() {
        return goKakaoOAuth();
    }

    @Override
    public RedirectView loginCallback(String code) {
        String param = "grant_type=authorization_code&client_id="+REST_API_KEY+"&redirect_uri="+REDIRECT_URI+"&client_secret="+CLIENT_SECRET+"&code="+code;
        String rtn = httpCallService.call(HttpConst.POST, TOKEN_URI, HttpConst.EMPTY, param);
        httpSession.setAttribute("token", Trans.token(rtn, new JsonParser()));
        httpSession.setAttribute("user", userService.getUser());
        return new RedirectView(AFTER_LOGIN_URI);
    }

    @Override
    public String logout() {
        String uri = KAKAO_API_HOST + "/v1/user/logout";
        return httpCallService.callWithToken(
                HttpConst.POST,
                uri,
                (String) httpSession.getAttribute("token")
        );
    }

    public RedirectView goKakaoOAuth() {
        return goKakaoOAuth(DEFAULT_SCOPE);
    }

    public RedirectView goKakaoOAuth(String scope) {

        String uri = AUTHORIZE_URI+"?redirect_uri="+REDIRECT_URI+"&response_type=code&client_id="+REST_API_KEY;
        if(!scope.isEmpty()) uri += "&scope="+scope;

        return new RedirectView(uri);
    }
}

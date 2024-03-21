package project.reminder.login.service.impl;

import com.google.gson.JsonParser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;
import project.reminder.common.enums.HttpConst;
import project.reminder.common.util.TransUtil;
import project.reminder.common.util.service.HttpCallService;
import project.reminder.login.dto.SessionUserDto;
import project.reminder.login.service.LoginService;

@Service
@RequiredArgsConstructor
public class KakaoLoginService implements LoginService {

    private final HttpCallService httpCallService;

    private final HttpSession httpSession;

    private final TransUtil transUtil;

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

    @Override
    public RedirectView login() {
        return goKakaoOAuth();
    }

    @Override
    public RedirectView loginCallback(String code) {
        String param = "grant_type=authorization_code&client_id="+REST_API_KEY+"&redirect_uri="+REDIRECT_URI+"&client_secret="+CLIENT_SECRET+"&code="+code;
        String rtn = httpCallService.call(HttpConst.POST, TOKEN_URI, HttpConst.EMPTY, param);
        httpSession.setAttribute("token", transUtil.token(rtn, new JsonParser()));
        httpSession.setAttribute("user", getUser());
        return new RedirectView(AFTER_LOGIN_URI);
    }

    @Override
    public String logout() {
        String uri = KAKAO_API_HOST + "/v1/user/logout";
        String token = (String) httpSession.getAttribute("token");
        httpSession.removeAttribute("token");
        httpSession.removeAttribute("user");
        return httpCallService.callWithToken(
                HttpConst.POST,
                uri,
                token
        );
    }

    public RedirectView goKakaoOAuth() {
        return goKakaoOAuth("");
    }

    public RedirectView goKakaoOAuth(String scope) {

        String uri = AUTHORIZE_URI+"?redirect_uri="+REDIRECT_URI+"&response_type=code&client_id="+REST_API_KEY;
        if(!scope.isEmpty()) uri += "&scope="+scope;

        return new RedirectView(uri);
    }

    @Override
    public SessionUserDto getUser() {
        String uri = KAKAO_API_HOST + "/v2/user/me";
        String token = (String) httpSession.getAttribute("token");
        String result = httpCallService.callWithToken(
                HttpConst.GET,
                uri,
                token
        );
        return transUtil.parseJson(result, SessionUserDto.class);
    }
}

package project.reminder.core.service.impl;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import project.reminder.common.annotation.AuthenticatedOnly;
import project.reminder.common.enums.HttpConst;
import project.reminder.common.util.TransUtil;
import project.reminder.common.util.service.HttpCallService;
import project.reminder.core.service.SendService;

@Service
@RequiredArgsConstructor
public class KakaoSendService implements SendService {

    private final HttpCallService httpCallService;

    private final HttpSession httpSession;

    private final TransUtil transUtil;

    @Value("${kakao.kakao-api-host}")
    private String KAKAO_API_HOST;

    @Override
    @AuthenticatedOnly
    public String sendMe(String text) {
        String uri = KAKAO_API_HOST + "/v2/api/talk/memo/default/send";
        return httpCallService.callWithToken(
                HttpConst.POST,
                uri,
                (String) httpSession.getAttribute("token"),
                transUtil.createTemplate(text)
        );
    }
}

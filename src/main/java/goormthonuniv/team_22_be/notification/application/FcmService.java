package goormthonuniv.team_22_be.notification.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import goormthonuniv.team_22_be.common.config.FcmProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class FcmService {
    private final GoogleCredentials credentials;
    private final FcmProperties props;
    private final ObjectMapper om = new ObjectMapper();
    private final RestTemplate rest = new RestTemplate();

    public void sendToToken(String token, String title, String body, Map<String,String> data) throws Exception {
        // 액세스 토큰
        String accessToken = credentials.refreshAccessToken().getTokenValue();

        // HTTP 요청 만들기 (HTTP v1 포맷)
        var payload = Map.of(
                "message", Map.of(
                        "token", token,
                        "notification", Map.of(
                                "title", title,
                                "body", body
                        ),
                        "data", data == null ? Map.of() : data
                )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(om.writeValueAsString(payload), headers);
        ResponseEntity<String> res = rest.postForEntity(props.url(), entity, String.class);

        if (!res.getStatusCode().is2xxSuccessful()) {
            throw new IllegalStateException("FCM send failed: " + res.getStatusCode() + " - " + res.getBody());
        }
    }
}
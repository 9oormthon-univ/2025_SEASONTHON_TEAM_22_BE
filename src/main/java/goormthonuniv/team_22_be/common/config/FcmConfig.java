package goormthonuniv.team_22_be.common.config;

import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.util.List;

@Configuration
@EnableConfigurationProperties(FcmProperties.class)
public class FcmConfig {

    @Bean
    public GoogleCredentials googleCredentials(FcmProperties props) throws Exception {
        // classpath: 경로 사용
        InputStream is = getClass().getClassLoader().getResourceAsStream(
                props.filePath().replace("classpath:", "")
        );
        if (is == null) throw new IllegalStateException("FCM credentials not found: " + props.filePath());
        return GoogleCredentials.fromStream(is)
                .createScoped(List.of(props.googleApi())); // cloud-platform scope
    }
}
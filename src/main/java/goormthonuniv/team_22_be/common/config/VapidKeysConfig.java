package goormthonuniv.team_22_be.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "spring.vapid")
public class VapidKeysConfig {
    private String publicKey;
    private String privateKey;
    private String subject;
}

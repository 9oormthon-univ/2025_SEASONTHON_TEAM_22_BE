package goormthonuniv.team_22_be.common.properties;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CorsProperties {

    public static List<String> ORIGINS = List.of(
            "http://localhost:3000",
            "https://slowmind.ngrok.app",
            "https://slowmind.netlify.app"
    );

    public static List<String> METHODS = List.of(
            "GET",
            "POST",
            "PUT",
            "PATCH",
            "DELETE",
            "OPTIONS"
    );

    public static List<String> HEADERS = List.of(
            "*"
    );

    public static List<String> EXPOSEDHEADERS = List.of(
            "Authorization",
            "Location"
    );
}

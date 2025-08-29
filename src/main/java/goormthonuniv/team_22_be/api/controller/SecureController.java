package goormthonuniv.team_22_be.api.controller;


import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/secure")
public class SecureController {

    @GetMapping("/me")
    public Map<String, Object> me(Authentication auth) {
        return Map.of(
                "authenticated", auth != null,
                "principal", auth != null ? auth.getName() : null
        );
    }
}
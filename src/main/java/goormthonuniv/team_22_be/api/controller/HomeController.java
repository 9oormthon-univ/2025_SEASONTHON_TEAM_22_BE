package goormthonuniv.team_22_be.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {
    @GetMapping("/")
    public ResponseEntity<Void> root() {
        return ResponseEntity.status(302)
                .header("Location", "/oauth2/authorization/google")
                .build();
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "OK");
    }
}
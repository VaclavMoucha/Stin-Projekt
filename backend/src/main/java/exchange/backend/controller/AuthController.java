package exchange.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok("Odhlášení úspěšné");
    }

    @GetMapping("/status")
    public ResponseEntity<?> status() {
        return ResponseEntity.ok("Přihlášen");
    }
}
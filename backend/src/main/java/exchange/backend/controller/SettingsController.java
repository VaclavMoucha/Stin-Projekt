package exchange.backend.controller;

import exchange.backend.dto.UserSettings;
import exchange.backend.service.SettingsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/settings")
public class SettingsController {

    private final SettingsService settingsService;

    public SettingsController(SettingsService settingsService) {
        this.settingsService = settingsService;
    }


    @GetMapping
    public ResponseEntity<?> getSettings() {
        try {
            return ResponseEntity.ok(settingsService.loadSettings());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Chyba při načítání nastavení: " + e.getMessage());
        }
    }

   
    @PutMapping
    public ResponseEntity<?> saveSettings(@RequestBody UserSettings settings) {
        try {
            settingsService.saveSettings(settings);
            return ResponseEntity.ok("Nastavení uloženo");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Chyba při ukládání nastavení: " + e.getMessage());
        }
    }
}
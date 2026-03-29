package exchange.backend.controller;

import exchange.backend.storage.LogStorage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    private final LogStorage logStorage;

    public LogController(LogStorage logStorage) {
        this.logStorage = logStorage;
    }

    @GetMapping
    public ResponseEntity<List<String>> getLogs() {
        return ResponseEntity.ok(logStorage.getLogs());
    }
}
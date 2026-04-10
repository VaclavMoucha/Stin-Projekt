package com.currencyapp.backend.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.currencyapp.backend.model.LogEntry;
import com.currencyapp.backend.repository.LogRepository;



@RestController
@RequestMapping("/api/logs")
public class LogsController {
    @Autowired
    private LogRepository logRepository;
    @GetMapping
    public List<LogEntry> getLogs() {
        return logRepository.getLogs();
    }
     @GetMapping("/file")
    public List<String> getFileLogs() throws IOException {
        return Files.readAllLines(Path.of("logs/app.log"));
    }
}

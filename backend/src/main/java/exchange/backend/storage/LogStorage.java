package exchange.backend.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Component
public class LogStorage {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Value("${app.data.log-file}")
    private String logFile;
    
    public void logError(String message) {
        String line = LocalDateTime.now().format(FORMATTER) + " ERROR " + message;
        try {
            File file = new File(logFile);
            file.getParentFile().mkdirs();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Nepodařilo se zapsat log: " + e.getMessage());
        }
    }

    
    public List<String> getLogs() {
        try {
            Path path = Path.of(logFile);
            if (!Files.exists(path)) {
                return Collections.emptyList();
            }
            return Files.readAllLines(path);
        } catch (IOException e) {
            System.err.println("Nepodařilo se přečíst logy: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
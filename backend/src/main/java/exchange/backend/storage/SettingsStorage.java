package exchange.backend.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import exchange.backend.dto.UserSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class SettingsStorage {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${app.data.settings-file}")
    private String settingsFile;

    public UserSettings load() {
        try {
            File file = new File(settingsFile);
            if (!file.exists()) {
                
                return new UserSettings("EUR", List.of("USD", "CZK", "GBP"));
            }
            return objectMapper.readValue(file, UserSettings.class);
        } catch (IOException e) {
            return new UserSettings("EUR", List.of("USD", "CZK", "GBP"));
        }
    }

    public void save(UserSettings settings) throws IOException {
        File file = new File(settingsFile);
        file.getParentFile().mkdirs(); 
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, settings);
    }
}
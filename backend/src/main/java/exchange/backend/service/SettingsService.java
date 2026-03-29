package exchange.backend.service;

import exchange.backend.dto.UserSettings;
import exchange.backend.storage.LogStorage;
import exchange.backend.storage.SettingsStorage;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SettingsService {

    private final SettingsStorage settingsStorage;
    private final LogStorage logStorage;

    public SettingsService(SettingsStorage settingsStorage, LogStorage logStorage) {
        this.settingsStorage = settingsStorage;
        this.logStorage = logStorage;
    }

    public UserSettings loadSettings() {
        return settingsStorage.load();
    }

    public void saveSettings(UserSettings settings) {
        try {
            settingsStorage.save(settings);
        } catch (IOException e) {
            logStorage.logError("saveSettings failed: " + e.getMessage());
            throw new RuntimeException("Nepodařilo se uložit nastavení", e);
        }
    }
}
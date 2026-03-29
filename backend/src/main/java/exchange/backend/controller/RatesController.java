package exchange.backend.controller;

import exchange.backend.dto.FrankfurterResponse;
import exchange.backend.dto.FrankfurterTimeSeriesResponse;
import exchange.backend.dto.UserSettings;
import exchange.backend.service.ExchangeRateService;
import exchange.backend.service.SettingsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/rates")
public class RatesController {

    private final ExchangeRateService exchangeRateService;
    private final SettingsService settingsService;

    public RatesController(ExchangeRateService exchangeRateService, SettingsService settingsService) {
        this.exchangeRateService = exchangeRateService;
        this.settingsService = settingsService;
    }

   
    @GetMapping("/latest")
    public ResponseEntity<?> getLatest() {
        try {
            UserSettings settings = settingsService.loadSettings();
            FrankfurterResponse response = exchangeRateService.getLatest(
                    settings.getBaseCurrency(),
                    settings.getSelectedCurrencies()
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Chyba při načítání kurzů: " + e.getMessage());
        }
    }

  
    @GetMapping("/history")
    public ResponseEntity<?> getHistory(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        try {
            UserSettings settings = settingsService.loadSettings();
            FrankfurterTimeSeriesResponse response = exchangeRateService.getTimeSeries(
                    settings.getBaseCurrency(),
                    settings.getSelectedCurrencies(),
                    startDate,
                    endDate
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Chyba při načítání historie: " + e.getMessage());
        }
    }


    @GetMapping("/strongest")
    public ResponseEntity<?> getStrongest() {
        try {
            UserSettings settings = settingsService.loadSettings();
            FrankfurterResponse response = exchangeRateService.getLatest(
                    settings.getBaseCurrency(),
                    settings.getSelectedCurrencies()
            );
            String strongest = exchangeRateService.getStrongest(response.getRates());
            return ResponseEntity.ok(Map.of(
                    "currency", strongest,
                    "rate", response.getRates().get(strongest)
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Chyba při výpočtu nejsilnější měny: " + e.getMessage());
        }
    }

  
    @GetMapping("/weakest")
    public ResponseEntity<?> getWeakest() {
        try {
            UserSettings settings = settingsService.loadSettings();
            FrankfurterResponse response = exchangeRateService.getLatest(
                    settings.getBaseCurrency(),
                    settings.getSelectedCurrencies()
            );
            String weakest = exchangeRateService.getWeakest(response.getRates());
            return ResponseEntity.ok(Map.of(
                    "currency", weakest,
                    "rate", response.getRates().get(weakest)
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Chyba při výpočtu nejslabší měny: " + e.getMessage());
        }
    }


    @GetMapping("/average")
    public ResponseEntity<?> getAverage(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        try {
            UserSettings settings = settingsService.loadSettings();
            Double average = exchangeRateService.getAverage(
                    settings.getBaseCurrency(),
                    settings.getSelectedCurrencies(),
                    startDate,
                    endDate
            );
            return ResponseEntity.ok(Map.of("average", average));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Chyba při výpočtu průměru: " + e.getMessage());
        }
    }
}
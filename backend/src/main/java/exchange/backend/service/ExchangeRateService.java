package exchange.backend.service;

import exchange.backend.dto.FrankfurterResponse;
import exchange.backend.dto.FrankfurterTimeSeriesResponse;

import exchange.backend.storage.LogStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class ExchangeRateService {

    private final RestTemplate restTemplate;
    private final LogStorage logStorage;

    @Value("${frankfurter.api.url}")
    private String apiUrl;

    public ExchangeRateService(RestTemplate restTemplate, LogStorage logStorage) {
        this.restTemplate = restTemplate;
        this.logStorage = logStorage;
    }

   
    public FrankfurterResponse getLatest(String base, List<String> symbols) {
        try {
            String url = apiUrl + "/latest?base=" + base + "&symbols=" + String.join(",", symbols);
            return restTemplate.getForObject(url, FrankfurterResponse.class);
        } catch (Exception e) {
            logStorage.logError("getLatest failed: " + e.getMessage());
            throw e;
        }
    }

   
    public FrankfurterTimeSeriesResponse getTimeSeries(String base, List<String> symbols, String startDate,
            String endDate) {
        try {
            String url = apiUrl + "/" + startDate + ".." + endDate
                    + "?base=" + base
                    + "&symbols=" + String.join(",", symbols);
            return restTemplate.getForObject(url, FrankfurterTimeSeriesResponse.class);
        } catch (Exception e) {
            logStorage.logError("getTimeSeries failed: " + e.getMessage());
            throw e;
        }
    }

   
    public String getStrongest(Map<String, Double> rates) {
        return rates.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

  
    public String getWeakest(Map<String, Double> rates) {
        return rates.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

  
    public Double getAverage(String base, List<String> symbols, String startDate, String endDate) {
        try {
            FrankfurterTimeSeriesResponse timeSeries = getTimeSeries(base, symbols, startDate, endDate);

            return timeSeries.getRates().values().stream()
                    .flatMap(dayRates -> dayRates.values().stream())
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(0.0);
        } catch (Exception e) {
            logStorage.logError("getAverage failed: " + e.getMessage());
            throw e;
        }
    }
}
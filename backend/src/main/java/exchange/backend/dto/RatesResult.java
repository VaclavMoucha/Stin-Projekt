package exchange.backend.dto;

import java.util.Map;

public class RatesResult {
    private String base;
    private String date;
    private Map<String, Double> rates;
    private String strongest;
    private String weakest;
    private Double average;

    public String getBase() { return base; }
    public void setBase(String base) { this.base = base; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public Map<String, Double> getRates() { return rates; }
    public void setRates(Map<String, Double> rates) { this.rates = rates; }

    public String getStrongest() { return strongest; }
    public void setStrongest(String strongest) { this.strongest = strongest; }

    public String getWeakest() { return weakest; }
    public void setWeakest(String weakest) { this.weakest = weakest; }

    public Double getAverage() { return average; }
    public void setAverage(Double average) { this.average = average; }
}
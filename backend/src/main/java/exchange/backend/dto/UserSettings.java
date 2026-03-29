package exchange.backend.dto;

import java.util.List;

public class UserSettings {
    private String baseCurrency;
    private List<String> selectedCurrencies;

    public UserSettings() {}

    public UserSettings(String baseCurrency, List<String> selectedCurrencies) {
        this.baseCurrency = baseCurrency;
        this.selectedCurrencies = selectedCurrencies;
    }

    public String getBaseCurrency() { return baseCurrency; }
    public void setBaseCurrency(String baseCurrency) { this.baseCurrency = baseCurrency; }

    public List<String> getSelectedCurrencies() { return selectedCurrencies; }
    public void setSelectedCurrencies(List<String> selectedCurrencies) { this.selectedCurrencies = selectedCurrencies; }
}
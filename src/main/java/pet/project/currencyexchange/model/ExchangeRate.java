package pet.project.currencyexchange.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExchangeRate {
    private UUID id;
    private String baseCurrencyId;
    private String targetCurrencyId;
    private Double rate;

    public ExchangeRate(String baseCurrencyId, String targetCurrencyId, Double rate) {
    }
}
package pet.project.currencyexchange.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewExchangeRatePayload {
    private Integer baseCurrencyId;
    private Integer targetCurrencyId;
    private Double rate;
}

package pet.project.currencyexchange.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExchangeRate {
    private Integer id;
    private Currency baseCurrencyId;
    private Currency targetCurrencyId;
    private Double rate;
}

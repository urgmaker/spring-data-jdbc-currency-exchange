package pet.project.currencyexchange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import pet.project.currencyexchange.model.Currency;


@Data
@AllArgsConstructor
public class ExchangeRateDto {
    private Currency baseCurrencyCode;
    private Currency targetCurrencyCode;
    private Double rate;
}

package pet.project.currencyexchange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ExchangeRateDto {
    private Integer baseCurrencyId;
    private Integer targetCurrencyId;
    private Double rate;
}

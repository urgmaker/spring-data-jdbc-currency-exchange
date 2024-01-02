package pet.project.currencyexchange.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
@Builder
public class ExchangeRateDto {
    private String baseCurrencyCode;
    private String targetCurrencyCode;
    private Double rate;
}

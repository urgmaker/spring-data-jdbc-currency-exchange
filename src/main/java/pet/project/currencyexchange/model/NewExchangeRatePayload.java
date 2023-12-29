package pet.project.currencyexchange.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class NewExchangeRatePayload {
    private UUID id;
    private Integer baseCurrencyId;
    private Integer targetCurrencyId;
    private Double rate;
}

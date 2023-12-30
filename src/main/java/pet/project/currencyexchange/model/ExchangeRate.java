package pet.project.currencyexchange.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
public class ExchangeRate {
    @Id
    private Long id;
    private Integer baseCurrencyId;
    private Integer targetCurrencyId;
    private Double rate;

    public ExchangeRate(Integer baseCurrencyId, Integer targetCurrencyId, Double rate) {
        this.baseCurrencyId = baseCurrencyId;
        this.targetCurrencyId = targetCurrencyId;
        this.rate = rate;
    }
}
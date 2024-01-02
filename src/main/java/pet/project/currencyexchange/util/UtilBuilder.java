package pet.project.currencyexchange.util;

import lombok.experimental.UtilityClass;
import pet.project.currencyexchange.dto.CurrencyDto;
import pet.project.currencyexchange.model.Currency;
import pet.project.currencyexchange.model.ExchangeRate;

@UtilityClass
public class UtilBuilder {
    public static Currency buildCurrency(CurrencyDto currencyDto) {
        return Currency.builder()
                .code(currencyDto.getCode())
                .fullName(currencyDto.getFullName())
                .sign(currencyDto.getSign())
                .build();
    }

    public static ExchangeRate buildExchangeRate(Long id, Currency baseCurrency, Currency targetCurrency, Double rate) {
        return ExchangeRate.builder()
                .id(id)
                .baseCurrency(baseCurrency)
                .targetCurrency(targetCurrency)
                .rate(rate)
                .build();
    }
}

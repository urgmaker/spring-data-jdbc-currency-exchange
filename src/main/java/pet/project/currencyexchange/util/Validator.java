package pet.project.currencyexchange.util;

import lombok.experimental.UtilityClass;
import pet.project.currencyexchange.dto.CurrencyDto;
import pet.project.currencyexchange.dto.ExchangeRateDto;

@UtilityClass
public class Validator {
    public static boolean isUploadCurrencyValid(CurrencyDto uploadCurrency) {
        return ((uploadCurrency.getCode() == null || uploadCurrency.getCode().isBlank()) &&
                (uploadCurrency.getFullName() == null || uploadCurrency.getFullName().isBlank()) &&
                (uploadCurrency.getSign() == null || uploadCurrency.getSign().isBlank()));
    }

    public static boolean isUploadExchangeRateValid(ExchangeRateDto uploadExchangeRate) {
        return ((uploadExchangeRate.getBaseCurrencyCode() == null || uploadExchangeRate.getBaseCurrencyCode().isBlank()) &&
                (uploadExchangeRate.getTargetCurrencyCode() == null || uploadExchangeRate.getTargetCurrencyCode().isBlank()) &&
                (uploadExchangeRate.getRate() == null || uploadExchangeRate.getRate() <= 0));
    }
}

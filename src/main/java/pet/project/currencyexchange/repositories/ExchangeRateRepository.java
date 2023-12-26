package pet.project.currencyexchange.repositories;

import pet.project.currencyexchange.model.ExchangeRate;

import java.util.List;
import java.util.Optional;

public interface ExchangeRateRepository {
    List<ExchangeRate> findAll();

    Optional<ExchangeRate> findByCodes(String baseCurrencyId, String targetCurrencyId);

    void save(ExchangeRate exchangeRate);
}
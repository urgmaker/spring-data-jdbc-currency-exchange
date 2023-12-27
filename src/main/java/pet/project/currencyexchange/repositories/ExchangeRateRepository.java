package pet.project.currencyexchange.repositories;

import org.springframework.stereotype.Repository;
import pet.project.currencyexchange.model.ExchangeRate;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExchangeRateRepository {
    List<ExchangeRate> findAll();

    Optional<ExchangeRate> findById(Integer baseCurrencyId, Integer targetCurrencyId);

    void save(ExchangeRate exchangeRate);
}
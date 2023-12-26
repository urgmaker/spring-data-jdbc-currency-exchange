package pet.project.currencyexchange.repositories;

import pet.project.currencyexchange.model.Currency;

import java.util.List;
import java.util.Optional;

public interface CurrencyRepository {
    List<Currency> findAll();

    void save(Currency currency);

    Optional<Currency> findByCode(String code);
}

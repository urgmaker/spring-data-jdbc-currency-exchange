package pet.project.currencyexchange.repositories;

import org.springframework.stereotype.Repository;
import pet.project.currencyexchange.model.Currency;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurrencyRepository {
    List<Currency> findAll();

    void save(Currency currency);

    Optional<Currency> findByCode(String code);
}

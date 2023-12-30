package pet.project.currencyexchange.repositories;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pet.project.currencyexchange.model.ExchangeRate;

import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends CrudRepository<ExchangeRate, Long> {
    @Query("SELECT * FROM exchange_rate WHERE base_currency_id = :base_currency_id AND target_currency_id = :target_currency_id")
    Optional<ExchangeRate> findByBaseIdAndTargetId(@Param("base_currency_id") Integer baseCurrencyId,
                                                   @Param("target_currency_id") Integer targetCurrencyId);
}
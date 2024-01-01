package pet.project.currencyexchange.repositories;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pet.project.currencyexchange.model.ExchangeRate;
import pet.project.currencyexchange.util.ExchangeRateRowMapper;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends CrudRepository<ExchangeRate, Long> {
    @Query(value = "SELECT er.id as id, er.rate as rate, " +
                   "bc.id as base_currency_id, bc.code as base_currency_code, bc.full_name as base_currency_full_name, bc.sign as base_currency_sign, " +
                   "tc.id as target_currency_id, tc.code as target_currency_code, tc.full_name as target_currency_full_name, tc.sign as target_currency_sign " +
                   "FROM exchange_rate er " +
                   "JOIN currency bc ON er.base_currency_id = bc.id " +
                   "JOIN currency tc ON er.target_currency_id = tc.id", rowMapperClass = ExchangeRateRowMapper.class)
    List<ExchangeRate> findAll();

    @Query(value = "SELECT er.id as id, er.rate as rate, " +
                   "bc.id as base_currency_id, bc.code as base_currency_code, bc.full_name as base_currency_full_name, bc.sign as base_currency_sign, " +
                   "tc.id as target_currency_id, tc.code as target_currency_code, tc.full_name as target_currency_full_name, tc.sign as target_currency_sign " +
                   "FROM exchange_rate er " +
                   "JOIN currency bc ON er.base_currency_id = bc.id " +
                   "JOIN currency tc ON er.target_currency_id = tc.id " +
                   "WHERE bc.code = :base_currency_code AND tc.code = :target_currency_code",
            rowMapperClass = ExchangeRateRowMapper.class)
    Optional<ExchangeRate> findByCodes(@Param("base_currency_code") String baseCurrencyCode,
                                       @Param("target_currency_code") String targetCurrencyCode);
}
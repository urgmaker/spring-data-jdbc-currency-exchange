package pet.project.currencyexchange.repositories;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pet.project.currencyexchange.model.Currency;
import pet.project.currencyexchange.model.ExchangeRate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public class ExchangeRateRepositoryImpl implements ExchangeRateRepository, RowMapper<ExchangeRate> {
    private final JdbcOperations jdbcOperations;

    public ExchangeRateRepositoryImpl(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<ExchangeRate> findAll() {
        return this.jdbcOperations.query("""
                select * from e_exchange_rates
                """, this);
    }

    @Override
    public Optional<ExchangeRate> findByCodes(String baseCurrencyId, String targetCurrencyId) {
        return this.jdbcOperations.query("""
                        select * from e_exchange_rates where base_currency = ? and target_currency = ?
                        """, new Object[]{baseCurrencyId, targetCurrencyId}, this)
                .stream().findFirst();
    }

    @Override
    public void save(ExchangeRate exchangeRate) {
        this.jdbcOperations.update("""
                                insert into e_exchange_rates(e_id, e_base_currency_id, e_target_currency_id, e_rate)
                        """, exchangeRate.getId(), exchangeRate.getBaseCurrencyId(),
                exchangeRate.getTargetCurrencyId(), exchangeRate.getRate());
    }

    @Override
    public ExchangeRate mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ExchangeRate(
                rs.getObject("e_id", UUID.class),
                rs.getString("e_base_currency_id"),
                rs.getString("e_target_currency_id"),
                rs.getDouble("e_rate")
        );
    }
}

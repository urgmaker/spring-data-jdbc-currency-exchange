package pet.project.currencyexchange.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pet.project.currencyexchange.model.ExchangeRate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Repository
public class ExchangeRateRepositoryImpl implements ExchangeRateRepository, RowMapper<ExchangeRate> {
    private final JdbcOperations jdbcOperations;

    @Autowired
    public ExchangeRateRepositoryImpl(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<ExchangeRate> findAll() {
        return this.jdbcOperations.query("""
                SELECT * FROM "e_exchangeRates"
                """, this);
    }

    @Override
    public Optional<ExchangeRate> findById(Integer baseCurrencyId, Integer targetCurrencyId) {
        return this.jdbcOperations.query("""
                                select * from "e_exchangeRates" where e_base_currency_id = ? and e_target_currency_id = ?""",
                        new Object[]{baseCurrencyId, targetCurrencyId}, this)
                .stream().findFirst();
    }

    @Override
    public void save(ExchangeRate exchangeRate) {
        this.jdbcOperations.update("""
                                insert into "e_exchangeRates"(e_id, e_base_currency_id, e_target_currency_id, e_rate) values (?, ?, ?, ?)
                        """, exchangeRate.getId(), exchangeRate.getBaseCurrencyId(),
                exchangeRate.getTargetCurrencyId(), exchangeRate.getRate());
    }

    @Override
    public ExchangeRate mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ExchangeRate(
                rs.getInt("e_id"),
                rs.getInt("e_base_currency_id"),
                rs.getInt("e_target_currency_id"),
                rs.getDouble("e_rate")
        );
    }
}

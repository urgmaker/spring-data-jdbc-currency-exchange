package pet.project.currencyexchange.util;

import org.springframework.jdbc.core.RowMapper;
import pet.project.currencyexchange.model.Currency;
import pet.project.currencyexchange.model.ExchangeRate;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExchangeRateRowMapper implements RowMapper<ExchangeRate> {
    @Override
    public ExchangeRate mapRow(ResultSet rs, int rowNum) throws SQLException {
        Currency baseCurrency = Currency.builder()
                .id(rs.getLong("base_currency_id"))
                .code(rs.getString("base_currency_code"))
                .fullName(rs.getString("base_currency_full_name"))
                .sign(rs.getString("base_currency_sign"))
                .build();

        Currency targetCurrency = Currency.builder()
                .id(rs.getLong("target_currency_id"))
                .code(rs.getString("target_currency_code"))
                .fullName(rs.getString("target_currency_full_name"))
                .sign(rs.getString("target_currency_sign"))
                .build();

        return ExchangeRate.builder()
                .id(rs.getLong("id"))
                .baseCurrency(baseCurrency)
                .targetCurrency(targetCurrency)
                .rate(rs.getDouble("rate"))
                .build();
    }
}

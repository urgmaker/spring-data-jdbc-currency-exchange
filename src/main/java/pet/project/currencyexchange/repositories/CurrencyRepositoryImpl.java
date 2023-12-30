package pet.project.currencyexchange.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pet.project.currencyexchange.model.Currency;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CurrencyRepositoryImpl implements CurrencyRepository, RowMapper<Currency> {
    private final JdbcOperations jdbcOperations;

    @Autowired
    public CurrencyRepositoryImpl(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<Currency> findAll() {
        return this.jdbcOperations.query("""
                select * from c_currencies
                """, this);
    }

    @Override
    public void save(Currency currency) {
        this.jdbcOperations.update("""
                insert into c_currencies(c_id, c_code, c_full_name, c_sign) values (?, ?, ?, ?)
                """, currency.getId(), currency.getCode(), currency.getFullName(), currency.getSign());
    }

    @Override
    public Optional<Currency> findByCode(String code) {
        return this.jdbcOperations.query("""
                                select * from c_currencies where c_code = ?""",
                        new Object[]{code}, this)
                .stream().findFirst();
    }

    @Override
    public Currency mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Currency(
                rs.getInt("c_id"),
                rs.getString("c_code"),
                rs.getString("c_full_name"),
                rs.getString("c_sign"));
    }
}

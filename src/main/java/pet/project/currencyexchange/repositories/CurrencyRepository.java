package pet.project.currencyexchange.repositories;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pet.project.currencyexchange.model.Currency;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends CrudRepository<Currency, Long> {
    @Query("SELECT * FROM currency WHERE currency.code = :code")
    Optional<Currency> findByCode(@Param("code") String code);
}

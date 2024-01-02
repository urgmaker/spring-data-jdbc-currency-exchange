package pet.project.currencyexchange.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pet.project.currencyexchange.model.Currency;
import pet.project.currencyexchange.repositories.CurrencyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {
    private final CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public List<Currency> findAll() {
        return (List<Currency>) this.currencyRepository.findAll();
    }

    public void save(Currency currency) {
        this.currencyRepository.save(currency);
    }

    public Optional<Currency> findByCode(String code) {
        return this.currencyRepository.findByCode(code);
    }
}

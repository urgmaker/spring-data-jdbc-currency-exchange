package pet.project.currencyexchange.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pet.project.currencyexchange.model.Currency;
import pet.project.currencyexchange.model.ExchangeRate;
import pet.project.currencyexchange.repositories.CurrencyRepository;
import pet.project.currencyexchange.repositories.ExchangeRateRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ExchangeRateService {
    private final ExchangeRateRepository exchangeRateRepository;

    @Autowired
    public ExchangeRateService(CurrencyRepository currencyRepository, ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    public List<ExchangeRate> findAll() {
        return this.exchangeRateRepository.findAll();
    }

    public Long save(Currency baseCurrency, Currency targetCurrency, Double rate) {
        Long baseCurrencyId = baseCurrency.getId();
        Long targetCurrencyId = targetCurrency.getId();
        return this.exchangeRateRepository.save(Math.toIntExact(baseCurrencyId), Math.toIntExact(targetCurrencyId), rate);
    }

    public Optional<ExchangeRate> findByCodes(String baseCurrencyCode, String targetCurrencyCode) {
        return this.exchangeRateRepository.findByCodes(baseCurrencyCode, targetCurrencyCode);
    }
}

package pet.project.currencyexchange.controllers;

import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pet.project.currencyexchange.model.ExchangeRate;
import pet.project.currencyexchange.repositories.ExchangeRateRepositoryImpl;
import pet.project.currencyexchange.util.ErrorsPresentation;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("api")
public class ExchangeRateController {
    private final ExchangeRateRepositoryImpl exchangeRateRepository;

    private final MessageSource messageSource;

    public ExchangeRateController(ExchangeRateRepositoryImpl exchangeRateRepository, MessageSource messageSource) {
        this.exchangeRateRepository = exchangeRateRepository;
        this.messageSource = messageSource;
    }

    @GetMapping("exchangeRates")
    public ResponseEntity<List<ExchangeRate>> handleGetAllExchangeRates() {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.exchangeRateRepository.findAll());
    }

    @GetMapping("exchangeRate/{baseCurrencyId}{targetCurrencyId}")
    public ResponseEntity<ExchangeRate> handleExchangeRatesByCode(@PathVariable("baseCurrencyId") String baseCurrencyId,
                                                                  @PathVariable("targetCurrencyId") String targetCurrencyId) {
        return ResponseEntity.of(this.exchangeRateRepository.findByCodes(baseCurrencyId, targetCurrencyId));
    }

    @PostMapping("exchangeRates")
    @Transactional
    public ResponseEntity<?> handleAddExchangeRate(
            @RequestBody ExchangeRate payloadExchangeRate,
            UriComponentsBuilder uriComponentsBuilder,
            Locale locale) {
        if (payloadExchangeRate.getBaseCurrencyId() == null || payloadExchangeRate.getBaseCurrencyId().isBlank() &&
                payloadExchangeRate.getTargetCurrencyId() == null || payloadExchangeRate.getTargetCurrencyId().isBlank() &&
                payloadExchangeRate.getRate() == null) {
            final String message = this.messageSource.getMessage("currencies.create.details.errors.not_set",
                    new Object[0], locale);

            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ErrorsPresentation(List.of(message)));
        } else {
            ExchangeRate exchangeRate = new ExchangeRate(
                    payloadExchangeRate.getBaseCurrencyId(),
                    payloadExchangeRate.getTargetCurrencyId(),
                    payloadExchangeRate.getRate());

            this.exchangeRateRepository.save(exchangeRate);
            return ResponseEntity.created(uriComponentsBuilder
                            .path("api/exchangeRates/{id}")
                            .build(Map.of("id", exchangeRate.getId()))).contentType(MediaType.APPLICATION_JSON)
                    .body(exchangeRate);
        }
    }
}

package pet.project.currencyexchange.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pet.project.currencyexchange.model.ExchangeRate;
import pet.project.currencyexchange.dto.ExchangeRateDto;
import pet.project.currencyexchange.repositories.ExchangeRateRepository;
import pet.project.currencyexchange.util.ErrorsPresentation;

import java.util.*;

@RestController
@RequestMapping("api")
public class ExchangeRateController {
    private final ExchangeRateRepository exchangeRateRepository;

    private final MessageSource messageSource;

    @Autowired
    public ExchangeRateController(ExchangeRateRepository exchangeRateRepository, MessageSource messageSource) {
        this.exchangeRateRepository = exchangeRateRepository;
        this.messageSource = messageSource;
    }

    @GetMapping("exchangeRates")
    public ResponseEntity<List<ExchangeRate>> handleGetAllExchangeRates() {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body((List<ExchangeRate>) this.exchangeRateRepository.findAll());
    }

    @GetMapping("exchangeRate/{baseCurrencyId}/{targetCurrencyId}")
    public ResponseEntity<ExchangeRate> handleExchangeRatesByCode(@PathVariable("baseCurrencyId") Integer baseCurrencyId,
                                                                  @PathVariable("targetCurrencyId") Integer targetCurrencyId) {
        return ResponseEntity.of(this.exchangeRateRepository.findByBaseIdAndTargetId(baseCurrencyId, targetCurrencyId));
    }

    @PostMapping("exchangeRates")
    @Transactional
    public ResponseEntity<?> handleAddExchangeRate(
            @RequestBody ExchangeRateDto uploadExchangeRate,
            UriComponentsBuilder uriComponentsBuilder,
            Locale locale) {
        if ((uploadExchangeRate.getBaseCurrencyId() == null || uploadExchangeRate.getBaseCurrencyId() <= 0) &&
            (uploadExchangeRate.getTargetCurrencyId() == null || uploadExchangeRate.getTargetCurrencyId() <= 0) &&
            (uploadExchangeRate.getRate() == null || uploadExchangeRate.getRate() <= 0)) {
            final String message = this.messageSource.getMessage("currencies.create.details.errors.not_set",
                    new Object[0], locale);

            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ErrorsPresentation(List.of(message)));
        } else {
            ExchangeRate exchangeRate = new ExchangeRate(
                    uploadExchangeRate.getBaseCurrencyId(),
                    uploadExchangeRate.getTargetCurrencyId(),
                    uploadExchangeRate.getRate());

            this.exchangeRateRepository.save(exchangeRate);

            return ResponseEntity.created(uriComponentsBuilder
                            .path("api/exchangeRates/{id}")
                            .build(Map.of("id", exchangeRate.getId())))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(exchangeRate);
        }
    }
}
package pet.project.currencyexchange.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pet.project.currencyexchange.model.Currency;
import pet.project.currencyexchange.model.ExchangeRate;
import pet.project.currencyexchange.dto.ExchangeRateDto;
import pet.project.currencyexchange.services.CurrencyService;
import pet.project.currencyexchange.services.ExchangeRateService;
import pet.project.currencyexchange.util.ErrorsPresentation;
import pet.project.currencyexchange.util.UtilBuilder;
import pet.project.currencyexchange.util.Validator;

import java.util.*;

@RestController
@RequestMapping("api")
public class ExchangeRateController {
    private final CurrencyService currencyService;
    private final ExchangeRateService exchangeRateService;

    private final MessageSource messageSource;

    @Autowired
    public ExchangeRateController(CurrencyService currencyService, ExchangeRateService exchangeRateService, MessageSource messageSource) {
        this.currencyService = currencyService;
        this.exchangeRateService = exchangeRateService;
        this.messageSource = messageSource;
    }

    @GetMapping("exchangeRates")
    public ResponseEntity<List<ExchangeRate>> handleGetAllExchangeRates() {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.exchangeRateService.findAll());
    }

    @GetMapping("exchangeRate/{baseCurrencyCode}/{targetCurrencyCode}")
    public ResponseEntity<ExchangeRate> handleGetExchangeRatesByCodes(@PathVariable("baseCurrencyCode") String baseCurrencyCode,
                                                                      @PathVariable("targetCurrencyCode") String targetCurrencyCode) {
        return ResponseEntity.of(this.exchangeRateService.findByCodes(baseCurrencyCode, targetCurrencyCode));
    }

    @PostMapping("exchangeRates")
    @Transactional
    public ResponseEntity<?> handleAddExchangeRate(
            @RequestBody ExchangeRateDto uploadExchangeRate,
            UriComponentsBuilder uriComponentsBuilder,
            Locale locale) {
        if (Validator.isUploadExchangeRateValid(uploadExchangeRate)) {
            final String message = this.messageSource.getMessage("currencies.create.details.errors.not_set",
                    new Object[0], locale);

            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ErrorsPresentation(List.of(message)));
        } else {
            Currency baseCurrency = currencyService.findByCode(uploadExchangeRate.getBaseCurrencyCode()).orElse(null);
            Currency targetCurrency = currencyService.findByCode(uploadExchangeRate.getTargetCurrencyCode()).orElse(null);
            Double rate = uploadExchangeRate.getRate();

            Long savedId = this.exchangeRateService.save(baseCurrency, targetCurrency, rate);

            ExchangeRate exchangeRate = UtilBuilder.buildExchangeRate(savedId, baseCurrency, targetCurrency, rate);

            return ResponseEntity.created(uriComponentsBuilder
                            .path("api/exchangeRates/{id}")
                            .build(Map.of("id", exchangeRate.getId())))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(exchangeRate);
        }
    }
}
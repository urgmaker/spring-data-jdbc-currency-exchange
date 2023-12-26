package pet.project.currencyexchange.controllers;

import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pet.project.currencyexchange.model.Currency;
import pet.project.currencyexchange.repositories.CurrencyRepositoryImpl;
import pet.project.currencyexchange.util.ErrorsPresentation;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("api")
public class CurrencyController {
    private final CurrencyRepositoryImpl currencyRepository;
    private final MessageSource messageSource;

    public CurrencyController(CurrencyRepositoryImpl currencyRepository, MessageSource messageSource) {
        this.currencyRepository = currencyRepository;
        this.messageSource = messageSource;
    }

    @GetMapping("currencies")
    public ResponseEntity<List<Currency>> handleGetAllCurrencies() {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.currencyRepository.findAll());
    }

    @PostMapping("currencies")
    @Transactional
    public ResponseEntity<?> handleAddCurrency(
            @RequestBody Currency payloadCurrency,
            UriComponentsBuilder uriComponentsBuilder,
            Locale locale) {
        if (payloadCurrency.getCode() == null || payloadCurrency.getCode().isBlank() &&
                payloadCurrency.getFullName() == null || payloadCurrency.getFullName().isBlank() &&
                payloadCurrency.getSign() == null || payloadCurrency.getSign().isBlank()) {
            final String message = this.messageSource.getMessage(
                    "currencies.create.details.errors.not_set",
                    new Object[0], locale);

            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ErrorsPresentation(List.of(message)));
        } else {
            Currency currency = new Currency(payloadCurrency.getCode(), payloadCurrency.getFullName(), payloadCurrency.getSign());
            this.currencyRepository.save(currency);
            return ResponseEntity.created(uriComponentsBuilder
                            .path("api/currencies/{id}")
                            .build(Map.of("id", currency.getId())))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(currency);
        }
    }

    @GetMapping("currency/{code}")
    public ResponseEntity<Currency> handleFindTaskByCode(@PathVariable("code") String code) {
        return ResponseEntity.of(this.currencyRepository.findByCode(code));
    }
}
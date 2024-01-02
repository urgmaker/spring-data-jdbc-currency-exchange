package pet.project.currencyexchange.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pet.project.currencyexchange.model.Currency;
import pet.project.currencyexchange.dto.CurrencyDto;
import pet.project.currencyexchange.services.CurrencyService;
import pet.project.currencyexchange.util.ErrorsPresentation;
import pet.project.currencyexchange.util.UtilBuilder;
import pet.project.currencyexchange.util.Validator;

import java.util.*;

@RestController
@RequestMapping("api")
public class CurrencyController {
    private final CurrencyService currencyService;
    private final MessageSource messageSource;

    @Autowired
    public CurrencyController(CurrencyService currencyService, MessageSource messageSource) {
        this.currencyService = currencyService;
        this.messageSource = messageSource;
    }

    @GetMapping("currencies")
    public ResponseEntity<List<Currency>> handleGetAllCurrencies() {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.currencyService.findAll());
    }

    @PostMapping("currencies")
    @Transactional
    public ResponseEntity<?> handleAddCurrency(
            @RequestBody CurrencyDto uploadCurrency,
            UriComponentsBuilder uriComponentsBuilder,
            Locale locale) {
        if (Validator.isUploadCurrencyValid(uploadCurrency)) {
            final String message = this.messageSource.getMessage(
                    "currencies.create.details.errors.not_set",
                    new Object[0], locale);

            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ErrorsPresentation(List.of(message)));
        } else {
            Currency currency = UtilBuilder.buildCurrency(uploadCurrency);

            this.currencyService.save(currency);

            return ResponseEntity.created(uriComponentsBuilder
                            .path("api/currencies/{id}")
                            .build(Map.of("id", currency.getId())))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body((currency));
        }
    }

    @GetMapping("currency/{code}")
    public ResponseEntity<Currency> handleFindCurrencyByCode(@PathVariable("code") String code) {
        return ResponseEntity.of(this.currencyService.findByCode(code));
    }
}
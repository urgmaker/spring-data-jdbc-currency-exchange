package pet.project.currencyexchange.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewCurrencyPayload {
    private String code;
    private String fullName;
    private String sign;
}

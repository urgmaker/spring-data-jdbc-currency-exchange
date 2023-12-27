package pet.project.currencyexchange.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCurrencyPayload {
    private String code;
    private String fullName;
    private String sign;
}

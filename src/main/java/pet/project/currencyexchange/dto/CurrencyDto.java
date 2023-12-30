package pet.project.currencyexchange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class CurrencyDto {
    private String code;
    private String fullName;
    private String sign;
}

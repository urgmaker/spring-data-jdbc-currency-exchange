package pet.project.currencyexchange.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Currency {
    private UUID id;
    private String code;
    private String fullName;
    private String sign;

    public Currency(String code, String fullName, String sign) {
    }
}

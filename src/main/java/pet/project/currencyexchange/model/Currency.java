package pet.project.currencyexchange.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;


@Data
@AllArgsConstructor
@Builder
public class Currency {
    private UUID id;
    private String code;
    private String fullName;
    private String sign;
}

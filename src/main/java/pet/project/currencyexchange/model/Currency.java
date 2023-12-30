package pet.project.currencyexchange.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;



@Data
@AllArgsConstructor
@Builder
public class Currency {
    private Integer id;
    private String code;
    private String fullName;
    private String sign;
}

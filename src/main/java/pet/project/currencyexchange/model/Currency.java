package pet.project.currencyexchange.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Currency {
    @Id
    private Long id;
    private String code;
    private String fullName;
    private String sign;
}

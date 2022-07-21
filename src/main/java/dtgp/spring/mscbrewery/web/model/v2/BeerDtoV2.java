package dtgp.spring.mscbrewery.web.model.v2;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class BeerDtoV2 {
    private UUID id;
    private String beerName;
    private BeerStyleEnum beerStyle;
    private Long upc;
}

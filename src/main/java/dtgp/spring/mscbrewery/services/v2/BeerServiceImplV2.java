package dtgp.spring.mscbrewery.services.v2;

import dtgp.spring.mscbrewery.web.model.v2.BeerDtoV2;
import dtgp.spring.mscbrewery.web.model.v2.BeerStyleEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class BeerServiceImplV2 implements BeerServiceV2 {
    @Override
    public BeerDtoV2 getBeerById(UUID id) {
        return BeerDtoV2.builder().id(UUID.randomUUID()).beerName("Kunstmann").beerStyle(BeerStyleEnum.ALE).build();
    }

    @Override
    public BeerDtoV2 saveNewBeer(BeerDtoV2 beerDto) {
        return BeerDtoV2.builder().id(UUID.randomUUID()).build();
    }

    @Override
    public void updateBeer(UUID beerId, BeerDtoV2 beerDto) {
        //todo impl
    }

    @Override
    public void deleteById(UUID beerId) {
        log.debug("Calling deleteById Method");
        //todo impl
    }
}

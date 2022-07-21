package dtgp.spring.mscbrewery.services;

import dtgp.spring.mscbrewery.web.model.BeerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {
    @Override
    public BeerDto getBeerById(UUID id) {
        return BeerDto.builder().id(UUID.randomUUID()).beerName("Kunstmann").beerStyle("Pale Ale").build();
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        return BeerDto.builder().id(UUID.randomUUID()).build();
    }

    @Override
    public void updateBeer(UUID beerId, BeerDto beerDto) {
        //todo impl
    }

    @Override
    public void deleteById(UUID beerId) {
        log.debug("Calling deleteById Method");
        //todo impl
    }
}

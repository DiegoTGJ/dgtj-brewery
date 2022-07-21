package dtgp.spring.mscbrewery.web.controller.v2;

import dtgp.spring.mscbrewery.services.v2.BeerServiceV2;
import dtgp.spring.mscbrewery.web.model.v2.BeerDtoV2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RequestMapping("/api/v2/beer")
@RestController
public class BeerControllerV2 {

    private final BeerServiceV2 beerService;

    public BeerControllerV2(BeerServiceV2 beerService) {
        this.beerService = beerService;
    }

    @GetMapping({"/{beerId}"})
    public ResponseEntity<BeerDtoV2> getBeer(@PathVariable UUID beerId){
        log.warn("Request made to getBeer");
        return new ResponseEntity<>(beerService.getBeerById(beerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> handlePost(@RequestBody BeerDtoV2 beerDto){
        log.info("Posting beer to system...");
        BeerDtoV2 savedDto = beerService.saveNewBeer(beerDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location","/api/v1/beer/"+savedDto.getId().toString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping({"/{beerId}"})
    public ResponseEntity<String> handleUpdate(@PathVariable UUID beerId,@RequestBody BeerDtoV2 beerDto){
        log.info("Starting updating beer...");
        beerService.updateBeer(beerId,beerDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping({"/{beerId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public  void deleteBeer(@PathVariable UUID beerId){
        log.info("Deleting beer...");
        beerService.deleteById(beerId);
    }
}

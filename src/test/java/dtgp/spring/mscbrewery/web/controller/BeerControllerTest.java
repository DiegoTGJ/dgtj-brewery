package dtgp.spring.mscbrewery.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dtgp.spring.mscbrewery.web.model.BeerDto;
import dtgp.spring.mscbrewery.web.services.BeerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BeerController.class)
public class BeerControllerTest {

    @MockBean
    BeerService beerService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    BeerDto validBeer;

    private static final String API_ENDPOINT = "/api/v1/beer/";

    @BeforeEach
    public void setUp(){
        validBeer = BeerDto.builder().id(UUID.randomUUID())
                .beerName("Beer1")
                .beerStyle("PALE_ALE")
                .upc(1232456789L)
                .build();
    }

    @Test
    public void getBeer() throws Exception {
        given(beerService.getBeerById(any(UUID.class))).willReturn(validBeer);

        mockMvc.perform(get(API_ENDPOINT+validBeer.getId().toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(validBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName",is("Beer1")));
    }

    @Test
    public void handlePost() throws Exception{
        given(beerService.saveNewBeer(any(BeerDto.class))).willReturn(validBeer);
        String beerDtoJson = objectMapper.writeValueAsString(validBeer);
        mockMvc.perform(post(API_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isCreated());


    }

    @Test
    public void handleUpdate() throws Exception{
        BeerDto updatedDto = validBeer;
        updatedDto.setBeerName("New Name");
        String updatedBeerDtoJson = objectMapper.writeValueAsString(updatedDto);

        mockMvc.perform(put(API_ENDPOINT+validBeer.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedBeerDtoJson))
                .andExpect(status().isNoContent());

        then(beerService).should().updateBeer(any(UUID.class),any(BeerDto.class));

    }
}
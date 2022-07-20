package dtgp.spring.mscbrewery.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dtgp.spring.mscbrewery.web.model.BeerDto;
import dtgp.spring.mscbrewery.web.model.CustomerDto;
import dtgp.spring.mscbrewery.web.services.CustomerService;
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

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @MockBean
    CustomerService customerService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    CustomerDto validCustomer;

    private static final String API_ENDPOINT = "/api/v1/customer/";
    @BeforeEach
    public void setUp() {

        validCustomer = CustomerDto.builder()
                .customerName("Customer")
                .id(UUID.randomUUID()).build();
    }

    @Test
    public void getCustomer() throws Exception {

        given(customerService.getCustomerById(any(UUID.class))).willReturn(validCustomer);
        mockMvc.perform(get(API_ENDPOINT+validCustomer.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.customerName",is("Customer")))
                .andExpect(jsonPath("$.id",is(validCustomer.getId().toString())));
    }

    @Test
    public void handlePost() throws Exception {

        given(customerService.saveNewCustomer(any(CustomerDto.class))).willReturn(validCustomer);
        String customerToSave = objectMapper.writeValueAsString(validCustomer);
        String location = API_ENDPOINT+validCustomer.getId().toString();

        mockMvc.perform(post(API_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerToSave))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location",location));
    }

    @Test
    public void handleUpdate() throws Exception{

        CustomerDto updatedCustomer = validCustomer;
        updatedCustomer.setCustomerName("Updated");
        String updatedCustomerString = objectMapper.writeValueAsString(updatedCustomer);

        mockMvc.perform(put(API_ENDPOINT+validCustomer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedCustomerString))
                .andExpect(status().isNoContent());
        then(customerService).should(times(1)).updateCustomer(any(UUID.class),any(CustomerDto.class));
    }

    @Test
    public void handleDelete() throws Exception{

        mockMvc.perform(delete(API_ENDPOINT+validCustomer.getId().toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        then(customerService).should(times(1)).deleteCustomer(any(UUID.class));
    }
}
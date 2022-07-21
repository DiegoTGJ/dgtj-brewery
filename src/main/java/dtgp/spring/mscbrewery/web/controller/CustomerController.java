package dtgp.spring.mscbrewery.web.controller;


import dtgp.spring.mscbrewery.web.model.CustomerDto;
import dtgp.spring.mscbrewery.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{customerId}")
    ResponseEntity<CustomerDto> getCustomer(@PathVariable UUID customerId){
        log.warn("Request made to get customer");
        return new ResponseEntity<>(customerService.getCustomerById(customerId), HttpStatus.OK);
    }

    @PostMapping()
    ResponseEntity<String> handlePost(@RequestBody CustomerDto customerDto){
        CustomerDto savedCustomer = customerService.saveNewCustomer(customerDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location","/api/v1/customer/"+savedCustomer.getId().toString());
        return new ResponseEntity<>(headers,HttpStatus.CREATED);
    }

    @PutMapping({"/{customerId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void handleUpdate(@PathVariable UUID customerId
            ,@RequestBody CustomerDto customerDto){
        customerService.updateCustomer(customerId,customerDto);
    }

    @DeleteMapping({"/{customerId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void handleDelete(@PathVariable UUID customerId){
        customerService.deleteCustomer(customerId);
    }
}

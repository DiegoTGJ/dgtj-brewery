package dtgp.spring.mscbrewery.services;

import dtgp.spring.mscbrewery.web.model.CustomerDto;

import java.util.UUID;

public interface CustomerService {
    CustomerDto getCustomerById(UUID id);

    CustomerDto saveNewCustomer(CustomerDto customerDto);

    void updateCustomer(UUID id, CustomerDto customerDto);

    void deleteCustomer(UUID customerID);
}

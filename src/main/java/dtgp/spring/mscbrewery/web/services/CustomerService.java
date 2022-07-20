package dtgp.spring.mscbrewery.web.services;

import dtgp.spring.mscbrewery.web.model.CustomerDto;

import java.util.UUID;

public interface CustomerService {
    CustomerDto getCustomerById(UUID id);
}

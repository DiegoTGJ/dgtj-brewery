package dtgp.spring.mscbrewery.services;

import dtgp.spring.mscbrewery.web.model.CustomerDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Override
    public CustomerDto getCustomerById(UUID id) {
        return CustomerDto.builder().id(UUID.randomUUID()).customerName("Diego").build();
    }

    @Override
    public CustomerDto saveNewCustomer(CustomerDto customerDto) {
        return CustomerDto.builder()
                .customerName(customerDto.getCustomerName())
                .id(UUID.randomUUID()).build();
    }

    @Override
    public void updateCustomer(UUID id, CustomerDto customerDto) {
        //todo impl
    }

    @Override
    public void deleteCustomer(UUID customerID) {
        //todo impl
    }
}

package dev.hugo.hotel_management_backend.service;

import dev.hugo.hotel_management_backend.model.Customer;
import dev.hugo.hotel_management_backend.repository.CustomerRepository;
import dev.hugo.hotel_management_backend.dto.CustomerDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createOrFindCustomer(CustomerDto customerDto) {
        // Intentamos encontrar un cliente por nombre y correo electrónico
        Optional<Customer> existingCustomer = customerRepository.findByNameAndEmail(
            customerDto.getName(), customerDto.getEmail()
        );

        // Si el cliente ya existe, lo devolvemos
        if (existingCustomer.isPresent()) {
            return existingCustomer.get();
        }

        // Si no existe, creamos uno nuevo
        Customer newCustomer = new Customer();
        newCustomer.setName(customerDto.getName());
        newCustomer.setEmail(customerDto.getEmail());

        return customerRepository.save(newCustomer);
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Customer not found"));
    }

    public Optional<Customer> findCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public boolean deleteCustomer(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            customerRepository.delete(customer.get());
            return true;
        }
        return false;
    }
}

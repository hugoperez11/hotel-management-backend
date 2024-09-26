package dev.hugo.hotel_management_backend.service;

import dev.hugo.hotel_management_backend.model.Customer;
import dev.hugo.hotel_management_backend.dto.CustomerDto;
import dev.hugo.hotel_management_backend.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Método para buscar o crear un cliente
    public Customer createOrFindCustomer(CustomerDto customerDto) {
        // Buscamos al cliente por su correo
        Customer existingCustomer = customerRepository.findByEmail(customerDto.getEmail());
        if (existingCustomer != null) {
            return existingCustomer; // Si existe, lo devolvemos
        }

        // Si no existe, lo creamos
        Customer newCustomer = new Customer();
        newCustomer.setName(customerDto.getName());
        newCustomer.setEmail(customerDto.getEmail());

        return customerRepository.save(newCustomer); // Guardamos el nuevo cliente
    }
}


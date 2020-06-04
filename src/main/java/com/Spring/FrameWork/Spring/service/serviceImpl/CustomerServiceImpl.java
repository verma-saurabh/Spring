package com.Spring.FrameWork.Spring.service.serviceImpl;

import com.Spring.FrameWork.Spring.Entity.Customer;
import com.Spring.FrameWork.Spring.Repositories.CustomerRepository;
import com.Spring.FrameWork.Spring.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;


    public String getEmail(Long id){

        Optional<Customer> customer =customerRepository.findById(id);
        return customer.get().getMobileNumber();
    }
}

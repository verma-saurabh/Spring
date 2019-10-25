package com.Spring.FrameWork.Spring.Controllers;

import com.Spring.FrameWork.Spring.Entity.Customer;
import com.Spring.FrameWork.Spring.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/")
public class HomeController {

    @Autowired
    CustomerRepository customerRepository;


    @GetMapping(value = "/hello")
    public String hello() {
        return "Hello";
    }

    @GetMapping(value = "/All")
    public Iterable<Customer> getAllResultsFromDB() {
        return customerRepository.findAll();
    }

    @PutMapping(value = "/Save")
    public Customer saveCustomer(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @GetMapping(value = "/{id}")
    public Optional<Customer> getCustomer(@PathVariable("id") long id) {
        return customerRepository.findById(id);
    }

    @GetMapping()
    public void getIssue(@PathVariable("id") long id) {
        //return null;
    }

}

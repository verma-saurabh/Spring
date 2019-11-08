package com.Spring.FrameWork.Spring.Controllers;

import com.Spring.FrameWork.Spring.Entity.Customer;
import com.Spring.FrameWork.Spring.Repositories.CustomerRepository;
import com.Spring.FrameWork.Spring.error.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
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

    @PostMapping(value = "/Save")
    public  Customer saveCustomer(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @GetMapping(value = "/{id}")
    public Customer getCustomer(@PathVariable("id") long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @GetMapping()
    public void getIssue(@PathVariable("id") long id) {
        //return null;
    }

    @DeleteMapping(value = "/Delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("id") long id) {

        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            customerRepository.deleteById(id);
            return new ResponseEntity<Customer>(HttpStatus.OK);
        } else {
            throw new CustomerNotFoundException(id);
        }
    }
}

package com.Spring.FrameWork.Spring.Controllers;

import com.Spring.FrameWork.Spring.Entity.Customer;
import com.Spring.FrameWork.Spring.Repositories.CustomerRepository;
import com.Spring.FrameWork.Spring.error.CustomerAlreadyPresentException;
import com.Spring.FrameWork.Spring.error.CustomerNotFoundException;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/api/")
public class HomeController {

    private Logger logger = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    CustomerRepository customerRepository;


    @GetMapping(value = "/hello")
    public String hello() {
        logger.info("Customer wants to be greeted");
        return "Hello";
    }

    @GetMapping(value = "/All")
    public Iterable<Customer> getAllResultsFromDB() {
        return customerRepository.findAll();
    }

    @PostMapping(value = "/Save")
    public ResponseEntity<?> saveCustomer(@RequestBody final Customer customerrequest) {

        synchronized (customerrequest) {

            Customer customer = customerRepository.findByMobileNumber(customerrequest.getMobileNumber());
            if (customer == null) {
                try {
                    Customer saveCustomerResponse = customerRepository.save(customerrequest);
                    return ResponseEntity.status(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(saveCustomerResponse);
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .contentType(MediaType.APPLICATION_JSON).body("Something Went Wrong");
                }

            } else {
                throw new CustomerAlreadyPresentException(customerrequest.getMobileNumber());
            }
        }

    }

    @GetMapping(value = "/{id}")
    public Customer getCustomer(@PathVariable("id") long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @DeleteMapping(value = "/Delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("id") long id) {

        HashMap<Integer, Integer> map = new HashMap<>();

        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            customerRepository.deleteById(id);
            return new ResponseEntity<Customer>(HttpStatus.OK);
        } else {
            throw new CustomerNotFoundException(id);
        }
    }

    public int findJudge(int N, int[][] trust) {

        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 1; i <= N; i++) {
            map.put(i, 0);
        }

        for (int i = 0; i < trust.length; i++) {
            map.put(i, map.get(i) + 1);
        }
        int[] ans = {-1};
        map.forEach((k, v) -> {
            if (v == 0) {
                ans[0] =v;
            }
        });
        return ans[0];
    }
}

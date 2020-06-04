package com.Spring.FrameWork.Spring.Controllers;

import com.Spring.FrameWork.Spring.Entity.Customer;
import com.Spring.FrameWork.Spring.Repositories.CustomerRepository;
import com.Spring.FrameWork.Spring.error.CustomerAlreadyPresentException;
import com.Spring.FrameWork.Spring.error.CustomerNotFoundException;
import com.Spring.FrameWork.Spring.service.CustomerService;
import com.Spring.FrameWork.Spring.service.serviceImpl.CustomerServiceImpl;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/")
public class HomeController {

    private Logger logger = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerServiceImpl customerService;

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

    @PostMapping(value = "/getUserAndThenProcess")
    public void getUserAndThenProcess(@RequestBody Long[] ids) {

        Supplier<List<Long>> supplyIds = () -> {
            //sleep(500);
            return Arrays.asList(ids);
        };


        Function<List<Long>, CompletableFuture<List<Optional<Customer>>>> fetchUsers = id -> {
            //sleep(500);

            Supplier<List<Optional<Customer>>> custSupplier =
                    () -> {
                        System.out.println("Running in Thread -> " + Thread.currentThread().getName());
                        return id.stream()
                                .map(i -> customerRepository.findById(i))
                                .collect(Collectors.toList());
                    };
            return CompletableFuture.supplyAsync(custSupplier);
        };

        Consumer<List<Optional<Customer>>> displayCust = customers -> {
            System.out.println("Running in Thread -> " + Thread.currentThread().getName());
            customers.forEach(System.out::println);
        };

        Function<List<Long>, CompletableFuture<List<String>>> fetchMobileNumber = id -> {
            sleep(350);
            Supplier<List<String>> phoneNumberSupplier = () -> {
                return id.stream().map(i -> customerService.getEmail(i)).collect(Collectors.toList());
            };
            return CompletableFuture.supplyAsync(phoneNumberSupplier);
        };

        CompletableFuture<List<Long>> completableFuture = CompletableFuture.supplyAsync(supplyIds);

        CompletableFuture<List<Optional<Customer>>> userFuture = completableFuture.thenCompose(fetchUsers);
        CompletableFuture<List<String>> mobileFuture = completableFuture.thenCompose(fetchMobileNumber);


        // task after both of then gets executed
        userFuture.thenAcceptBoth(mobileFuture, (cust, mob) -> {
            System.out.println(cust.size() + " " + mob.size());
        });

        // one after the other
        //.thenAcceptAsync(displayCust);

        sleep(2000);
    }

    public void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

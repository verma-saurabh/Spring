package com.Spring.FrameWork.Spring.Repositories;

import com.Spring.FrameWork.Spring.Entity.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    @Query(value = "Select * From \"Customer\" ", nativeQuery = true)
    List<Customer> findAllCustomers(String name);

    @Query(value = "Select * From Customer where mobileNumber=?1 LIMIT 1", nativeQuery = true)
    Customer findByMobileNumber(String mobileNumber);

    @Query(value = "Select * From Customer where id=?1 LIMIT 1", nativeQuery = true)
    Optional<Customer> findById(Long id);


}

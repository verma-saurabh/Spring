package com.Spring.FrameWork.Spring.Repositories;

import com.Spring.FrameWork.Spring.Entity.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    @Query(value = "Select * From \"Customer\" ", nativeQuery = true)
    List<Customer> findByName(String name);
}

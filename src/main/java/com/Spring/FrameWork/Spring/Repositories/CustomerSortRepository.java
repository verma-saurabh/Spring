package com.Spring.FrameWork.Spring.Repositories;

import com.Spring.FrameWork.Spring.Entity.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerSortRepository extends PagingAndSortingRepository<Customer, Long> {
}

package com.example.api.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.api.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	List<Customer> findAllByOrderByNameAsc();

	  @Query("FROM Customer c " +
	           "WHERE LOWER(c.name) like %:searchTerm% " +
	           "OR LOWER(c.email) like %:searchTerm%")
	    Page<Customer> search(
	            @Param("searchTerm") String searchTerm, 
	            Pageable pageable);
}

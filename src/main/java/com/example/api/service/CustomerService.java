package com.example.api.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.api.domain.Customer;
import com.example.api.repository.CustomerRepository;

@Service
public class CustomerService {

	private CustomerRepository repository;

	@Autowired
	public CustomerService(CustomerRepository repository) {
		this.repository = repository;
	}

	
	public Page<Customer> findAll() {
	        int page = 0;
	        int size = 10;
	        PageRequest pageRequest = PageRequest.of(
	                page,
	                size,
	                Sort.Direction.ASC,
	                "name");
	        return new PageImpl<>(
	                repository.findAll(), 
	                pageRequest, size);
	    }

	public Optional<Customer> findById(Long id) {
		return repository.findById(id);
	}


	public Customer salvar(Customer customer) {
		return repository.save(customer);
	}
	
	
	public Customer getOne(Long id) {
return repository.getOne(id);
	}
	

	public void excluir(Long id) {
		repository.deleteById(id);

	}
	
	
	
	 public Page<Customer> search(
	            String searchTerm,
	            int page,
	            int size) {
	        PageRequest pageRequest = PageRequest.of(
	                page,
	                size,
	                Sort.Direction.ASC,
	                "name");

	        return repository.search(
	                searchTerm.toLowerCase(),
	                pageRequest);
	    }


}

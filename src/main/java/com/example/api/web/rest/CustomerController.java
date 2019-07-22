package com.example.api.web.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.api.domain.Customer;
import com.example.api.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {
	
	@Autowired
	private CustomerService service;
	
//	@Autowired
//	private CustomerRepository repository;

	@Autowired
	public CustomerController(CustomerService service) {
		this.service = service;
	}

//	@GetMapping
//	public List<Customer> findAll() {
//		return service.findAll();
//	}

	@GetMapping("/{id}")
	public Customer findById(@PathVariable Long id) {
		return service.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
	}

	@PostMapping("/inserir")
	public Customer inserir(@Valid  @RequestBody Customer customer) {
		return service.salvar(customer);
	}


	@PutMapping("/alterar/{id}")
	@RequestMapping(value = "/alterar/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Customer> alterar(@PathVariable Long id, @Valid @RequestBody Customer customer){
		Customer existe = service.getOne(id);
		
		if(existe ==null) {
		return ResponseEntity.notFound().build();
		}
		BeanUtils.copyProperties(customer, existe, "id");
		
		existe = service.salvar(existe);
		
		return ResponseEntity.ok(existe);
	}
	
	@RequestMapping(value = "/excluir/{id}", method = RequestMethod.DELETE)
	public void excluir(@PathVariable("id") Long id) {
		service.excluir(id);

	}
	
	   @GetMapping("/paginada")
	    public Page<Customer> search(
	            @RequestParam("searchTerm") String searchTerm,
	            @RequestParam(
	                    value = "page",
	                    required = false,
	                    defaultValue = "0") int page,
	            @RequestParam(
	                    value = "size",
	                    required = false,
	                    defaultValue = "10") int size) {
	        return service.search(searchTerm, page, size);

	    }

	    @GetMapping
	    public Page<Customer> getAll() {
	        return service.findAll();
	    }
	
	

}

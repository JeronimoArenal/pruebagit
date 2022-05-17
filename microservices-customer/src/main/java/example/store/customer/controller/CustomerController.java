package example.store.customer.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import example.store.customer.entity.Customer;
import example.store.customer.entity.Region;
import example.store.customer.exception.ErrorMessage;
import example.store.customer.repository.CustomerRepository;
import example.store.customer.service.CustomerService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/customers")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	//............................... List all Customers .........................
	
	 @GetMapping
	    public ResponseEntity<List<Customer>> listAllCustomers(@RequestParam(name = "regionId" , required = false) Long regionId ) {
	        List<Customer> customers =  new ArrayList<>();
	        if (null ==  regionId) {
	            customers = customerService.findAllCustomer();
	            if (customers.isEmpty()) {
	                return ResponseEntity.noContent().build();
	            }
	        }else{
	            Region Region= new Region();
	            Region.setId(regionId);
	            customers = customerService.findCustomerByRegion(Region);
	            if ( null == customers ) {
	                log.error("Customers with Region id {} not found.", regionId);
	                return  ResponseEntity.notFound().build();
	            }
	        }

	        return  ResponseEntity.ok(customers);
	    }

	   // -------------------Retrieve Single Customer------------------------------------------

	    @GetMapping(value = "/{id}")
	    public ResponseEntity<Customer> getCustomer(@PathVariable("id") long id) {
	        log.info("Fetching Customer with id {}", id);
	        Customer customer = customerService.findCustomer(id);
	        if (  null == customer) {
	            log.error("Customer with id {} not found.", id);
	            return  ResponseEntity.notFound().build();
	        }
	        return  ResponseEntity.ok(customer);
	    }

	    // -------------------Create a Customer-------------------------------------------

	    @PostMapping
	    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer, BindingResult result) {
	        log.info("Creating Customer : {}", customer);
	        if (result.hasErrors()){
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessage.formatMessage(result));
	        }

	       Customer customerDB = customerService.createCustomer (customer);

	        return  ResponseEntity.status( HttpStatus.CREATED).body(customerDB);
	    }

	    // ------------------- Update a Customer ------------------------------------------------

	    @PutMapping(value = "/{id}")
	    public ResponseEntity<?> updateCustomer(@PathVariable("id") long id, @RequestBody Customer customer) {
	        log.info("Updating Customer with id {}", id);

	        Customer currentCustomer = customerService.findCustomer(id);

	        if ( null == currentCustomer ) {
	            log.error("Unable to update. Customer with id {} not found.", id);
	            return  ResponseEntity.notFound().build();
	        }
	        customer.setId(id);
	        currentCustomer=customerService.updateCustomer(customer);
	        return  ResponseEntity.ok(currentCustomer);
	    }

	    // ------------------- Delete a Customer -----------------------------------------

	    @DeleteMapping(value = "/{id}")
	    public ResponseEntity<Customer> deleteCustomer(@PathVariable("id") long id) {
	        log.info("Fetching & Deleting Customer with id {}", id);

	        Customer customer = customerService.findCustomer(id);
	        if ( null == customer ) {
	            log.error("Unable to delete. Customer with id {} not found.", id);
	            return  ResponseEntity.notFound().build();
	        }
	        customer = customerService.deleteCustomer(customer);
	        return  ResponseEntity.ok(customer);
	    }

}

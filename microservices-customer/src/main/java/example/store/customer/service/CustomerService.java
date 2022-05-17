package example.store.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import example.store.customer.entity.Customer;
import example.store.customer.entity.Region;
import example.store.customer.repository.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
	CustomerRepository customerRepository;
	
	
	//............................... List all Customers ..........................
	
	public List<Customer> findAllCustomer(){
		return customerRepository.findAll();
	}
		
	//............................... Find Customer ..........................
	
    public Customer findCustomer(Long id) {
        return  customerRepository.findById(id).orElse(null);
    }
	
	//............................... Create Customer ..........................
	
	public Customer createCustomer(Customer customer) {
		//Hacemos método idempotente
		Customer customerDB = customerRepository.findByDni(customer.getDni());
			if(customerDB != null) {
				return customerDB;
			}
			customer.setState("CREATED");
			customerDB = customerRepository.save(customer);
			
			return customerDB;
		}
		
	//............................... Update Customer ..........................
		
		public Customer updateCustomer(Customer customer) {
			Customer customerDB = findCustomer(customer.getId());
			if(customerDB == null) {
				return null;
			}
			customerDB.setFirstName(customer.getFirstName());
			customerDB.setLastName(customer.getLastName());
			customerDB.setEmail(customer.getEmail());
			customerDB.setPhotoUrl(customer.getPhotoUrl());
			
			return customerRepository.save(customerDB);
		}
		
	//............................... Delete Customer ..........................
		
		public Customer deleteCustomer(Customer customer) {
			
			Customer customerDB = findCustomer(customer.getId());
			if(customerDB == null) {
				return null;
			}
			
			customer.setState("DELETED");
			return customerRepository.save(customer);
			
		}
		
	//............................... Find by Region ..........................
		
		public List<Customer> findCustomerByRegion(Region region){
			return customerRepository.findByRegion(region);
		}

}

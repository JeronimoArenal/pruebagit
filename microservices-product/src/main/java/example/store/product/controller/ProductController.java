package example.store.product.controller;

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

import example.store.product.entity.Category;
import example.store.product.entity.Product;
import example.store.product.exception.ErrorMessage;
import example.store.product.service.ProductService;

@RestController
@RequestMapping(value="/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	//............................... Get all ..........................
	
	@GetMapping																	
	public ResponseEntity<List<Product>> listProduct(){
		List<Product> products = products = productService.findAll();
				if (products.isEmpty()) {
					return ResponseEntity.noContent().build();
				}
				
		return ResponseEntity.ok(products);
	}
	
	//............................... Get all ByCategory..........................// Incluye Get all
	
	@GetMapping																		
	public ResponseEntity<List<Product>> listCategoryProduct(@RequestParam(name="categoryId", required=false)Long categoryId){
		List<Product> products = new ArrayList<>();
			if(categoryId == null) {
				products = productService.findAll();
				if (products.isEmpty()) {
					return ResponseEntity.noContent().build();
				}
		}else{
			 products = productService.findByCategory(Category.builder().id(categoryId).build());
				if(products.isEmpty()) {
					return ResponseEntity.notFound().build();
				}
		}
			
		return ResponseEntity.ok(products);
	}
	
	//............................... Get by id ..........................
	
	@GetMapping(value="/{id}")														
	public ResponseEntity<Product> getProduct(@PathVariable("id") Long id){
		Product product = productService.findProduct(id);
		if(product == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(product);
	}
	
	//............................... Create ..........................
	
	@PostMapping																	
	public ResponseEntity<Product> entryProduct(@Valid @RequestBody Product product, BindingResult resultBind){
		if(resultBind.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessage.formatMessage(resultBind));
																	//SUSTITUIDO this.formatMessage(resultBind))
		}
		
		Product productCreate = productService.createProduct(product);
			return ResponseEntity.status(HttpStatus.CREATED).body(productCreate);
	}
	
	//............................... Update ..........................	
	
	@PutMapping(value="/{id}")														
	public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
		product.setId(id);
		Product productDB = productService.updateProduct(product);
		if(productDB == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(productDB);
	}
	
	//............................... Delete ..........................
	
	@DeleteMapping(value="/{id}")													
	public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id){
		Product productDelete = productService.deleteProduct(id);
		if(productDelete == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(productDelete);
	}
	
	//............................... Update stock ..........................
	
	@PutMapping(value="/{id}/stock")
	public ResponseEntity<Product> updateStock(@PathVariable("id") Long id, 
												@RequestParam(name="quantity", required = true) Double quantity){
		Product product = productService.updateStock(id, quantity);
		if(product == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(product);
		
/*	    // ------------------- Message Format Method-----------------------------------------
		public static String formatMessage(BindingResult resultBind) {
			List<Map<String, String>> errors = resultBind.getFieldErrors().stream()
					 .map(err ->{
		                    Map<String,String>  error =  new HashMap<>();
		                    error.put(err.getField(), err.getDefaultMessage());
		                    return error;

		                }).collect(Collectors.toList());
		        ErrorMessage errorMessage = ErrorMessage.builder()
		                .code("01")
		                .messages(errors).build();
		        ObjectMapper mapper = new ObjectMapper();
		        String jsonString="";
		        try {
		            jsonString = mapper.writeValueAsString(errorMessage);
		        } catch (JsonProcessingException e) {
		            e.printStackTrace();
		        }
		        return jsonString;
				
		}
*/	
		
	}
	
}

package example.store.product.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import example.store.product.entity.Category;
import example.store.product.entity.Product;
import example.store.product.repository.ProductRepository;



@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;

	//............................... Find all ..........................
	
	public List<Product> findAll(){										
		return productRepository.findAll();
	}
	
	//............................... Find Product ..........................
	
	public Product findProduct(Long id) {								
		return productRepository.findById(id).orElse(null);
	}
	
	//............................... Create ..........................
	
	public Product createProduct(Product product) {						
		product.setStatus("CREATED");
		product.setCreateAt(new Date());
		
		return productRepository.save(product);
	}
	
	//............................... Update ..........................
	
	public Product updateProduct(Product product) {						
		Product productDB = findProduct(product.getId());
			if(productDB == null) {
			return null;
			}
		productDB.setName(product.getName());
		productDB.setDescription(product.getDescription());
		productDB.setCategory(product.getCategory());
		productDB.setPrice(product.getPrice());
		
		return productRepository.save(productDB);
	}
	
	//............................... Delete ..........................
	
	public Product deleteProduct(Long id) {								
		Product productDB = findProduct(id);
			if(productDB == null) {
					return null;
				}
		productDB.setStatus("DELETED");
		return productRepository.save(productDB);
	}
	
	//............................... Find by Category ..........................
	
	public List<Product> findByCategory(Category category){				
		return productRepository.findByCategory(category);
	}
	
	//............................... Update Stock ..........................
	
	public Product updateStock(Long id, Double quantity) {	//recibimos producto y su stock
		Product productDB = findProduct(id);{
			if(productDB == null) {
				return null;
			}
			Double stock = productDB.getStock() + quantity;
			productDB.setStock(stock);
			return productRepository.save(productDB);
		}
	
	}

}

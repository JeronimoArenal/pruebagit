package example.store.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import example.store.product.entity.Category;
import example.store.product.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	
	public List<Product> findByCategory(Category category);

}

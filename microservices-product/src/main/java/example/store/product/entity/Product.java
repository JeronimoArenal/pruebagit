package example.store.product.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.annotations.ManyToAny;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor @Builder ////Metodos Lombok para testear
@Table(name="PRODUCTS")
public class Product implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message="El nombre no puede estar en blanco")
	private String name;
	private String description;
	
	@Positive(message="El valor tiene que ser mayor que 0")
	private Double stock;
	private Double price;
	private String status;
	
	@Column(name="CREATE_AT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="CATEGORY_ID", referencedColumnName = "id")
	@NotNull(message="La categoría no puede ser nula")
	@JsonIgnoreProperties({"hibernateLazyInitializer"})	//corrige el error "hibernateLazyInitializer" al hacer consulta postman 
	private Category category;
	

}

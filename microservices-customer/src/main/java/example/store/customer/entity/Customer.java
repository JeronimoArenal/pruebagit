package example.store.customer.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Table(name = "CUSTOMERS")
@Data
public class Customer implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, nullable=false)
	@Size(min=8, max=8, message="El documento tiene que tener un total de 8 caracteres")
	private String dni;
	
	@NotBlank(message="El campo no puede estar vacio")
	private String firstName;
	
	@NotBlank(message="El campo no puede estar vacio")
	private String lastName;
	
	@Column(unique=true, nullable=false)
	@Email(message="direccion no valida")
	@NotBlank(message="por favor, introduzca un correo válido")
	private String email;
	
	@Column(name="photo_url")
	private String photoUrl;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "region_id", referencedColumnName = "id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Region region;
	
	private String state;

}

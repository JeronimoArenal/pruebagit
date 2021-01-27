package es.proyecto.bookstore.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
public class Book implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "{message.book.title}")
	private String title;
	
	@NotBlank(message = "{message.book.isbn}")
	@Column(unique = true)
	private String isbn;
	
	@NotBlank(message = "{message.book.author}")
	private String author;
	
	@Enumerated(value = EnumType.STRING)
	private Editorial editorial;
	
    @NotNull(message = "{message.book.published}")
	@Past(message = "{message.book.publishedPast}")
	private Date publishedDate;
	
    @NotNull(message = "{message.book.price}")
    @Min(value = 1, message = "{message.book.priceMin}")
	private Double price;
	
	@NotNull
	private Boolean onSale;
	

//M E T H O D S
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Editorial getEditorial() {
		return editorial;
	}

	public void setEditorial(Editorial editorial) {
		this.editorial = editorial;
	}

	public Date getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Boolean getOnSale() {
		return onSale;
	}

	public void setOnSale(Boolean onSale) {
		this.onSale = onSale;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", isbn=" + isbn + ", author=" + author + ", editorial="
				+ editorial + ", publishedDate=" + publishedDate + ", price=" + price + ", onSale=" + onSale + "]";
	}
	
		
}

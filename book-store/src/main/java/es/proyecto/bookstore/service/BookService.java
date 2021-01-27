package es.proyecto.bookstore.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.proyecto.bookstore.domain.Book;
import es.proyecto.bookstore.repository.BookRepository;

@Service
public class BookService {
	
	@Autowired
	private BookRepository bookRepository;
	
	
	
	public List<Book> findAll(){						//Buscamos libros para mostrar en el index
		return this.bookRepository.findAll();
	}
	
		
	public void edit(Book book) {
		this.bookRepository.findById(book.getId());
	}
	
	public void save(Book book) {
		this.bookRepository.save(book);
	}
	

	public void delete(Book book) {
		this.bookRepository.deleteById(book.getId());
	}
	
	public boolean checkIsbnAvailable(Book book) throws Exception {
		Optional bookFound = bookRepository.findByIsbn(book.getIsbn());
		if (bookFound.isPresent()) {
			throw new Exception("isbn repetido");
		}
		return true;
	}
	
	public Book creteBook(Book book) throws Exception{
		if(checkIsbnAvailable(book)) {
			book = bookRepository.save(book);
		}
		return book;
	}

}

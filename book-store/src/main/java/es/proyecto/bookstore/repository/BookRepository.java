package es.proyecto.bookstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import es.proyecto.bookstore.domain.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

	Optional<Book> findByIsbn(String isbn);


}

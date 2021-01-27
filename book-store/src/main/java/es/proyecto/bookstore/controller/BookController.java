package es.proyecto.bookstore.controller;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.proyecto.bookstore.domain.Book;
import es.proyecto.bookstore.domain.Editorial;
import es.proyecto.bookstore.repository.BookRepository;
import es.proyecto.bookstore.service.BookService;

@Controller
//@RequestMapping(path = {"/","/book"})
public class BookController {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BookController.class);
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private BookRepository bookRepository;
	
	
	@GetMapping("/")																	//L I S T A R							
	public String listBook(final Book book, ModelMap model) {
		model.addAttribute("listBooks", bookService.findAll());	// nombre = valor
		return "index";												
	}
	@ModelAttribute("allBooks")
	public List<Book> populatedBooks(){
		return this.bookService.findAll();
	}
	
	@GetMapping("/add/{id}")															//A Ñ A D I R
	public String addBook(Book book) {
		book.setPublishedDate(Calendar.getInstance().getTime());
		book.setOnSale(true);
		return "add";
	}
	@ModelAttribute("allEditorials")
	public List<Editorial> showEditorial(){
		return Arrays.asList(Editorial.values());
	}
		
	@GetMapping("/edit/{id}")															//E D I T A R 
	public String editBook(@PathVariable("id") Long id, Model model, Book book) {
		if(id != null && id !=0) {
			model.addAttribute("book", bookRepository.findById(id));
		} 
		return "edit";
	}
	
	/*@PostMapping("/saveBook")															//G U A R D A R
	public String saveBook(@Valid @ModelAttribute("book") Book book, BindingResult resultBind, ModelMap model) {
		if(resultBind.hasErrors()) {
			model.addAttribute("book", book);
			return "add";
		}
		this.bookService.save(book);
		return "redirect:/";
		}*/
	
	@PostMapping("/changeBook")															//G U A R D A R
	public String changek(@Valid @ModelAttribute("book") Book book, final Errors resultBind, final ModelMap model) {
		if(resultBind.hasErrors()) {
			model.addAttribute("book", book);
			return "edit";
		}
		this.bookService.save(book);
		return "redirect:/";
		}
	
	@PostMapping("/saveBook")															//G U A R D A R
	public String saveBook(@Valid @ModelAttribute("book") Book book, BindingResult resultBind, ModelMap model) {
		if(resultBind.hasErrors()) {
			model.addAttribute("book", book);
			return "add";
		} else {
			try {
				bookService.creteBook(book);
			} catch (Exception e) {
				model.addAttribute("isbnErrorMessage", e.getMessage());
				model.addAttribute("book", book);
				return "add";
				}
			} 	
		this.bookService.save(book);
		return "redirect:/";											
		}


	@GetMapping("/delete/{jero}")														//ELIMINAR
	public String deleteBook(@PathVariable(name = "jero") Long id) {
		this.bookRepository.deleteById(id);
		return "redirect:/";
	}
	
}

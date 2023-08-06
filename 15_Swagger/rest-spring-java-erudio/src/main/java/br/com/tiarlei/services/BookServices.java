package br.com.tiarlei.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tiarlei.controllers.BookController;
import br.com.tiarlei.data.vo.v1.BookVO;
import br.com.tiarlei.exceptions.RequiredObjectsIsNullException;
import br.com.tiarlei.exceptions.ResourceNotFoundException;
import br.com.tiarlei.mapper.DozerMapper;
import br.com.tiarlei.mapper.custom.BookMapper;
import br.com.tiarlei.model.Book;
import br.com.tiarlei.repositories.BookRepository;

@Service
public class BookServices {
	
	private Logger logger = Logger.getLogger(BookServices.class.getName());
	
	@Autowired
	BookMapper mapper;
	
	@Autowired
	BookRepository repository;
	
	public List<BookVO> findAll() {
		logger.info("Finding all books!");
		var books = DozerMapper.parseListObjects(repository.findAll(), BookVO.class);
		books.stream()
		.forEach(p -> p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));
		return books;
	}
		
	public BookVO findById(Long id) {
		logger.info("Finding one book!");
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		var vo = DozerMapper.parseObject(entity, BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		return vo;
	}
	
	public BookVO create(BookVO book) {
		if (book == null) throw new RequiredObjectsIsNullException();
		logger.info("Creating one book!");
		var entity = DozerMapper.parseObject(book, Book.class);
		var vo =  DozerMapper.parseObject(repository.save(entity), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}

	public BookVO update(BookVO book) {
		if (book == null) throw new RequiredObjectsIsNullException();
		logger.info("Updating one book!");
		
		var entity = repository.findById(book.getKey())
			.orElseThrow(() -> new ResourceNotFoundException("No records found this ID!"));
		
		entity.setAuthor(book.getAuthor());
		entity.setTitle(book.getTitle());
		entity.setDate(book.getDate());
		entity.setPrice(book.getPrice());
		
		var vo =  DozerMapper.parseObject(repository.save(entity), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public void delete(Long id) {
		logger.info("Deleting one book!");
		
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found this ID!"));
		repository.delete(entity);
	}
}

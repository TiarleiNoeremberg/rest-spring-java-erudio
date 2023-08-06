package br.com.tiarlei.mapper.custom;

import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.tiarlei.data.vo.v1.BookVO;
import br.com.tiarlei.model.Book;

@Service
public class BookMapper {
	
	public BookVO convertEntityToVo(Book book) {
		BookVO vo = new BookVO();
		vo.setKey(book.getId());
		vo.setAuthor(book.getAuthor());
		vo.setTitle(book.getTitle());
		vo.setDate(new Date());
		vo.setPrice(book.getPrice());
		return vo;
	}
	
	public Book convertVoToEntity(BookVO book) {
		Book entity = new Book();
		entity.setId(book.getKey());
		entity.setAuthor(book.getAuthor());
		entity.setTitle(book.getTitle());
		entity.setDate(book.getDate());
		return entity;
	}
}

package br.com.tiarlei.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tiarlei.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}

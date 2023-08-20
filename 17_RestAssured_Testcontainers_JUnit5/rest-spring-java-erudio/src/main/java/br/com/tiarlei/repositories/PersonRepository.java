package br.com.tiarlei.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tiarlei.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}

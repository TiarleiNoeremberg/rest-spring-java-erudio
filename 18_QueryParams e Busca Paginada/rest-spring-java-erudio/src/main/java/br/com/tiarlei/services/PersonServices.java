package br.com.tiarlei.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import br.com.tiarlei.controllers.PersonController;
import br.com.tiarlei.data.dto.v1.PersonDTO;
import br.com.tiarlei.exceptions.RequiredObjectsIsNullException;
import br.com.tiarlei.exceptions.ResourceNotFoundException;
import br.com.tiarlei.mapper.DozerMapper;
import br.com.tiarlei.mapper.custom.PersonMapper;
import br.com.tiarlei.model.Person;
import br.com.tiarlei.repositories.PersonRepository;
import jakarta.transaction.Transactional;

@Service
public class PersonServices {
	
	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	PersonMapper mapper;
	
	@Autowired
	PersonRepository repository;
	
	@Autowired
	PagedResourcesAssembler<PersonDTO> assembler;
	
	public PagedModel<EntityModel<PersonDTO>> findAll(Pageable pageable) {
		logger.info("Finding all people!");
		
		var personPage = repository.findAll(pageable);
		var personDTOsPage = personPage.map(p -> DozerMapper.parseObject(p, PersonDTO.class));
		personDTOsPage.map(
				p -> p.add(
						linkTo(methodOn(PersonController.class)
								.findById(p.getKey())).withSelfRel()));
		
		Link link = linkTo(methodOn(PersonController.class)
				.findAll(pageable.getPageNumber(),
						pageable.getPageSize(),
						"asc")).withSelfRel();
		
		return assembler.toModel(personDTOsPage, link);
	}
	
	public PagedModel<EntityModel<PersonDTO>> findPersonByName(String firstName, Pageable pageable) {
		logger.info("Finding all people contain characters from firstName!");
		
		var personPage = repository.findPersonByName(firstName, pageable);
		
		var personDTOsPage = personPage.map(p -> DozerMapper.parseObject(p, PersonDTO.class));
		personDTOsPage.map(
				p -> p.add(
						linkTo(methodOn(PersonController.class)
								.findById(p.getKey())).withSelfRel()));
		
		Link link = linkTo(methodOn(PersonController.class)
				.findAll(pageable.getPageNumber(),
						pageable.getPageSize(),
						"asc")).withSelfRel();
		
		return assembler.toModel(personDTOsPage, link);
	}
		
	public PersonDTO findById(Long id) {
		logger.info("Finding one person!");
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		var vo = DozerMapper.parseObject(entity, PersonDTO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return vo;
	}
	
	public PersonDTO create(PersonDTO person) {
		if (person == null) throw new RequiredObjectsIsNullException();
		logger.info("Creating one person!");
		var entity = DozerMapper.parseObject(person, Person.class);
		var vo =  DozerMapper.parseObject(repository.save(entity), PersonDTO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}

	public PersonDTO update(PersonDTO person) {
		if (person == null) throw new RequiredObjectsIsNullException();
		logger.info("Updating one person!");
		
		var entity = repository.findById(person.getKey())
			.orElseThrow(() -> new ResourceNotFoundException("No records found this ID!"));
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		var vo =  DozerMapper.parseObject(repository.save(entity), PersonDTO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	@Transactional
	public PersonDTO disablePerson(Long id) {
		logger.info("Disabling one person!");
		
		repository.disablePerson(id);
		
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		var vo = DozerMapper.parseObject(entity, PersonDTO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return vo;
	}
	
	public void delete(Long id) {
		logger.info("Deleting one person!");
		
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found this ID!"));
		repository.delete(entity);
	}
}

package br.com.tiarlei.mapper.custom;

import org.springframework.stereotype.Service;

import br.com.tiarlei.data.dto.v1.PersonDTO;
import br.com.tiarlei.model.Person;

@Service
public class PersonMapper {
	
	public PersonDTO convertEntityToVo(Person person) {
		PersonDTO vo = new PersonDTO();
		vo.setKey(person.getId());
		vo.setAddress(person.getAddress());
		vo.setFirstName(person.getFirstName());
		vo.setLastName(person.getLastName());
		vo.setGender(person.getGender());	
		return vo;
	}
	
	public Person convertVoToEntity(PersonDTO person) {
		Person entity = new Person();
		entity.setId(person.getKey());
		entity.setAddress(person.getAddress());
		//vo.setBirthDay(new Date());
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setGender(person.getGender());
		return entity;
	}
}

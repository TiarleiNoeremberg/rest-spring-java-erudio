package br.com.tiarlei.mapper.custom;

import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.tiarlei.data.dto.v2.PersonDTOV2;
import br.com.tiarlei.model.Person;

@Service
public class PersonMapper {
	
	public PersonDTOV2 convertEntityToVo(Person person) {
		PersonDTOV2 vo = new PersonDTOV2();
		vo.setId(person.getId());
		vo.setAddress(person.getAddress());
		vo.setBirthDay(new Date());
		vo.setFirstName(person.getFirstName());
		vo.setLastName(person.getLastName());
		vo.setGender(person.getGender());
		return vo;
	}
	
	public Person convertVoToEntity(PersonDTOV2 person) {
		Person entity = new Person();
		entity.setId(person.getId());
		entity.setAddress(person.getAddress());
		//vo.setBirthDay(new Date());
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setGender(person.getGender());
		return entity;
	}
}

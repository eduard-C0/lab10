package en.ubb.pet_shop.web.converter;

import en.ubb.pet_shop.core.domain.Person;
import en.ubb.pet_shop.web.dto.PersonDto;
import org.springframework.stereotype.Component;

@Component
public class PersonConverter extends BaseEntityConverter<Person, PersonDto>{
    @Override
    public Person convertDtoToModel(PersonDto dto) {
        var model = new Person();
        model.setId(dto.getId());
        model.setName(dto.getName());
        model.setBudget(dto.getBudget());
        return model;
    }

    @Override
    public PersonDto convertModelToDto(Person model) {
        PersonDto dto = new PersonDto();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setBudget(model.getBudget());
        return dto;
    }
}

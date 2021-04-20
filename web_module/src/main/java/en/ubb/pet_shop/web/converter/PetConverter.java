package en.ubb.pet_shop.web.converter;

import en.ubb.pet_shop.core.domain.Pet;
import en.ubb.pet_shop.web.dto.PetDto;
import org.springframework.stereotype.Component;

@Component
public class PetConverter extends BaseEntityConverter<Pet, PetDto>{
    @Override
    public Pet convertDtoToModel(PetDto dto) {
        Pet model = new Pet();
        model.setId(dto.getId());
        model.setName(dto.getName());
        model.setSpecies(dto.getSpecies());
        model.setSellingPrice(dto.getSellingPrice());
        return model;
    }

    @Override
    public PetDto convertModelToDto(Pet model) {
        PetDto dto = new PetDto();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setSpecies(model.getSpecies());
        dto.setSellingPrice(model.getSellingPrice());
        return dto;
    }
}

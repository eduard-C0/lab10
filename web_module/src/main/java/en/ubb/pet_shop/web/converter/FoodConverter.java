package en.ubb.pet_shop.web.converter;

import en.ubb.pet_shop.core.domain.Food;
import en.ubb.pet_shop.core.domain.Person;
import en.ubb.pet_shop.web.dto.FoodDto;
import en.ubb.pet_shop.web.dto.PersonDto;
import org.springframework.stereotype.Component;

@Component
public class FoodConverter extends BaseEntityConverter<Food, FoodDto>{
    @Override
    public Food convertDtoToModel(FoodDto dto) {
        var model = new Food();
        model.setId(dto.getId());
        model.setName(dto.getName());
        model.setSpecies(dto.getSpecies());
        model.setDescription(dto.getDescription());
        model.setPrice(dto.getPrice());

        return model;
    }

    @Override
    public FoodDto convertModelToDto(Food model) {
        FoodDto dto = new FoodDto();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setSpecies(model.getSpecies());
        dto.setDescription(model.getDescription());
        dto.setPrice(model.getPrice());
        return dto;
    }

}

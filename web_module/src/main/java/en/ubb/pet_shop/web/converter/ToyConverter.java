package en.ubb.pet_shop.web.converter;
import en.ubb.pet_shop.core.domain.Food;
import en.ubb.pet_shop.core.domain.Toy;
import en.ubb.pet_shop.core.domain.Transaction;
import en.ubb.pet_shop.web.dto.FoodDto;
import en.ubb.pet_shop.web.dto.ToyDto;
import en.ubb.pet_shop.web.dto.TransactionDto;
import org.springframework.stereotype.Component;

@Component
public class ToyConverter extends BaseEntityConverter<Toy, ToyDto>{
    @Override
    public Toy convertDtoToModel(ToyDto dto) {
        Toy model = new Toy();
        model.setId(dto.getId());
        model.setMaterial(dto.getMaterial());
        model.setName(dto.getName());
        model.setSpecies(dto.getSpecies());
        model.setPrice(dto.getPrice());
        return model;
    }

    @Override
    public ToyDto convertModelToDto(Toy model) {
        ToyDto dto = new ToyDto();
        dto.setId(model.getId());
        dto.setMaterial(model.getMaterial());
        dto.setName(model.getName());
        dto.setSpecies(model.getSpecies());
        dto.setPrice(model.getPrice());
        return dto;
    }
}

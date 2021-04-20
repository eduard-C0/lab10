package en.ubb.pet_shop.web.converter;

import en.ubb.pet_shop.core.domain.BaseEntity;
import en.ubb.pet_shop.web.dto.BaseEntityDto;

public interface Converter <Model extends BaseEntity<Integer>, Dto extends BaseEntityDto> {
    Model convertDtoToModel(Dto dto);
    Dto convertModelToDto(Model model);
}

package en.ubb.pet_shop.web.converter;

import en.ubb.pet_shop.core.domain.BaseEntity;
import en.ubb.pet_shop.web.dto.BaseEntityDto;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


public abstract class BaseEntityConverter<Model extends BaseEntity<Integer>, Dto extends BaseEntityDto> implements Converter<Model, Dto> {
    public Set<Integer>  convertModelsToIds(Set<Model> models) {
        return models.stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toSet());
    }
    public Set<Integer> convertDtosToIds(Set<Dto> dtos) {
        return dtos.stream()
                .map(BaseEntityDto::getId)
                .collect(Collectors.toSet());
    }
    public Set<Dto> convertModelsToDtos(Collection<Model> models) {
        return models.stream()
                .map(this::convertModelToDto)
                .collect(Collectors.toSet());
    }
}

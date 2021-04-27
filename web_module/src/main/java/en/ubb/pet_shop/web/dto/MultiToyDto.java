package en.ubb.pet_shop.web.dto;

import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data

public class MultiToyDto {
    private Set<ToyDto> toys;
}

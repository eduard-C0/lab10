package en.ubb.pet_shop.web.dto;

import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class MultiPersonDto {
    private Set<PersonDto> people;
}

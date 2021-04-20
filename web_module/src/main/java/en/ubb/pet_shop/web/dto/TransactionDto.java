package en.ubb.pet_shop.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
public class TransactionDto extends BaseEntityDto {
    private int person;
    private int pet;
}

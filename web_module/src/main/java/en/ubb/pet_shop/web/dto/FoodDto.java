package en.ubb.pet_shop.web.dto;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
public class FoodDto extends BaseEntityDto {
    private String name;
    private String species;
    private String description;
    private double price;
}

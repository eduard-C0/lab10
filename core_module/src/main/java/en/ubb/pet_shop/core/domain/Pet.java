package en.ubb.pet_shop.core.domain;

import lombok.*;

import javax.persistence.Entity;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Pet extends BaseEntity<Integer>  {
    private String name;
    private String species;
    private double sellingPrice;
}
package en.ubb.pet_shop.core.domain;

import lombok.*;

import javax.persistence.Entity;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)

public class Person extends BaseEntity<Integer>  {
    private String name;
    private double budget;
}
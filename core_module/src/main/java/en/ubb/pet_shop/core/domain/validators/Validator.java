package en.ubb.pet_shop.core.domain.validators;

public interface Validator<T> {
    void validate(T object) throws ValidatorException;
}

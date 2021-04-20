package en.ubb.pet_shop.web.domain.validators;

public interface Validator<T> {
    void validate(T object) throws ValidatorException;
}

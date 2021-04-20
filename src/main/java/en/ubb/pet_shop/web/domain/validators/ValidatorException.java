package en.ubb.pet_shop.web.domain.validators;

/**
 * @author radu.
 */

public class ValidatorException extends PetShopException {
    public ValidatorException(String message) {
        super(message);
    }

    public ValidatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidatorException(Throwable cause) {
        super(cause);
    }

}

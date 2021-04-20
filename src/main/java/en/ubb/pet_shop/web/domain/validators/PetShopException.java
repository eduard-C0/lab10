package en.ubb.pet_shop.web.domain.validators;

/**
 * Created by radu.
 */
public class PetShopException extends RuntimeException{

    public PetShopException(String message) {
        super(message);
    }

    public PetShopException(String message, Throwable cause) {
        super(message, cause);
    }

    public PetShopException(Throwable cause) {
        super(cause);
    }
}

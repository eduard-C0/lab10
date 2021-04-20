package en.ubb.pet_shop.web.repository;

import en.ubb.pet_shop.web.domain.validators.PetShopException;

public class BaseFileRepositoryException extends PetShopException {
    public BaseFileRepositoryException(String message) {
        super(message);
    }

    public BaseFileRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseFileRepositoryException(Throwable cause) {
        super(cause);
    }

}

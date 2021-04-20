package en.ubb.pet_shop.web.repository.database;

import en.ubb.pet_shop.web.repository.BaseFileRepositoryException;

public class DatabaseException extends BaseFileRepositoryException {

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseException(Throwable cause) {
        super(cause);
    }
}

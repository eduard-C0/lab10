package en.ubb.pet_shop.web.repository.csv_file;

import en.ubb.pet_shop.web.repository.BaseFileRepositoryException;

public class CsvException extends BaseFileRepositoryException {

    public CsvException(String message) {
        super(message);
    }

    public CsvException(String message, Throwable cause) {
        super(message, cause);
    }

    public CsvException(Throwable cause) {
        super(cause);
    }
}

package en.ubb.pet_shop.web.repository.file;

import en.ubb.pet_shop.web.repository.BaseFileRepositoryException;

public class XmlException extends BaseFileRepositoryException {
    public XmlException(String message) {
        super(message);
    }

    public XmlException(String message, Throwable cause) {
        super(message, cause);
    }

    public XmlException(Throwable cause) {
        super(cause);
    }

}

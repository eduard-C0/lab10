package en.ubb.pet_shop.web.domain.validators;

import en.ubb.pet_shop.web.domain.Transaction;

import java.util.Optional;

public class TransactionValidator implements Validator<Transaction>{
    @Override
    /**
     * validates my entity
     * @param object must be of type transaction
     * @return none
     * @throws ValidatorException
     */
    public void validate(Transaction object) throws ValidatorException
    {
        Optional.of(object).filter(transaction -> transaction.getId()>=0).orElseThrow(()-> {throw new ValidatorException("id must be positive");});
    }
}

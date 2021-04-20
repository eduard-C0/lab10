package en.ubb.pet_shop.web.converter;

import en.ubb.pet_shop.core.domain.Transaction;
import en.ubb.pet_shop.web.dto.TransactionDto;
import org.springframework.stereotype.Component;

@Component
public class TransactionConverter extends BaseEntityConverter<Transaction, TransactionDto> {
    @Override
    public Transaction convertDtoToModel(TransactionDto dto) {
        Transaction model = new Transaction();
        model.setId(dto.getId());
        model.setPerson(dto.getPerson());
        model.setPet(dto.getPet());
        return model;
    }

    @Override
    public TransactionDto convertModelToDto(Transaction model) {
        TransactionDto dto = new TransactionDto();
        dto.setId(model.getId());
        dto.setPerson(model.getPerson());
        dto.setPet(model.getPet());
        return dto;
    }
}

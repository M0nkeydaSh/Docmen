package ru.imsit.diplom.docmen.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.imsit.diplom.docmen.enums.StatesEnum;
import ru.imsit.diplom.docmen.mapper.DocCardMapper;
import ru.imsit.diplom.docmen.repository.DocCardRepository;
import ru.imsit.diplom.docmen.service.HistoryService;

import java.util.UUID;


@Component
@RequiredArgsConstructor
public class DocCardHelper {

    private final DocCardMapper docCardMapper;

    private final DocCardRepository docCardRepository;

    private final HistoryService historyService;

    public void setDocCardState(UUID docCardId, String state) {
        var docCard = docCardRepository.findById(docCardId).orElseThrow(() -> new RuntimeException("Карточка документа не найдена"));
        docCard.setState(StatesEnum.getByStateValue(state));
        historyService.create(docCardId,state);
        docCardMapper.toDocCardDto(docCardRepository.save(docCard));
    }

}

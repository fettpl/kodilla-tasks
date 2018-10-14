package com.crud.tasks.trello.facade;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TrelloFacade {

    @Autowired
    private TrelloService trelloService;

    @Autowired
    private TrelloMapper trelloMapper;

    public List<TrelloBoard> fetchTrelloBoards() {
        return trelloMapper.mapToBoards(trelloService.fetchTrelloBoards());
    }
}

package com.crud.tasks.trello.facade;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TrelloFacadeTestSuite {

    @InjectMocks
    private TrelloFacade trelloFacade;

    @Mock
    private TrelloService trelloService;

    @Mock
    private TrelloMapper trelloMapper;

    @Mock
    private TrelloValidator trelloValidator;

    @Test
    public void shouldCreateCard() {
        // Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("name", "description", "1", "1");
        TrelloCard trelloCard = new TrelloCard("cardname", "description", "2", "2");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("1", new TrelloBadgeDto(1,
                new TrelloAttachmentDto(new TrelloDto(1, 1))), "name", "http://www.trello.com");

        // When
        when(trelloMapper.mapToCard(trelloCardDto)).thenReturn(trelloCard);
        when(trelloMapper.mapToCardDto(trelloCard)).thenReturn(trelloCardDto);
        when(trelloService.createTrelloCard(trelloCardDto)).thenReturn(createdTrelloCardDto);

        // Then
        CreatedTrelloCardDto card = trelloFacade.createCard(trelloCardDto);
        assertEquals(createdTrelloCardDto, card);
    }

    @Test
    public void shouldFetchTrelloBoards() {
        // Given
        List<TrelloList> mappedTrelloLists = new ArrayList<>();
        mappedTrelloLists.add(new TrelloList("1", "listname", false));

        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("2", "dtoListname", false));

        List<TrelloBoard> mappedTrelloBoards = new ArrayList<>();
        mappedTrelloBoards.add(new TrelloBoard("3", "boardname", mappedTrelloLists));

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("4", "dtoBoardname", trelloLists));

        // When
        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(trelloMapper.mapToBoards(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(trelloMapper.mapToBoardsDto(anyList())).thenReturn(new ArrayList<>());
        when(trelloValidator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(new ArrayList<>());

        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchTrelloBoards();

        // Then
        assertNotNull(trelloBoardDtos);
        assertEquals(1, trelloBoardDtos.size());

        trelloBoardDtos.forEach(trelloBoardDto -> {
            assertEquals("4", trelloBoardDto.getId());
            assertEquals("dtoBoardname", trelloBoardDto.getName());

            trelloBoardDto.getLists().forEach(trelloListDto -> {
                    assertEquals("1", trelloListDto.getId());
                    assertEquals("listname", trelloListDto.getName());
                    assertEquals(false, trelloListDto.isClosed());
            });
        });

        trelloMapper.mapToList(trelloLists).forEach(trelloList -> {
            assertEquals("1", trelloList.getId());
        });
    }
}

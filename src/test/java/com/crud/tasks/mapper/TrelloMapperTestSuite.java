package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TrelloMapperTestSuite {

    @InjectMocks
    private TrelloMapper trelloMapper;

    @Test
    public void testMapToBoards(){
        // Given
        TrelloList trelloList = new TrelloList("1", "list", false);
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(trelloList);
        TrelloBoard trelloBoard = new TrelloBoard("1", "board", trelloLists);
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(trelloBoard);

        // When
        List<TrelloBoardDto> trelloBoardDtos = trelloMapper.mapToBoardsDto(trelloBoards);

        // Then
        assertEquals(1, trelloBoardDtos.size());
    }

    @Test
    public void testMapToBoardsDto(){
        // Given
        TrelloListDto trelloList = new TrelloListDto("1", "list", false);
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(trelloList);
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("1", "boardDto", trelloLists);
        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        trelloBoardDtos.add(trelloBoardDto);

        // When
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardDtos);

        // Then
        assertEquals(1, trelloBoardDtos.size());
    }

    @Test
    public void testMapToList(){
        TrelloListDto trelloListDto = new TrelloListDto("1", "list", false);
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(trelloListDto);

        // When
        List<TrelloList> trelloListDtos = trelloMapper.mapToList(trelloLists);

        // Then
        assertEquals(1, trelloListDtos.size());
    }

    @Test
    public void testMapToListDto(){
        TrelloList trelloList = new TrelloList("1", "list", false);
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(trelloList);

        // When
        List<TrelloListDto> trelloListDtos = trelloMapper.mapToListDto(trelloLists);

        // Then
        assertEquals(1, trelloListDtos.size());
    }

    @Test
    public void testMapToCard(){
        // Given
        TrelloCard trelloCard = new TrelloCard("trelloCard", "description", "1", "1");

        // When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);

        // Then
        assertEquals("1", trelloCardDto.getListId());
    }

    @Test
    public void testMapToCardDto(){
        // Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("trelloCardDto", "description", "1", "1");

        // When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);

        // Then
        assertEquals("1", trelloCard.getPos());
    }
}

package com.crud.tasks.trello.client;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.config.TrelloConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class TrelloClient {
    private static final String MEMBERS = "members";
    private static final String BOARDS = "boards";
    private static final String TRELLO_KEY = "key";
    private static final String TRELLO_TOKEN = "token";
    private static final String FILTER = "filter";
    private static final String FIELDS = "fields";
    private static final String LISTS = "lists";
    private static final String CARDS = "cards";
    private static final String NAME = "name";
    private static final String DESC = "desc";
    private static final String POS = "pos";
    private static final String IDLIST = "idList";
    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloClient.class);

    @Autowired
    private TrelloConfig trelloConfig;

    @Autowired
    private RestTemplate restTemplate;

    public List<TrelloBoardDto> getTrelloBoards() {
        URI getUrl = UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/" + MEMBERS + "/"
                + trelloConfig.getTrelloUsername() + "/" + BOARDS)
                .queryParam(TRELLO_KEY, trelloConfig.getTrelloAppKey())
                .queryParam(TRELLO_TOKEN, trelloConfig.getTrelloToken())
                .queryParam(FIELDS, "name,id")
                .queryParam(LISTS, "all").build().encode().toUri();

        try {
            TrelloBoardDto[] boardsResponse = restTemplate.getForObject(getUrl, TrelloBoardDto[].class);
            return Arrays.asList(Optional.ofNullable(boardsResponse).orElse(new TrelloBoardDto[0]));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public CreatedTrelloCard createNewCard(TrelloCardDto trelloCardDto) {
        URI postUrl = UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/" + CARDS)
                .queryParam(TRELLO_KEY, trelloConfig.getTrelloAppKey())
                .queryParam(TRELLO_TOKEN, trelloConfig.getTrelloToken())
                .queryParam(NAME, trelloCardDto.getName())
                .queryParam(DESC, trelloCardDto.getDescription())
                .queryParam(POS, trelloCardDto.getPos())
                .queryParam(IDLIST, trelloCardDto.getListId()).build().encode().toUri();

        return restTemplate.postForObject(postUrl, null, CreatedTrelloCard.class);
    }

//    private URI getTrelloUri() {
//        return UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/" + MEMBERS + "/"
//                + trelloConfig.getTrelloUsername() + "/" + BOARDS)
//                .queryParam(TRELLO_KEY, trelloConfig.getTrelloAppKey())
//                .queryParam(TRELLO_TOKEN, trelloConfig.getTrelloToken())
//                .queryParam(FIELDS, "name,ID")
//                .queryParam(LISTS, "all")
//                .queryParam(FILTER, "all").build().encode().toUri();
//    }
}

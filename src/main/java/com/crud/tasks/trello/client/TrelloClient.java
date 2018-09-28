package com.crud.tasks.trello.client;

import com.crud.tasks.domain.TrelloBoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;

    @Value("${trello.app.username}")
    private String trelloUsername;

    @Value("${trello.app.key}")
    private String trelloAppKey;

    @Value("${trello.app.token}")
    private String trelloToken;

    @Autowired
    private RestTemplate restTemplate;

    public List<TrelloBoardDto> getTrelloBoards() {
        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(getTrelloUri(), TrelloBoardDto[].class);

        return Arrays.asList(Optional.ofNullable(boardsResponse).orElse(new TrelloBoardDto[0]));
    }

    private URI getTrelloUri() {

        return UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/" + MEMBERS + "/" + trelloUsername + "/" + BOARDS)
                .queryParam(FILTER, "all")
                .queryParam(FIELDS, "id,name")
                .queryParam(LISTS, "all")
                .queryParam(TRELLO_KEY, trelloAppKey)
                .queryParam(TRELLO_TOKEN, trelloToken).build().encode().toUri();
    }
}

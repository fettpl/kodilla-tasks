package com.crud.tasks.trello.client;

import com.crud.tasks.domain.TrelloBoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class TrelloClient {
    private static final String TRELLO_KEY = "key";
    private static final String TRELLO_TOKEN = "token";
    private static final String MEMBERS = "members";
    private static final String BOARDS = "boards";
    private static final String FIELDS = "fields";

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;

    @Value("$(trello.api.username}")
    private String trelloUsername;

    @Value("${trello.app.key}")
    private String trelloAppKey;

    @Value("${trello.app.token}")
    private String trelloToken;

    @Autowired
    private RestTemplate restTemplate;

    public List<TrelloBoardDto> getTrelloBoards() {
        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(getTrelloUri(), TrelloBoardDto[].class);

        if (boardsResponse != null) {
            return Arrays.asList(boardsResponse);
        }
        return new ArrayList<>();
    }

    private URI getTrelloUri() {

        return UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/" + MEMBERS + "/" + trelloUsername + "/" + BOARDS)
                .queryParam(TRELLO_KEY, trelloToken)
                .queryParam(TRELLO_TOKEN, trelloToken)
                .queryParam(FIELDS, "name,id").build().encode().toUri();
    }
}

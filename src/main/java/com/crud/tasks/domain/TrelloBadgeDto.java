package com.crud.tasks.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@AllArgsConstructor
public class TrelloBadgeDto {

    @JsonProperty(value = "votes")
    private int votes;

    @JsonProperty(value = "attachmentsByType")
    private TrelloAttachmentDto trelloAttachmentDto;
}

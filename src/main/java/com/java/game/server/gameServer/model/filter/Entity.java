package com.java.game.server.gameServer.model.filter;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Entity implements Serializable {

    private String entity;
    private String type;
    private int startIndex;
    private int endIndex;
    private Resolution resolution;


}

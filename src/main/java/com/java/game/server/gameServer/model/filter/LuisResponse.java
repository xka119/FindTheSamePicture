package com.java.game.server.gameServer.model.filter;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor(staticName = "valueOf")

public class LuisResponse implements Serializable {

    private String query;
    private Intent topScoringIntent;
    private ArrayList<Intent> intents;
    private ArrayList<Entity> entities;
}

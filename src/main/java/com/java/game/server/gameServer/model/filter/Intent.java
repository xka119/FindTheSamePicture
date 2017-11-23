package com.java.game.server.gameServer.model.filter;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor(staticName = "valueOf")
public class Intent implements Serializable {

    private String intent;
    private float score;

}

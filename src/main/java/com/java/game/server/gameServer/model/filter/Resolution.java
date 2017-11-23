package com.java.game.server.gameServer.model.filter;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor(staticName = "valueOf")

public class Resolution implements Serializable {
    private ArrayList<String> values;

}

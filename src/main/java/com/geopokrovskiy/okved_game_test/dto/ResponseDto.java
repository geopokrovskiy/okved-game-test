package com.geopokrovskiy.okved_game_test.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder(toBuilder = true)
public class ResponseDto {
    private String phoneNumber;
    private String okvedCode;
    private String okvedName;
    private int matchingLength;
}

package com.geopokrovskiy.okved_game_test.service;

import com.geopokrovskiy.okved_game_test.dto.RequestDto;
import com.geopokrovskiy.okved_game_test.dto.ResponseDto;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class OkvedProcessor {
    private final OkvedService okvedService;
    private final NumberNormalizer numberNormalizer;

    public ResponseDto processNumber(RequestDto requestDto){
        String number = requestDto.getPhoneNumber();
        String normalizePhoneNumber = numberNormalizer.normalizePhoneNumber(number);
        return okvedService.processNumber(normalizePhoneNumber);
    }
}

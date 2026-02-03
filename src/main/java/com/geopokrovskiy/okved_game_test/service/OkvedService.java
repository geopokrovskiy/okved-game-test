package com.geopokrovskiy.okved_game_test.service;

import com.geopokrovskiy.okved_game_test.dto.ResponseDto;
import com.geopokrovskiy.okved_game_test.entity.OkvedItem;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@Data
public class OkvedService {
    private final OkvedScheduler okvedScheduler;

    public ResponseDto processNumber(String phoneNumber) {
        List<OkvedItem> okvedItems = okvedScheduler.getItems();
        OkvedItem bestItem = null;
        int bestMatchLength = 0;

        phoneNumber = phoneNumber.replace("\\D", "");

        for (OkvedItem okvedItem : okvedItems) {
            String code = okvedItem.getCode();
            code = code.replace(".", "");
            if (phoneNumber.endsWith(code)) {
                int matchLength = code.length();

                if (matchLength > bestMatchLength) {
                    bestItem = okvedItem;
                    bestMatchLength = matchLength;
                }
            }
        }

        if (bestItem == null) {
            return reserveStrategyResponseDto(phoneNumber, okvedItems);
        }

        return ResponseDto.builder()
                .okvedCode(bestItem.getCode())
                .okvedName(bestItem.getItem())
                .phoneNumber(phoneNumber)
                .matchingLength(bestMatchLength)
                .build();
    }

    private ResponseDto reserveStrategyResponseDto(String phoneNumber, List<OkvedItem> okvedItems) {

        // ищем первое совпадение по двум последним цифре
        for (OkvedItem okvedItem : okvedItems) {
            String initialCode = okvedItem.getCode();
            String code = initialCode;
            code = code.replace(".", "");
            int codeLength = code.length();
            code = code.substring(codeLength - 2);
            if (phoneNumber.endsWith(code)) {
                return ResponseDto.builder()
                        .okvedCode(initialCode)
                        .okvedName(okvedItem.getItem())
                        .matchingLength(2)
                        .phoneNumber(phoneNumber)
                        .build();
            }
        }

        // ищем первое совпадение по последней цифре
        OkvedItem bestItem = okvedItems.stream()
                .filter(oi -> phoneNumber.endsWith(oi.getCode().substring(oi.getCode().length() - 1)))
                .findFirst().orElse(okvedItems.getFirst());


        return ResponseDto.builder()
                .okvedCode(bestItem.getCode())
                .okvedName(bestItem.getItem())
                .matchingLength(1)
                .phoneNumber(phoneNumber)
                .build();
    }
}

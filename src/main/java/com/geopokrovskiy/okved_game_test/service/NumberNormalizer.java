package com.geopokrovskiy.okved_game_test.service;

import com.geopokrovskiy.okved_game_test.exception.NotRussianNumberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NumberNormalizer {

    public static String NOT_RUSSIAN_MOBILE_NUMBER_ERROR = "The number doesn't match to Russian mobile number standards";
    private static final int RUS_PHONE_LENGTH = 12;

    public String normalizePhoneNumber(String phoneNumber) {

        log.info("Processing phone number {}", phoneNumber);

        String result = phoneNumber
                .replaceAll("[^\\d+]", "")   // убираем всё, кроме цифр и +
                .replaceAll("(?!^)\\+", ""); // убираем +, если он не первый
        if (result.startsWith("89")) {
            result = "+7" + result.substring(1);
        } else if (result.startsWith("0079")) {
            result = "+7" + result.substring(3);
        } else if (result.startsWith("79")) {
            result = "+" + result;
        }

        if (!(result.startsWith("+79") && result.length() == RUS_PHONE_LENGTH)) {
            log.error("{} does not match to Russian mobile number standards", phoneNumber);
            throw new NotRussianNumberException(NOT_RUSSIAN_MOBILE_NUMBER_ERROR);
        }

        log.info("Phone number has been successfully normalized {} ", phoneNumber);
        return result;
    }
}

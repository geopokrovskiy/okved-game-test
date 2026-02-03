package com.geopokrovskiy.okved_game_test.unit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geopokrovskiy.okved_game_test.dto.ResponseDto;
import com.geopokrovskiy.okved_game_test.service.OkvedScheduler;
import com.geopokrovskiy.okved_game_test.service.OkvedService;
import com.geopokrovskiy.okved_game_test.unit.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class OkvedServiceTest {

    @Autowired
    private OkvedScheduler okvedScheduler;

    @Autowired
    private OkvedService okvedService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        okvedScheduler.processResponseBody(TestUtils.OKVED_TEST_JSON);
        objectMapper = okvedScheduler.getObjectMapper();
    }

    @Test()
    @DisplayName("Matching length 6")
    void testCase1() throws JsonProcessingException {
        ResponseDto actualResponseDto = okvedService.processNumber(TestUtils.phoneNumber1);
        ResponseDto expectedResponseDto = objectMapper.readValue(TestUtils.response1, ResponseDto.class);

        assertEquals(expectedResponseDto.getOkvedCode(), actualResponseDto.getOkvedCode());
        assertEquals(expectedResponseDto.getOkvedName(), actualResponseDto.getOkvedName());
        assertEquals(expectedResponseDto.getMatchingLength(), actualResponseDto.getMatchingLength());
        assertEquals(expectedResponseDto.getPhoneNumber(), actualResponseDto.getPhoneNumber());
    }

    @Test()
    @DisplayName("Matching length 4")
    void testCase2() throws JsonProcessingException {
        ResponseDto actualResponseDto = okvedService.processNumber(TestUtils.phoneNumber2);
        ResponseDto expectedResponseDto = objectMapper.readValue(TestUtils.response2, ResponseDto.class);

        assertEquals(expectedResponseDto.getOkvedCode(), actualResponseDto.getOkvedCode());
        assertEquals(expectedResponseDto.getOkvedName(), actualResponseDto.getOkvedName());
        assertEquals(expectedResponseDto.getMatchingLength(), actualResponseDto.getMatchingLength());
        assertEquals(expectedResponseDto.getPhoneNumber(), actualResponseDto.getPhoneNumber());
    }

    @Test()
    @DisplayName("Matching length 2, reserve strategy")
    void testCase3() throws JsonProcessingException {
        ResponseDto actualResponseDto = okvedService.processNumber(TestUtils.phoneNumber3);
        ResponseDto expectedResponseDto = objectMapper.readValue(TestUtils.response3, ResponseDto.class);

        assertEquals(expectedResponseDto.getOkvedCode(), actualResponseDto.getOkvedCode());
        assertEquals(expectedResponseDto.getOkvedName(), actualResponseDto.getOkvedName());
        assertEquals(expectedResponseDto.getMatchingLength(), actualResponseDto.getMatchingLength());
        assertEquals(expectedResponseDto.getPhoneNumber(), actualResponseDto.getPhoneNumber());
    }
}

package com.geopokrovskiy.okved_game_test.controller;

import com.geopokrovskiy.okved_game_test.dto.ErrorResponseDto;
import com.geopokrovskiy.okved_game_test.dto.RequestDto;
import com.geopokrovskiy.okved_game_test.dto.ResponseDto;
import com.geopokrovskiy.okved_game_test.service.OkvedProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/okved")
@Slf4j
@RequiredArgsConstructor
public class OkvedController {

    public final OkvedProcessor okvedProcessor;

    @PostMapping
    public ResponseEntity<?> getNumber(@RequestBody RequestDto requestDto) {

        log.debug("Received request: " + requestDto);

        try {
            ResponseDto responseDto = okvedProcessor.processNumber(requestDto);
            return new ResponseEntity<>(responseDto, HttpStatusCode.valueOf(200));
        } catch (RuntimeException e) {
            ErrorResponseDto responseDto = new ErrorResponseDto(e.getMessage());
            return new ResponseEntity<>(responseDto, HttpStatusCode.valueOf(400));
        }
    }
}

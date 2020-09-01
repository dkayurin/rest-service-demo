package com.dev.task.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dev.task.model.CompoundIndex;
import com.dev.task.service.InputDataProcessingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class Controller {

    private final InputDataProcessingService processingService;

    @GetMapping
    public ResponseEntity<String> A(@RequestParam("inputData") String inputData) {
        return new ResponseEntity<>(processingService.processInputString(inputData), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> B(@RequestBody CompoundIndex newIndex) {
        processingService.updateIndexValue(newIndex);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

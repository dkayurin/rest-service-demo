package com.dev.task.service;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dev.task.config.CompoundIndexComponent;
import com.dev.task.model.CompoundIndex;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class InputDataProcessingService {

    private static final String EX_VALIDATION_FAILED = "Processing failed for the value '%s'. Not an Integer";

    private final CompoundIndexComponent indexComponent;

    public String processInputString(String inputData) {
        log.info("Data processing requested for value: " + inputData);
        final String[] valuesToProcess = inputData.split(",");
        final int index = indexComponent.getCompoundIndex();
        return increaseEachValue(valuesToProcess, index);
    }

    String increaseEachValue(String[] numericStrings, int index) {
        log.info("Increasing provided values...");

        return Arrays.stream(numericStrings)
            .mapToInt(item -> processValue(item, index))
            .mapToObj(Objects::toString)
            .collect(Collectors.joining(","));
    }

    int processValue(String dataToValidate, int index) {
        try {
            final var currentIntValue = Integer.parseInt(dataToValidate);
            return currentIntValue + index;
        } catch (NumberFormatException e) {
            var message = String.format(EX_VALIDATION_FAILED, dataToValidate);
            log.error(message, e);
            throw new IllegalArgumentException(message);
        }
    }

    public void updateIndexValue(CompoundIndex newIndex) {
        final var newIndexValue = newIndex.getUpdatedCompoundIndex();
        log.info("Request to update compound index with value: " + newIndexValue);
        indexComponent.updateCompoundIndex(newIndexValue);
    }
}

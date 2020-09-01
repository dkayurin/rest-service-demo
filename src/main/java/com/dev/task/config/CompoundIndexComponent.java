package com.dev.task.config;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CompoundIndexComponent {

    private static final BlockingDeque<Integer> INDEX_LOG = new LinkedBlockingDeque<>();
    private static final int DEFAULT_COMPOUND_INDEX = 1;

    static {
        INDEX_LOG.push(DEFAULT_COMPOUND_INDEX);
    }

    public Integer getCompoundIndex() {
        return INDEX_LOG.peek();
    }

    public void updateCompoundIndex(Integer newIndex) {
        try {
            INDEX_LOG.putFirst(newIndex);
        } catch (InterruptedException e) {
            log.error("Failed to update compound index with value: " + newIndex, e);
            Thread.currentThread().interrupt();
        }
    }

}

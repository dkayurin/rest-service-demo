package com.dev.task.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.dev.task.config.CompoundIndexComponent;

@RunWith(MockitoJUnitRunner.class)
public class InputDataProcessingServiceTest {

    @Mock
    private CompoundIndexComponent indexComponent;

    @Spy
    @InjectMocks
    private InputDataProcessingService processingService;

    private static final String VALID_INPUT_STRING = "1,2,3";
    private static final String OUTPUT_STRING = "2,3,4";
    private static final String[] VALID_STRINGS_ARRAY = {"1", "2", "3"};
    private static final int DEFAULT_COMPOUND_INDEX = 1;

    @Test
    public void testProcessInputString() {
        //given
        given(indexComponent.getCompoundIndex())
            .willReturn(DEFAULT_COMPOUND_INDEX);
        willReturn(OUTPUT_STRING)
            .given(processingService).increaseEachValue(any(), anyInt());
        //when
        String actualString = processingService.processInputString(VALID_INPUT_STRING);
        //then
        assertThat(actualString).isEqualTo(OUTPUT_STRING);
        verify(indexComponent, times(1)).getCompoundIndex();
        verify(processingService).increaseEachValue(VALID_STRINGS_ARRAY, DEFAULT_COMPOUND_INDEX);
    }

    @Test
    public void testIncreaseEachValue() {
        //when
        String actualString = processingService.increaseEachValue(VALID_STRINGS_ARRAY, DEFAULT_COMPOUND_INDEX);
        //then
        assertThat(actualString).isEqualTo(OUTPUT_STRING);
    }

    @Test
    public void testProcessValue() {
        //when
        int actualInt = processingService.processValue("1", DEFAULT_COMPOUND_INDEX);
        //then
        assertThat(actualInt).isEqualTo(2);
    }

    @Test
    public void testProcessValue_IntegerMaxValue() {
        //when
        int actualInt = processingService.processValue(String.valueOf(Integer.MAX_VALUE), DEFAULT_COMPOUND_INDEX);
        //then
        assertThat(actualInt).isEqualTo(Integer.MIN_VALUE);
    }

    @Test
    public void testProcessValue_OutOfIntegerRange() {
        //given
        var invalidValue = String.valueOf((long)Integer.MAX_VALUE + 1);
        //when
        Throwable t = catchThrowable(() -> processingService.processValue(invalidValue, DEFAULT_COMPOUND_INDEX));
        //then
        assertThat(t).isInstanceOf(RuntimeException.class);
    }
}

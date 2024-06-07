package pl.edu.pja.trainmate.core.common.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class WeekNumberCalculatorTest {

    @Test
    void shouldCalculateSameWeek() {
        var calculator = new WeekNumberCalculator();

        assertEquals(1, calculator.calculate(LocalDate.now()));
    }

    @Test
    void shouldCalculatePreviousWeek() {
        var calculator = new WeekNumberCalculator();

        assertEquals(2, calculator.calculate(LocalDate.now().minusWeeks(1)));
    }

    @Test
    void shouldCalculateTwoWeaksAgo() {
        var calculator = new WeekNumberCalculator();

        assertEquals(3, calculator.calculate(LocalDate.now().minusWeeks(2)));
    }
}
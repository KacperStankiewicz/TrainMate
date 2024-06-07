package pl.edu.pja.trainmate.core.common.utils;

import static java.util.Locale.ENGLISH;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import org.springframework.stereotype.Service;

@Service
public class WeekNumberCalculator {

    public Long calculate(LocalDate referenceDate) {
        var today = LocalDate.now();

        var todayWeekOfYear = today.get(WeekFields.of(ENGLISH).weekOfYear());
        var workoutStartWeekOfYear = referenceDate.get(WeekFields.of(ENGLISH).weekOfYear());

        return (long) (todayWeekOfYear - workoutStartWeekOfYear + 1);
    }
}

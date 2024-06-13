package pl.edu.pja.trainmate.core.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import pl.edu.pja.trainmate.core.domain.exercise.SetParams;

class SetParamsConverterTest {

    @Test
    void convertToDatabaseColumn() {
        //given
        var converter = new SetParamsConverter();
        var setsParams = SetParams.builder()
            .reportedRir(1)
            .reportedWeight(2)
            .reportedRepetitions(3)
            .set(1)
            .build();
        //when
        var convertedString = converter.convertToDatabaseColumn(List.of(setsParams));

        //then
        assertEquals("[{\"reportedRepetitions\":3,\"reportedWeight\":2,\"reportedRir\":1,\"set\":1}]", convertedString);
    }

    @Test
    void convertToDatabaseColumnWhenEmptyList() {
        //given
        var converter = new SetParamsConverter();

        //when
        var convertedString = converter.convertToDatabaseColumn(List.of());

        //then
        assertEquals("[]", convertedString);
    }

    @Test
    void convertToDatabaseColumnWhenNullGiven() {
        //given
        var converter = new SetParamsConverter();

        //when
        var convertedString = converter.convertToDatabaseColumn(null);

        //then
        assertEquals("[]", convertedString);
    }

    @Test
    void convertToDatabaseColumnWhenAllParamsAreNull() {
        //given
        var converter = new SetParamsConverter();
        var setsParams = SetParams.builder()
            .build();

        //when
        var convertedString = converter.convertToDatabaseColumn(List.of(setsParams));

        //then
        assertEquals("[{\"reportedRepetitions\":null,\"reportedWeight\":null,\"reportedRir\":null,\"set\":null}]", convertedString);
    }

    @Test
    void convertToDatabaseColumnWhenMultipleElementsGiven() {
        //given
        var converter = new SetParamsConverter();
        var setsParams = SetParams.builder()
            .reportedRir(1)
            .reportedWeight(2)
            .reportedRepetitions(3)
            .set(1)
            .build();

        var otherSetsParams = SetParams.builder()
            .reportedRir(4)
            .reportedWeight(5)
            .reportedRepetitions(6)
            .set(2)
            .build();

        //when
        var convertedString = converter.convertToDatabaseColumn(List.of(setsParams, otherSetsParams));

        //then
        assertEquals(
            "[{\"reportedRepetitions\":3,\"reportedWeight\":2,\"reportedRir\":1,\"set\":1},"
                + "{\"reportedRepetitions\":6,\"reportedWeight\":5,\"reportedRir\":4,\"set\":2}]",
            convertedString);
    }

    @Test
    void convertToEntityAttribute() {
        //given
        var converter = new SetParamsConverter();
        var stringToConvert = "[{\"reportedRepetitions\":3,\"reportedWeight\":2,\"reportedRir\":1,\"set\":1}]";

        //when
        var result = converter.convertToEntityAttribute(stringToConvert);

        //then
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getSet());
        assertEquals(3, result.get(0).getReportedRepetitions());
        assertEquals(2, result.get(0).getReportedWeight());
        assertEquals(1, result.get(0).getReportedRir());
    }

    @Test
    void convertToEntityAttributeWhenStringIsEmpty() {
        //given
        var converter = new SetParamsConverter();
        var stringToConvert = "";

        //when
        var result = converter.convertToEntityAttribute(stringToConvert);

        //then
        assertEquals(0, result.size());
    }

    @Test
    void convertToEntityAttributeWhenStringIsNull() {
        //given
        var converter = new SetParamsConverter();

        //when
        var result = converter.convertToEntityAttribute(null);

        //then
        assertEquals(0, result.size());
    }

    @Test
    void convertToEntityAttributeWhenStringIsEmptyArray() {
        //given
        var converter = new SetParamsConverter();
        var stringToConvert = "[]";

        //when
        var result = converter.convertToEntityAttribute(stringToConvert);

        //then
        assertEquals(0, result.size());
    }
}
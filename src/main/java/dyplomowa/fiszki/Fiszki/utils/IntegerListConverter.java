package dyplomowa.fiszki.Fiszki.utils;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Converter
public class IntegerListConverter implements AttributeConverter<List<Integer>, String> {
    private static final String SEPARATOR = ",";

    @Override
    public String convertToDatabaseColumn(List<Integer> integers) {
        return integers.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    @Override
    public List<Integer> convertToEntityAttribute(String s) {
        return Stream.of(s.split(SEPARATOR)).map(String::trim).map(Integer::parseInt).collect(Collectors.toList());
    }
}

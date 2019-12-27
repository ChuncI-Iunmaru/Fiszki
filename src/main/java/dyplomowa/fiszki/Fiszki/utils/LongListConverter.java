package dyplomowa.fiszki.Fiszki.utils;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Converter
public class LongListConverter implements AttributeConverter<List<Long>, String> {
    private static final String SEPARATOR = ",";

    @Override
    public String convertToDatabaseColumn(List<Long> longs) {
        if (longs == null || longs.isEmpty()) {
            return "";
        } else return longs.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    @Override
    public List<Long> convertToEntityAttribute(String s) {
        if (s == null || s.isEmpty()) {
            return Collections.emptyList();
        } else return Stream.of(s.split(SEPARATOR)).map(String::trim).map(Long::parseLong).collect(Collectors.toList());
    }
}

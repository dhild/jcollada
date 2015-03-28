package net.dryanhild.collada.common.parser;

import org.xmlpull.v1.XmlPullParser;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.function.Function;
import javax.xml.bind.DatatypeConverter;

public class ZonedDateTimeParser implements Function<XmlPullParser, ZonedDateTime> {

    private final SimpleTextParser textParser;

    public ZonedDateTimeParser(String tag) {
        this(XmlParser.COLLADA_NAMESPACE, tag);
    }

    public ZonedDateTimeParser(String namespace, String tag) {
        textParser = new SimpleTextParser(namespace, tag);
    }

    @Override
    public ZonedDateTime apply(XmlPullParser parser) {
        String time = textParser.apply(parser).toString();
        Calendar cal = DatatypeConverter.parseDateTime(time);
        return ZonedDateTime.from(cal.toInstant());
    }
}

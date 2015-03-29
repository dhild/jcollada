package net.dryanhild.collada.common.parser;

import org.xmlpull.v1.XmlPullParser;

import java.time.Instant;
import java.util.Calendar;
import java.util.function.Function;
import javax.xml.bind.DatatypeConverter;

public class InstantParser implements Function<XmlPullParser, Instant> {

    private final SimpleTextParser textParser;

    public InstantParser(String tag) {
        this(XmlParser.COLLADA_NAMESPACE, tag);
    }

    public InstantParser(String namespace, String tag) {
        textParser = new SimpleTextParser(namespace, tag);
    }

    @Override
    public Instant apply(XmlPullParser parser) {
        String time = textParser.apply(parser).toString();
        Calendar cal = DatatypeConverter.parseDateTime(time);
        return cal.toInstant();
    }
}

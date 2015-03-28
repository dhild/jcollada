package net.dryanhild.collada.common.parser;

import lombok.SneakyThrows;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.function.Function;

import static org.xmlpull.v1.XmlPullParser.END_TAG;
import static org.xmlpull.v1.XmlPullParser.START_TAG;
import static org.xmlpull.v1.XmlPullParser.TEXT;

public class SimpleTextParser implements Function<XmlPullParser, StringBuilder> {

    private final String namespace;
    private final String tag;

    public SimpleTextParser(String tag) {
        this(XmlParser.COLLADA_NAMESPACE, tag);
    }

    public SimpleTextParser(String namespace, String tag) {
        this.namespace = namespace;
        this.tag = tag;
    }

    @Override
    @SneakyThrows({IOException.class, XmlPullParserException.class})
    public StringBuilder apply(XmlPullParser parser) {
        parser.require(START_TAG, namespace, tag);
        parser.next();

        StringBuilder builder = new StringBuilder();
        while (parser.getEventType() != END_TAG) {
            if (parser.getEventType() == TEXT) {
                int[] startAndLength = new int[2];
                char[] text = parser.getTextCharacters(startAndLength);
                builder.append(text, startAndLength[0], startAndLength[1]);
            }
            parser.next();
        }
        // Make sure we're not reading too much
        parser.require(END_TAG, namespace, tag);

        return builder;
    }
}

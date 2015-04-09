package net.dryanhild.collada.common.parser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static org.xmlpull.v1.XmlPullParser.START_TAG;
import static org.xmlpull.v1.XmlPullParser.TEXT;

public class TextParser<OutputType> extends XmlParser<OutputType> {

    private BiConsumer<OutputType, CharSequence> charConsumer;

    public TextParser(String tag, Class<OutputType> type) {
        this(XmlParser.COLLADA_NAMESPACE, tag, type);
    }

    public TextParser(String namespace, String tag, Class<OutputType> type) {
        super(namespace, tag, type);
    }

    public void setCharConsumer(BiConsumer<OutputType, CharSequence> charConsumer) {
        this.charConsumer = charConsumer;
    }

    public <V> void setCharConsumer(BiConsumer<OutputType, V> charConsumer,
                                    Function<CharSequence, V> valueConsumer) {
        this.charConsumer = (outputType, sequence) -> charConsumer.accept(outputType, valueConsumer.apply(sequence));
    }

    @Override
    protected void parseInnerElements(XmlPullParser parser, OutputType object, int depth)
            throws XmlPullParserException, IOException {
        StringBuilder builder = new StringBuilder();
        int token = parser.next();
        while (depth <= parser.getDepth()) {
            switch (token) {
                case START_TAG:
                    token = processElement(parser, object);
                    break;
                case TEXT:
                    int[] startAndLength = new int[2];
                    char[] text = parser.getTextCharacters(startAndLength);
                    builder.append(text, startAndLength[0], startAndLength[1]);
                    token = parser.next();
                    break;
                default:
                    token = parser.next();
            }
        }
        charConsumer.accept(object, builder);
    }
}

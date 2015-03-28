package net.dryanhild.collada.common.parser;

import com.carrotsearch.hppc.FloatArrayList;
import lombok.SneakyThrows;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.function.BiConsumer;

import static org.xmlpull.v1.XmlPullParser.START_TAG;
import static org.xmlpull.v1.XmlPullParser.TEXT;

public class FloatParser<OutputType> implements BiConsumer<OutputType, XmlPullParser> {

    private FloatArrayList floats;

    @Override
    @SneakyThrows({IOException.class, XmlPullParserException.class})
    public void accept(OutputType object, XmlPullParser parser) {
        parser.require(START_TAG, null, null);
        parser.next();
        parser.require(TEXT, null, null);

        FloatArrayList floats = new FloatArrayList();

        int[] startAndLength = new int[2];
        char[] text = parser.getTextCharacters(startAndLength);
        final int maxIndex = startAndLength[0] + startAndLength[1];
        int i = startAndLength[0];
        while (i < (startAndLength[0] + startAndLength[1])) {
            while (Character.isWhitespace(text[i])) {
                i++;
            }
            int count = 0;
            while (!Character.isWhitespace(text[i + count]) && (i + count) < maxIndex) {
                count++;
            }
            if (count > 0) {
                floats.add(Float.valueOf(new String(text, i, count)));
            }
            i += count;
        }
        this.floats = floats;
    }

    public FloatArrayList getFloats() {
        return floats;
    }
}

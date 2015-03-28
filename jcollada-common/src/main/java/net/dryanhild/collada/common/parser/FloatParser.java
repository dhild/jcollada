package net.dryanhild.collada.common.parser;

import com.carrotsearch.hppc.FloatArrayList;
import org.xmlpull.v1.XmlPullParser;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FloatParser implements Function<XmlPullParser, FloatArrayList> {

    private static final Pattern pattern = Pattern.compile("\\S+");
    private final SimpleTextParser textParser;

    public FloatParser(String tag) {
        this(XmlParser.COLLADA_NAMESPACE, tag);
    }

    public FloatParser(String namespace, String tag) {
        textParser = new SimpleTextParser(namespace, tag);
    }

    @Override
    public FloatArrayList apply(XmlPullParser parser) {
        StringBuilder builder = textParser.apply(parser);
        FloatArrayList floats = new FloatArrayList();

        Matcher matcher = pattern.matcher(builder);
        while (matcher.find()) {
            floats.add(Float.parseFloat(matcher.group()));
        }
        return floats;
    }
}

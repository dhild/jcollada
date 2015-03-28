package net.dryanhild.collada.common.parser;

import com.carrotsearch.hppc.IntArrayList;
import org.xmlpull.v1.XmlPullParser;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IntParser implements Function<XmlPullParser, IntArrayList> {

    private static final Pattern pattern = Pattern.compile("\\S+");
    private final SimpleTextParser textParser;

    public IntParser(String tag) {
        this(XmlParser.COLLADA_NAMESPACE, tag);
    }

    public IntParser(String namespace, String tag) {
        textParser = new SimpleTextParser(namespace, tag);
    }

    @Override
    public IntArrayList apply(XmlPullParser parser) {
        StringBuilder builder = textParser.apply(parser);
        IntArrayList ints = new IntArrayList();

        Matcher matcher = pattern.matcher(builder);
        while (matcher.find()) {
            ints.add(Integer.parseInt(matcher.group()));
        }
        return ints;
    }

}

package net.dryanhild.collada.common.parser;

import com.carrotsearch.hppc.IntArrayList;
import org.xmlpull.v1.XmlPullParser;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IntParser implements Function<CharSequence, IntArrayList> {

    private static final Pattern pattern = Pattern.compile("\\S+");

    @Override
    public IntArrayList apply(CharSequence sequence) {
        IntArrayList ints = new IntArrayList();

        Matcher matcher = pattern.matcher(sequence);
        while (matcher.find()) {
            ints.add(Integer.parseInt(matcher.group()));
        }
        return ints;
    }

    public static Function<XmlPullParser, IntArrayList> asXmlParser(String tag) {
        return new IntParser().compose(new SimpleTextParser(tag));
    }

    public static Function<XmlPullParser, IntArrayList> asXmlParser(String namespace, String tag) {
        return new IntParser().compose(new SimpleTextParser(namespace, tag));
    }

}

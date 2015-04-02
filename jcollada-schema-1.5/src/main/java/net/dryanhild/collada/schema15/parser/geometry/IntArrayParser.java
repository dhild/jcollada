package net.dryanhild.collada.schema15.parser.geometry;

import net.dryanhild.collada.common.parser.IntParser;
import net.dryanhild.collada.common.parser.TextParser;
import net.dryanhild.collada.schema15.data.geometry.RawInts;
import org.xmlpull.v1.XmlPullParser;

import java.util.function.Function;

public class IntArrayParser implements Function<XmlPullParser, RawInts> {

    private final TextParser<RawInts> parser = new TextParser<>("int_array", RawInts.class);

    public IntArrayParser() {
        parser.addIntConsumer("count", RawInts::setCount);
        parser.addIntConsumer("minInclusive", RawInts::setMinInclusive);
        parser.addIntConsumer("maxInclusive", RawInts::setMaxInclusive);
        parser.setCharConsumer(RawInts::setInts, new IntParser());
    }

    @Override
    public RawInts apply(XmlPullParser pullParser) {
        return parser.apply(pullParser, new RawInts());
    }
}

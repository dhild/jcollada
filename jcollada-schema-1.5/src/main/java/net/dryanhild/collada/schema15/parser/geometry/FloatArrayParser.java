package net.dryanhild.collada.schema15.parser.geometry;

import net.dryanhild.collada.common.parser.FloatParser;
import net.dryanhild.collada.common.parser.TextParser;
import net.dryanhild.collada.schema15.data.geometry.RawFloats;
import org.xmlpull.v1.XmlPullParser;

import java.util.function.Function;

public class FloatArrayParser implements Function<XmlPullParser, RawFloats> {

    private final TextParser<RawFloats> parser = new TextParser<>("float_array", RawFloats.class);

    public FloatArrayParser() {
        parser.addIntConsumer("count", RawFloats::setCount);
        parser.addIntConsumer("digits", RawFloats::setDigits);
        parser.addIntConsumer("magnitude", RawFloats::setMagnitude);
        parser.setCharConsumer(RawFloats::setFloats, new FloatParser());
    }

    @Override
    public RawFloats apply(XmlPullParser pullParser) {
        return parser.apply(pullParser, new RawFloats());
    }
}

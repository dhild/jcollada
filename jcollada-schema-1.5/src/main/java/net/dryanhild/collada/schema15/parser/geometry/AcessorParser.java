package net.dryanhild.collada.schema15.parser.geometry;

import net.dryanhild.collada.common.parser.XmlParser;
import net.dryanhild.collada.schema15.data.geometry.Accessor;
import org.xmlpull.v1.XmlPullParser;

import java.util.function.Function;

public class AcessorParser implements Function<XmlPullParser, Accessor> {

    final XmlParser<Accessor> parser = new XmlParser<>("accessor", Accessor.class);

    public AcessorParser() {
        parser.addIntConsumer("count", Accessor::setCount);
        parser.addIntConsumer("offset", Accessor::setOffset);
        parser.addURIConsumer("source", Accessor::setSource);
        parser.addIntConsumer("stride", Accessor::setStride);
        parser.addElementConsumer("param", Accessor::addParam, new ParamParser());
    }

    @Override
    public Accessor apply(XmlPullParser pullParser) {
        return parser.apply(pullParser, new Accessor());
    }
}

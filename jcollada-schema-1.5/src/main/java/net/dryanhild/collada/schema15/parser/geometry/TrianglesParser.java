package net.dryanhild.collada.schema15.parser.geometry;

import net.dryanhild.collada.common.parser.IntParser;
import net.dryanhild.collada.common.parser.XmlParser;
import net.dryanhild.collada.schema15.data.geometry.RawTriangles;
import org.xmlpull.v1.XmlPullParser;

import java.util.function.Function;

public class TrianglesParser implements Function<XmlPullParser, RawTriangles> {

    private final XmlParser<RawTriangles> parser = new XmlParser<>("input", RawTriangles.class);

    public TrianglesParser() {
        parser.addIntConsumer("count", RawTriangles::setCount);
        parser.addAttributeConsumer("material", RawTriangles::setMaterial);
        parser.addElementConsumer("input", RawTriangles::addInput, new SharedInputParser());
        parser.addElementConsumer("p", RawTriangles::setP, IntParser.asXmlParser("p"));
    }

    @Override
    public RawTriangles apply(XmlPullParser pullParser) {
        return parser.apply(pullParser, new RawTriangles());
    }
}

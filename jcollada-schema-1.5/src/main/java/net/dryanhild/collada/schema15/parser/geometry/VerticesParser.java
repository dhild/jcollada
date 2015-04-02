package net.dryanhild.collada.schema15.parser.geometry;

import net.dryanhild.collada.common.parser.XmlParser;
import net.dryanhild.collada.schema15.data.geometry.RawVertices;
import org.xmlpull.v1.XmlPullParser;

import java.util.function.Function;
import javax.inject.Inject;

public class VerticesParser implements Function<XmlPullParser, RawVertices> {

    private final XmlParser<RawVertices> parser = new XmlParser<>("input", RawVertices.class);

    @Inject
    public VerticesParser(UnsharedInputParser inputParser) {
        parser.addElementConsumer("input", RawVertices::addInput, new UnsharedInputParser());
    }

    @Override
    public RawVertices apply(XmlPullParser pullParser) {
        return parser.apply(pullParser, new RawVertices());
    }
}

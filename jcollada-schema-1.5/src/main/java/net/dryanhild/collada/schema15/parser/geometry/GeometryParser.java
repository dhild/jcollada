package net.dryanhild.collada.schema15.parser.geometry;

import net.dryanhild.collada.common.parser.XmlParser;
import net.dryanhild.collada.schema15.data.geometry.RawGeometry;
import net.dryanhild.collada.schema15.parser.metadata.AssetParser;
import org.xmlpull.v1.XmlPullParser;

public class GeometryParser {

    private final XmlParser<RawGeometry> parser = new XmlParser<>("geometry", RawGeometry.class);

    public GeometryParser() {
        parser.addElementConsumer("asset", RawGeometry::setAsset, new AssetParser());

    }

    public RawGeometry parse(XmlPullParser pullParser) {
        return parser.apply(pullParser, new RawGeometry());
    }
}

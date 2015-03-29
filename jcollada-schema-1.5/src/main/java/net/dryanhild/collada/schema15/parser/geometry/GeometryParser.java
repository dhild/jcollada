package net.dryanhild.collada.schema15.parser.geometry;

import net.dryanhild.collada.common.parser.XmlParser;
import net.dryanhild.collada.schema15.data.geometry.RawGeometry;
import net.dryanhild.collada.schema15.parser.metadata.AssetParser;
import org.xmlpull.v1.XmlPullParser;

import javax.inject.Inject;

public class GeometryParser {

    private final XmlParser<RawGeometry> parser = new XmlParser<>("geometry", RawGeometry.class);

    @Inject
    public GeometryParser(AssetParser assetParser) {
        parser.addElementConsumer("asset", (fragment, pullParser) -> fragment.setAsset(assetParser.parse(pullParser)));
        
    }

    public RawGeometry parse(XmlPullParser pullParser) {
        RawGeometry geometry = new RawGeometry();

        return parser.parse(pullParser, geometry);
    }
}

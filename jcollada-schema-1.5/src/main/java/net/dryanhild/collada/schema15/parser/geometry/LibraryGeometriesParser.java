package net.dryanhild.collada.schema15.parser.geometry;

import net.dryanhild.collada.common.parser.XmlParser;
import net.dryanhild.collada.schema15.data.geometry.LibraryGeometries;
import net.dryanhild.collada.schema15.parser.metadata.AssetParser;
import org.xmlpull.v1.XmlPullParser;

import javax.inject.Inject;

public class LibraryGeometriesParser {

    private final XmlParser<LibraryGeometries> parser = new XmlParser<>("library_geometries", LibraryGeometries.class);

    @Inject
    public LibraryGeometriesParser(AssetParser assetParser) {
        parser.addElementConsumer("asset", (fragment, pullParser) -> fragment.setAsset(assetParser.parse(pullParser)));

    }

    public LibraryGeometries parse(XmlPullParser pullParser) {
        LibraryGeometries geometries = new LibraryGeometries();

        return parser.parse(pullParser, geometries);
    }
}

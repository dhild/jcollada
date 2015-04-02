package net.dryanhild.collada.schema15.parser.geometry;

import net.dryanhild.collada.common.parser.XmlParser;
import net.dryanhild.collada.schema15.data.geometry.LibraryGeometries;
import net.dryanhild.collada.schema15.parser.metadata.AssetParser;
import org.xmlpull.v1.XmlPullParser;

import java.util.function.Function;

public class LibraryGeometriesParser implements Function<XmlPullParser, LibraryGeometries> {

    private final XmlParser<LibraryGeometries> parser = new XmlParser<>("library_geometries", LibraryGeometries.class);

    public LibraryGeometriesParser() {
        parser.addElementConsumer("asset", LibraryGeometries::setAsset, new AssetParser());

    }

    @Override
    public LibraryGeometries apply(XmlPullParser pullParser) {
        return parser.apply(pullParser, new LibraryGeometries());
    }
}

package net.dryanhild.collada.schema15.parser;

import lombok.SneakyThrows;
import net.dryanhild.collada.common.parser.XmlParser;
import net.dryanhild.collada.schema15.data.ColladaDocumentFragment;
import net.dryanhild.collada.schema15.parser.metadata.AssetParser;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URI;

import static org.xmlpull.v1.XmlPullParser.START_DOCUMENT;
import static org.xmlpull.v1.XmlPullParser.START_TAG;

public class ColladaFragmentParser {

    private final XmlParser<ColladaDocumentFragment> parser = new XmlParser<>("COLLADA", ColladaDocumentFragment.class);

    public ColladaFragmentParser() {
        parser.addAttributeConsumer("base", ((fragment, value) -> fragment.setUri(fragment.getUri().resolve(value))));
        parser.addElementConsumer("asset", ColladaDocumentFragment::setAsset, new AssetParser());
    }

    public ColladaDocumentFragment parse(URI sourceUri, XmlPullParser pullParser) {
        ColladaDocumentFragment fragment = new ColladaDocumentFragment();
        fragment.setUri(sourceUri);

        skipToStart(pullParser);
        return parser.apply(pullParser, fragment);
    }

    @SneakyThrows({XmlPullParserException.class, IOException.class})
    private void skipToStart(XmlPullParser pullParser) {
        pullParser.require(START_DOCUMENT, null, null);
        while (pullParser.getEventType() != START_TAG) {
            pullParser.next();
        }
    }

}

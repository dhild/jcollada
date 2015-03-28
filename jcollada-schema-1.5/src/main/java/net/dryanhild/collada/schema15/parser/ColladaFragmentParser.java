package net.dryanhild.collada.schema15.parser;

import net.dryanhild.collada.common.parser.XmlParser;
import net.dryanhild.collada.schema15.data.ColladaDocumentFragment;
import org.xmlpull.v1.XmlPullParser;

import java.net.URI;

public class ColladaFragmentParser {

    private final XmlParser<ColladaDocumentFragment> parser = new XmlParser<>("COLLADA", ColladaDocumentFragment.class);

    public ColladaDocumentFragment parse(URI sourceUri, XmlPullParser pullParser) {
        return new ColladaDocumentFragment(sourceUri);
    }

}
